package com.fininfo.saeopcc.shared.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fininfo.saeopcc.configuration.KafkaConfiguration;
import com.fininfo.saeopcc.multitenancy.domains.Client;
import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.services.ClientService;
import com.fininfo.saeopcc.multitenancy.services.CompartementService;
import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.BusinessRiskCategory;
import com.fininfo.saeopcc.shared.domains.CSD;
import com.fininfo.saeopcc.shared.domains.ClientCategory;
import com.fininfo.saeopcc.shared.domains.Countries;
import com.fininfo.saeopcc.shared.domains.Custodian;
import com.fininfo.saeopcc.shared.domains.Devise;
import com.fininfo.saeopcc.shared.domains.EconomicAgentCategory;
import com.fininfo.saeopcc.shared.domains.FiscalNature;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.FundOrganism;
import com.fininfo.saeopcc.shared.domains.Issuer;
import com.fininfo.saeopcc.shared.domains.ResidenceStatus;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.SDG;
import com.fininfo.saeopcc.shared.domains.SecuritySector;
import com.fininfo.saeopcc.shared.domains.SocioProfessionalCategory;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.domains.enumeration.AddressTypes;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.ClientType;
import com.fininfo.saeopcc.shared.domains.enumeration.DayWeek;
import com.fininfo.saeopcc.shared.domains.enumeration.DetentionForm;
import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.FundCategory;
import com.fininfo.saeopcc.shared.domains.enumeration.FundClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.FundForm;
import com.fininfo.saeopcc.shared.domains.enumeration.FundGuarantee;
import com.fininfo.saeopcc.shared.domains.enumeration.FundRate;
import com.fininfo.saeopcc.shared.domains.enumeration.FundStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.FundType;
import com.fininfo.saeopcc.shared.domains.enumeration.JuridicalNature;
import com.fininfo.saeopcc.shared.domains.enumeration.LegalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.MarketType;
import com.fininfo.saeopcc.shared.domains.enumeration.Periodicity;
import com.fininfo.saeopcc.shared.domains.enumeration.QuantityType;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.domains.enumeration.SettlementPlace;
import com.fininfo.saeopcc.shared.domains.enumeration.ShareClass;
import com.fininfo.saeopcc.shared.domains.enumeration.ShareForm;
import com.fininfo.saeopcc.shared.domains.enumeration.UnitCategory;
import com.fininfo.saeopcc.shared.domains.exceptionutils.MapperException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

  private static final String JSON_ERROR_MESSAGE = "Error parsing JSON data: {}";
  private static final String SCHEMA_KEY = "Schema";

  @Value("${spring.application.name}")
  private String appName;

  private static final ObjectMapper mapper =
      new ObjectMapper()
          .registerModule(new ParameterNamesModule())
          .registerModule(new Jdk8Module())
          .registerModule(new JavaTimeModule());
  private static final String APP_NAME_KEY = "APP_ID";

  private static final String DECIMALFORMAT = "#,##0.00";
  private final Logger log = LoggerFactory.getLogger(KafkaService.class);
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired private ClientService clientService;
  @Autowired private CustodianService custodianService;
  @Autowired private CompartementService compartementService;

  @Autowired private FundService fundService;

  @Autowired private IssuerService issuerService;

  @Autowired private SDGService sdgService;

  @Autowired private ProviderService providerService;

  public KafkaService(
      KafkaConfiguration kafkaProperties, KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  private static DecimalFormat decimalFormat() {

    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

    decimalFormatSymbols.setDecimalSeparator(',');

    decimalFormatSymbols.setGroupingSeparator(' ');

    DecimalFormat decimalFormat = new DecimalFormat(DECIMALFORMAT, decimalFormatSymbols);

    decimalFormat.setParseBigDecimal(true);

    return decimalFormat;
  }

  public Object fillRequiredEntity(Object entity, JsonNode innerNode)
      throws NoSuchFieldException, IllegalAccessException {

    Iterator<Entry<String, JsonNode>> fields = innerNode.fields();

    HashMap<String, Object> requiredFields = new HashMap<>();

    while (fields.hasNext()) {

      Entry<String, JsonNode> item = fields.next();

      String targetField = item.getKey();

      JsonNode targetValue = item.getValue();

      if (targetValue != null
          && !targetValue.isMissingNode()
          && !(targetValue instanceof NullNode)) {
        if (targetValue instanceof ArrayNode) {
          List arrayValue = new ArrayList<>();
          targetValue.forEach(child -> arrayValue.add(child));
          requiredFields.put(targetField, arrayValue);
        } else if (targetValue instanceof ObjectNode) {
          requiredFields.put(targetField, targetValue);
        } else requiredFields.put(targetField, targetValue.asText());
      }
    }

    log.info("ARRAY------> : {}", requiredFields);

    final String PATTERN_DATE = "dd/MM/yyyy";
    final String PATTERN_DATE2 = "yyyy-MM-dd";

    requiredFields.forEach(
        (key, value) -> {
          try {

            Field field;
            if (entity instanceof Role) {
              try {
                field = entity.getClass().getDeclaredField(key);
              } catch (NoSuchFieldException e) {
                field = Role.class.getDeclaredField(key);
              }
              if (field != null) {

                field.setAccessible(true);

                if (field.getType().equals(String.class)) {

                  field.set(entity, value.toString());

                } else if (field.getType().equals(Integer.class)) {

                  Integer integerValue = Integer.valueOf(value.toString());

                  field.set(entity, integerValue);

                } else if (field.getType().equals(Long.class)) {

                  Long longValue = Long.valueOf(value.toString());

                  field.set(entity, longValue);

                } else if (field.getType().equals(BigDecimal.class)) {

                  BigDecimal bigDecimalValue = (BigDecimal) decimalFormat().parse(value.toString());

                  field.set(entity, bigDecimalValue);

                } else if (field.getType().equals(LocalDate.class)) {

                  DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DATE);

                  LocalDate localDateValue = LocalDate.parse(value.toString(), dtf);

                  field.set(entity, localDateValue);

                } else if (field.getType().equals(Boolean.class)) {

                  Boolean booleanValue = Boolean.valueOf(value.toString());

                  field.set(entity, booleanValue);
                } else if (field.getType().equals(AddressTypes.class)) {

                  AddressTypes addressTypesValue = AddressTypes.valueOf(value.toString());

                  field.set(entity, addressTypesValue);
                } else if (field.getType().equals(ActingAs.class)) {
                  ActingAs actingAsValue = ActingAs.valueOf(value.toString());
                  field.set(entity, actingAsValue);
                } else if (field.getType().equals(Set.class)
                    && field.getGenericType() instanceof ParameterizedType) {
                  ParameterizedType setType = (ParameterizedType) field.getGenericType();
                  if (setType.getActualTypeArguments()[0].equals(Address.class)) {
                    Set<Address> addresses = new HashSet<>();
                    for (JsonNode addressNode : innerNode.get(key)) {
                      Address address = new Address();
                      // Désérialisez les propriétés de l'adresse (name, type, etc.)
                      address = (Address) fillRequiredEntity(address, addressNode);
                      addresses.add(address);
                    }
                    field.set(entity, addresses);
                  }
                } else if (field.getType().isEnum() && entity instanceof Client) {
                  switch (field.getName()) {
                    case "clientType":
                      ClientType clientTypeValue = ClientType.valueOf(value.toString());
                      field.set(entity, clientTypeValue);
                      break;
                    case "fiscalStatus":
                      FiscalStatus FiscalStatusValue = FiscalStatus.valueOf(value.toString());
                      field.set(entity, FiscalStatusValue);
                      break;

                    case "juridicalNature":
                      JuridicalNature JuridicalNaturevalue =
                          JuridicalNature.valueOf(value.toString());
                      field.set(entity, JuridicalNaturevalue);
                      break;
                    case "securityForm":
                      SecurityForm SecurityFormValue = SecurityForm.valueOf(value.toString());
                      field.set(entity, SecurityFormValue);
                      break;
                  }
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof SocioProfessionalCategory) {

                  SocioProfessionalCategory s = new SocioProfessionalCategory();
                  s.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, s);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof EconomicAgentCategory) {
                  EconomicAgentCategory e = new EconomicAgentCategory();
                  e.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, e);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof Countries) {
                  Countries c = new Countries();
                  c.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, c);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof ResidenceStatus) {
                  ResidenceStatus c = new ResidenceStatus();
                  c.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, c);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof SecuritySector) {
                  SecuritySector c = new SecuritySector();
                  c.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, c);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof ClientCategory) {
                  ClientCategory c = new ClientCategory();
                  c.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, c);
                }
              }
            } else if (entity instanceof Asset) {
              try {
                field = entity.getClass().getDeclaredField(key);
              } catch (NoSuchFieldException e) {
                field = Asset.class.getDeclaredField(key);
              }
              if (field != null) {

                field.setAccessible(true);

                if (field.getType().equals(String.class)) {

                  field.set(entity, value.toString());

                } else if (field.getType().equals(Integer.class)) {

                  Integer integerValue = Integer.valueOf(value.toString());

                  field.set(entity, integerValue);

                } else if (field.getType().equals(Long.class)) {

                  Long longValue = Long.valueOf(value.toString());

                  field.set(entity, longValue);

                } else if (field.getType().equals(BigDecimal.class)) {

                  BigDecimal bigDecimalValue = (BigDecimal) decimalFormat().parse(value.toString());

                  field.set(entity, bigDecimalValue);

                } else if (field.getType().equals(LocalDate.class)) {

                  DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DATE2);

                  LocalDate localDateValue = LocalDate.parse(value.toString(), dtf);

                  field.set(entity, localDateValue);

                } else if (field.getType().equals(Boolean.class)) {

                  Boolean booleanValue = Boolean.valueOf(value.toString());

                  field.set(entity, booleanValue);
                } else if (field.getType().isEnum() && entity instanceof Fund) {
                  switch (field.getName()) {
                    case "dayNAV":
                      DayWeek dayNAVValue = DayWeek.valueOf(value.toString());
                      field.set(entity, dayNAVValue);
                      break;
                    case "frequencyNAV":
                      Periodicity frequencyNAVValue = Periodicity.valueOf(value.toString());
                      field.set(entity, frequencyNAVValue);
                      break;
                    case "fundClassification":
                      FundClassification fundClassificationValue =
                          FundClassification.valueOf(value.toString());
                      field.set(entity, fundClassificationValue);
                      break;
                    case "fundCategory":
                      FundCategory fundCategoryValue = FundCategory.valueOf(value.toString());
                      field.set(entity, fundCategoryValue);
                      break;

                    case "fundType":
                      FundType fundTypeValue = FundType.valueOf(value.toString());
                      field.set(entity, fundTypeValue);
                      break;
                    case "fundRate":
                      FundRate fundRateValue = FundRate.valueOf(value.toString());
                      field.set(entity, fundRateValue);
                      break;
                    case "fundGuarantee":
                      FundGuarantee fundGuaranteeValue = FundGuarantee.valueOf(value.toString());
                      field.set(entity, fundGuaranteeValue);
                      break;

                    case "fundStatus":
                      FundStatus FundStatusValue = FundStatus.valueOf(value.toString());
                      field.set(entity, FundStatusValue);
                      break;
                    case "fundForm":
                      FundForm FundFormValue = FundForm.valueOf(value.toString());
                      field.set(entity, FundFormValue);
                      break;
                    case "assetType":
                      AssetType AssetTypeValue = AssetType.valueOf(value.toString());
                      field.set(entity, AssetTypeValue);
                      break;

                    case "detentionForm":
                      DetentionForm DetentionFormValue = DetentionForm.valueOf(value.toString());
                      field.set(entity, DetentionFormValue);
                      break;
                    case "legalStatus":
                      LegalStatus legalStatusValue = LegalStatus.valueOf(value.toString());
                      field.set(entity, legalStatusValue);
                      break;
                    case "marketType":
                      MarketType markettypeValue = MarketType.valueOf(value.toString());
                      field.set(entity, markettypeValue);
                      break;
                    case "quantityType":
                      QuantityType quantityTypeValue = QuantityType.valueOf(value.toString());
                      field.set(entity, quantityTypeValue);
                      break;
                    case "securityForm":
                      SecurityForm SecurityFormValue = SecurityForm.valueOf(value.toString());
                      field.set(entity, SecurityFormValue);
                      break;
                    case "unitCategory":
                      UnitCategory unitCategoryValue = UnitCategory.valueOf(value.toString());
                      field.set(entity, unitCategoryValue);
                      break;

                    case "settlementPlace":
                      SettlementPlace settlementPlaceValue =
                          SettlementPlace.valueOf(value.toString());
                      field.set(entity, settlementPlaceValue);
                      break;
                  }
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof FundOrganism) {

                  FundOrganism f = new FundOrganism();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof BusinessRiskCategory) {

                  BusinessRiskCategory f = new BusinessRiskCategory();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof FiscalNature) {

                  FiscalNature f = new FiscalNature();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof SecuritySector) {
                  SecuritySector s = new SecuritySector();
                  s.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, s);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof SDG) {
                  SDG i = new SDG();
                  i.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, i);
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof CSD) {
                  CSD csd = new CSD();
                  csd.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, csd);
                }
              }

            } else {

              field = entity.getClass().getDeclaredField(key);

              if (field != null) {
                field.setAccessible(true);

                if (field.getType().equals(String.class)) {

                  field.set(entity, value.toString());

                } else if (field.getType().equals(Integer.class)) {

                  Integer integerValue = Integer.valueOf(value.toString());

                  field.set(entity, integerValue);

                } else if (field.getType().equals(Long.class)) {

                  Long longValue = Long.valueOf(value.toString());

                  field.set(entity, longValue);

                } else if (field.getType().equals(BigDecimal.class)) {

                  BigDecimal bigDecimalValue = (BigDecimal) decimalFormat().parse(value.toString());

                  field.set(entity, bigDecimalValue);

                } else if (field.getType().equals(LocalDate.class)) {

                  DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DATE2);

                  LocalDate localDateValue = LocalDate.parse(value.toString(), dtf);

                  field.set(entity, localDateValue);

                } else if (field.getType().equals(Boolean.class)) {

                  Boolean booleanValue = Boolean.valueOf(value.toString());

                  field.set(entity, booleanValue);
                } else if (field.getType().equals(AddressTypes.class)) {

                  AddressTypes addressTypesValue = AddressTypes.valueOf(value.toString());

                  field.set(entity, addressTypesValue);
                } else if (field.getType().isAssignableFrom(Asset.class)) {

                  Asset asset = new Asset();
                  asset.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, asset);

                } else if (field.getType().isEnum() && entity instanceof Compartement) {
                  switch (field.getName()) {
                    case "shareForm":
                      ShareForm SshareFormValue = ShareForm.valueOf(value.toString());
                      field.set(entity, SshareFormValue);
                      break;
                    case "shareClass":
                      ShareClass ShareClassValue = ShareClass.valueOf(value.toString());
                      field.set(entity, ShareClassValue);
                      break;
                  }
                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof Devise) {

                  Devise f = new Devise();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof Client) {

                  Client f = new Client();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof Client) {

                  Client f = new Client();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);

                } else if (Class.forName(field.getType().getName()).getConstructor().newInstance()
                    instanceof Fund) {

                  Fund f = new Fund();
                  f.setId(((ObjectNode) value).get("id").longValue());
                  field.set(entity, f);
                }
              }
            }

          } catch (NoSuchFieldException e) {

            log.error(
                "field {} not found in target entity {}", key, entity.getClass().getSimpleName());

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });

    return entity;
  }

  private <T> String toJson(T object) {

    try {

      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

      return mapper.writeValueAsString(object);

    } catch (Exception e) {

      throw new MapperException(e.getMessage());
    }
  }

  @KafkaListener(
      topics = "#{'${kafkaTopics.issuertopicFromReferentielToConsumers}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getIssuers(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    final Logger log = LoggerFactory.getLogger(getClass());

    ObjectMapper objectMapper =
        new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    JsonNode rootNode = objectMapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = objectMapper.writeValueAsString(innerNode);

    try {

      if (entityType.equalsIgnoreCase(Issuer.class.getSimpleName())) {

        Issuer i = new Issuer();

        i = (Issuer) fillRequiredEntity(i, innerNode);

        Issuer synced = issuerService.syncIssuer(i);

        if (synced != null) {

          log.info("Issuer Synchronized : [{}]", synced);

        } else log.error("Synchronize Issuer Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error(JSON_ERROR_MESSAGE, updatedJson);
    }
  }

  @KafkaListener(
      topics = "#{'${kafkaTopics.SDGFromReferentielToConsumers}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getSDGs(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    final Logger log = LoggerFactory.getLogger(getClass());

    ObjectMapper objectMapper =
        new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    JsonNode rootNode = objectMapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = objectMapper.writeValueAsString(innerNode);

    try {

      if (entityType.equalsIgnoreCase(SDG.class.getSimpleName())) {

        SDG i = new SDG();

        i = (SDG) fillRequiredEntity(i, innerNode);

        SDG synced = sdgService.syncSDG(i);

        if (synced != null) {

          log.info("SDG Synchronized : [{}]", synced);

        } else log.error("Synchronize SDG Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error(JSON_ERROR_MESSAGE, updatedJson);
    }
  }

  //   private <T> T deserializeMessage(String data, Class<T> targetType)
  //       throws JsonProcessingException {
  //     ObjectMapper objectMapper =
  //         new ObjectMapper()
  //             .registerModule(new ParameterNamesModule())
  //             .registerModule(new Jdk8Module())
  //             .registerModule(new JavaTimeModule());

  //     try {
  //       JsonNode rootNode = objectMapper.readTree(data);
  //       String firstKey = rootNode.fieldNames().next();
  //       JsonNode innerNode = rootNode.get(firstKey);
  //       return objectMapper.treeToValue(innerNode, targetType);
  //     } catch (JsonProcessingException e) {
  //       throw e;
  //     }
  //   }

  @KafkaListener(
      topics = "#{'${kafkaTopics.fundtopic}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getFunds(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    JsonNode rootNode = mapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = mapper.writeValueAsString(innerNode);
    JsonNode appNameNode = rootNode.get(APP_NAME_KEY);

    if (appNameNode.asText().contains("_")
        && !appNameNode.asText().endsWith(appName.substring(appName.indexOf("_")))) return;

    try {

      if (entityType.equalsIgnoreCase(Fund.class.getSimpleName())) {

        Fund b = new Fund();

        b = (Fund) fillRequiredEntity(b, innerNode);

        Fund synced = fundService.syncFund(b, !appNameNode.asText().contains("_"));

        if (synced != null) {

          log.info("Fund Synchronized : [{}]", synced);

        } else log.error("Synchronize Fund Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error("ffffffff", updatedJson);
    }
  }

  @KafkaListener(
      topics = "#{'${kafkaTopics.custodiantopicFromReferentielToConsumers}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getCustodians(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    final Logger log = LoggerFactory.getLogger(getClass());

    ObjectMapper objectMapper =
        new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    JsonNode rootNode = objectMapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = objectMapper.writeValueAsString(innerNode);

    try {

      if (entityType.equalsIgnoreCase(Custodian.class.getSimpleName())) {

        Custodian c = new Custodian();

        c = (Custodian) fillRequiredEntity(c, innerNode);

        Custodian synced = custodianService.syncCustodian(c);

        if (synced != null) {

          log.info("Custodian Synchronized : [{}]", synced);

        } else log.error("Synchronize Custodian Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error(JSON_ERROR_MESSAGE, updatedJson);
    }
  }

  @KafkaListener(
      topics = "#{'${kafkaTopics.clientFromReferentielToConsumers}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getClient(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    final Logger log = LoggerFactory.getLogger(getClass());

    JsonNode rootNode = mapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = mapper.writeValueAsString(innerNode);

    JsonNode schemaNode = null;

    String schemaJson = null;

    schemaNode = rootNode.get(SCHEMA_KEY);
    schemaJson = mapper.writeValueAsString(schemaNode);

    try {

      if (entityType.equalsIgnoreCase(Client.class.getSimpleName())) {

        String tenant = schemaNode.asText();

        Client c = new Client();

        c = (Client) fillRequiredEntity(c, innerNode);

        Client synced = clientService.syncClient(c, tenant);

        if (synced != null) {

          log.info("Client Synchronized : [{}]", synced);

        } else log.error("Synchronize Client Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error("Error parsing JSON data: {} with Schema {}", updatedJson, schemaJson);
    }
  }

  @KafkaListener(
      topics = "#{'${kafkaTopics.compartementtopicFromReferentielToConsumers}'}",
      groupId = "#{'${kafka.consumer.group.id}'}",
      containerFactory = "kakfaListenerContainerFactory")
  public void getCompartement(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
      throws JsonMappingException, JsonProcessingException {

    final Logger log = LoggerFactory.getLogger(getClass());

    JsonNode rootNode = mapper.readTree(data);

    String entityType = rootNode.fieldNames().next();

    String firstKey = rootNode.fieldNames().next();

    JsonNode innerNode = rootNode.get(firstKey);

    String updatedJson = mapper.writeValueAsString(innerNode);

    JsonNode schemaNode = null;

    String schemaJson = null;

    schemaNode = rootNode.get(SCHEMA_KEY);
    schemaJson = mapper.writeValueAsString(schemaNode);

    try {

      if (entityType.equalsIgnoreCase(Compartement.class.getSimpleName())) {

        String tenant = schemaNode.asText();

        Compartement c = new Compartement();

        c = (Compartement) fillRequiredEntity(c, innerNode);

        Compartement synced = compartementService.syncCompartement(c, tenant);

        if (synced != null) {

          log.info("Compartement Synchronized : [{}]", synced);

        } else log.error("Synchronize Compartement Process Failed !");
      }

    } catch (NoSuchFieldException | IllegalAccessException e) {

      log.error("Error parsing JSON data: {} with Schema {}", updatedJson, schemaJson);
    }
  }

  //   private <T> T deserializeMessage2(String data, Class<T> targetType)
  //       throws JsonProcessingException {
  //     ObjectMapper objectMapper =
  //         new ObjectMapper()
  //             .registerModule(new ParameterNamesModule())
  //             .registerModule(new Jdk8Module())
  //             .registerModule(new JavaTimeModule());

  //     try {
  //       JsonNode rootNode = objectMapper.readTree(data);
  //       String firstKey = rootNode.fieldNames().next();
  //       JsonNode innerNode = rootNode.get(firstKey);
  //       JsonNode schemaNode = rootNode.get(SCHEMA_KEY);
  //       currentTenant = schemaNode.asText();
  //       return objectMapper.treeToValue(innerNode, targetType);

  //     } catch (JsonProcessingException e) {
  //       throw e;
  //     }
  //   }

}
