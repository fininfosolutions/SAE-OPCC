package com.fininfo.saeopcc.shared.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.NotificationStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A Notification. */
@Entity
@Table(name = "notification", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Notification implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "market_type")
  private String marketType;

  @Column(name = "sign")
  private String sign;

  @Column(name = "isin_code")
  private String isinCode;

  @Column(name = "security_description")
  private String securityDescription;

  @Column(name = "proxy_reference")
  private String proxyRefrence;

  @Column(name = "seller")
  private String seller;

  @Column(name = "issuer")
  private String issuer;

  @Column(name = "shareholder_reference")
  private String shareholderReference;

  @Column(name = "security_type")
  private String securityType;

  @Column(name = "security_classification")
  private String securityClassification;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "transaction_number")
  private String transactionNumber;

  @Column(name = "contract_number")
  private String contractNumber;

  @Column(name = "negociation_code")
  private String negociationCode;

  @Column(name = "client_security_account")
  private String clientSecurityAccount;

  @Column(name = "client_security_account_description")
  private String clientSecurityAccountDescription;

  @Column(name = "custodian")
  private String custodian;

  @Column(name = "counterpart")
  private String counterpart;

  @Column(name = "client_name")
  private String clientName;

  @Column(name = "client_identifier")
  private String clientIdentifier;

  @Column(name = "broker_identifier")
  private String brokerIdentifier;

  @Column(name = "trade_date")
  private LocalDate tradeDate;

  @Column(name = "settlement_date")
  private LocalDate settlementDate;

  @Column(name = "value_date")
  private LocalDate valueDate;

  @Column(name = "return_date")
  private LocalDate returnDate;

  @Column(name = "maturity_date")
  private LocalDate maturityDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "validation_date")
  private LocalDate validationDate;

  @Column(name = "input_date")
  private LocalDate inputDate;

  @Column(name = "investment_day_number")
  private Integer investmentDayNumber;

  @Column(name = "maturity", precision = 21, scale = 2)
  private BigDecimal maturity;

  @Column(name = "negociation_price", precision = 21, scale = 2)
  private BigDecimal negociationPrice;

  @Column(name = "ppc_negociation", precision = 21, scale = 2)
  private BigDecimal ppcNegociation;

  @Column(name = "spread_negociation", precision = 21, scale = 2)
  private BigDecimal spreadNegociation;

  @Column(name = "quantity", precision = 21, scale = 2)
  private BigDecimal quantity;

  @Column(name = "provisional_share_number", precision = 21, scale = 2)
  private BigDecimal provisionalShareNumber;

  @Column(name = "provisional_nav", precision = 21, scale = 2)
  private BigDecimal provisionalNAV;

  @Column(name = "unit_price_nav_price", precision = 21, scale = 2)
  private BigDecimal unitPriceNavPrice;

  @Column(name = "repo_price", precision = 21, scale = 2)
  private BigDecimal repoPrice;

  @Column(name = "repo_back_price", precision = 21, scale = 2)
  private BigDecimal repoBackPrice;

  @Column(name = "provisional_amount", precision = 21, scale = 2)
  private BigDecimal provisionalAmount;

  @Column(name = "repo_amount", precision = 21, scale = 2)
  private BigDecimal repoAmount;

  @Column(name = "gross_amount", precision = 21, scale = 2)
  private BigDecimal grossAmount;

  @Column(name = "interest", precision = 21, scale = 2)
  private BigDecimal interest;

  @Column(name = "vat", precision = 21, scale = 2)
  private BigDecimal vat;

  @Column(name = "brokerage_commission", precision = 21, scale = 2)
  private BigDecimal brokerageCommission;

  @Column(name = "total_fees", precision = 21, scale = 2)
  private BigDecimal totalFees;

  @Column(name = "net_amount", precision = 21, scale = 2)
  private BigDecimal netAmount;

  @Column(name = "net_amount_wht", precision = 21, scale = 2)
  private BigDecimal netAmountWht;

  @Column(name = "repo_back_amount_vat_excluded", precision = 21, scale = 2)
  private BigDecimal repoBackAmountVatExcluded;

  @Column(name = "repo_back_amount_vat_included", precision = 21, scale = 2)
  private BigDecimal repoBackAmountVatIncluded;

  @Column(name = "trade_currency")
  private String tradeCurrency;

  @Column(name = "settlement_currency")
  private String settlementCurrency;

  @Column(name = "notification_error")
  private String notificationError;

  @Column(name = "notification_error_description")
  private String notificationErrorDescription;

  @Enumerated(EnumType.STRING)
  @Column(name = "notification_status")
  private NotificationStatus notificationStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "sens")
  private Sens sens;

  @Column(name = "sheet_config_id")
  private Long sheetConfigId;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "status")
  private String status;

  @Column(name = "settlementplace")
  private String settlementplace;

  @Column(name = "beneficiary_security_account")
  private String beneficiarySecurityAccount;

  @Column(name = "beneficiary_cash_account")
  private String beneficiaryCashAccount;

  @Column(name = "currency_cash_account")
  private String currencyCashAccount;

  @Column(name = "nostro_security_account")
  private String nostroSecurityAccount;

  @Column(name = "nostro_cash_account")
  private String nostroCashAccount;

  @Column(name = "client_custodian_id")
  private String clientCustodianId;

  @Column(name = "client_custodian_code")
  private String clientCustodianCode;

  @Column(name = "csd_id")
  private String csdId;

  @Column(name = "csd_code")
  private String csdCode;

  @Column(name = "csd_description")
  private String csdDescription;

  @Column(name = "beneficiary_id")
  private String beneficiaryId;

  @Column(name = "beneficiary_code")
  private String beneficiaryCode;

  @Column(name = "benediciary_description")
  private String benediciaryDescription;

  @Column(name = "counter_part_code")
  private String counterPartCode;

  @Column(name = "counter_part_id")
  private String counterPartId;

  @Column(name = "counter_part_custodian_id")
  private String counterPartCustodianId;

  @Column(name = "counter_part_custodian_code")
  private String counterPartCustodianCode;

  @Column(name = "counter_part_custodian_descrip")
  private String counterPartCustodianDescrip;

  @Column(name = "client_code")
  private String clientCode;

  @Column(name = "reference_rate", precision = 21, scale = 2)
  private BigDecimal referenceRate;

  @Column(name = "coverage_rate", precision = 21, scale = 2)
  private BigDecimal coverageRate;

  @Column(name = "base_point", precision = 21, scale = 2)
  private BigDecimal basePoint;

  @Column(name = "quantity_type")
  private String quantityType;

  @Column(name = "intereset_amount_gross", precision = 21, scale = 2)
  private BigDecimal interesetAmountGross;

  @Column(name = "intereset_amount_vat", precision = 21, scale = 2)
  private BigDecimal interesetAmountVAT;

  @Column(name = "tpprf", precision = 21, scale = 2)
  private BigDecimal tpprf;

  @Column(name = "broker_commission_vat", precision = 21, scale = 2)
  private BigDecimal brokerCommissionVAT;

  @Column(name = "broker_commission_net", precision = 21, scale = 2)
  private BigDecimal brokerCommissionNet;

  @Column(name = "stock_exchange_tax_gross", precision = 21, scale = 2)
  private BigDecimal stockExchangeTaxGross;

  @Column(name = "stock_exchange_tax_vat", precision = 21, scale = 2)
  private BigDecimal stockExchangeTaxVat;

  @Column(name = "stock_exchange_tax_net", precision = 21, scale = 2)
  private BigDecimal stockExchangeTaxNet;

  @Column(name = "settlement_amount", precision = 21, scale = 2)
  private BigDecimal settlementAmount;

  @Column(name = "settlement_fees_gross", precision = 21, scale = 2)
  private BigDecimal settlementFeesGross;

  @Column(name = "settlement_fees_vat", precision = 21, scale = 2)
  private BigDecimal settlementFeesVAT;

  @Column(name = "settlement_fees_net", precision = 21, scale = 2)
  private BigDecimal settlementFeesNet;

  @Column(name = "cash_account_currency_id")
  private String cashAccountCurrencyID;

  @Column(name = "cash_account_currency")
  private String cashAccountCurrency;

  @Column(name = "market_classification")
  private String marketClassification;

  @Column(name = "market_structure")
  private String marketStructure;

  @Column(name = "entry_or_exit_right_gross", precision = 21, scale = 2)
  private BigDecimal entryOrExitRightGross;

  @Column(name = "entry_or_exit_right_vat", precision = 21, scale = 2)
  private BigDecimal entryOrExitRightVAT;

  @Column(name = "entry_or_exit_right_net", precision = 21, scale = 2)
  private BigDecimal entryOrExitRightNet;

  @Column(name = "placement_network_commission_gross", precision = 21, scale = 2)
  private BigDecimal placementNetworkCommissionGross;

  @Column(name = "placement_network_commission_vat", precision = 21, scale = 2)
  private BigDecimal placementNetworkCommissionVAT;

  @Column(name = "placement_network_commission_net", precision = 21, scale = 2)
  private BigDecimal placementNetworkCommissionNet;

  @Column(name = "subs_redemp_type")
  private Boolean subsRedempType;

  @Column(name = "sett_part_client_id")
  private Long settPartClientID;

  @Column(name = "sett_part_client_identifier")
  private String settPartClientIdentifier;

  @Column(name = "sett_part_client_ds_schema")
  private String settPartClientDsSchema;

  @Column(name = "sett_part_client_property_code")
  private String settPartClientPropertyCode;

  @Column(name = "sett_part_safe_keeping_id")
  private Long settPartSafeKeepingId;

  @Column(name = "sett_part_safekeeping_identifier")
  private String settPartSafekeepingIdentifier;

  @Column(name = "sett_part_safekeeping_ds_schema")
  private String settPartSafekeepingDsSchema;

  @Column(name = "sett_part_safekeeping_property_code")
  private String settPartSafekeepingPropertyCode;

  @Column(name = "sett_part_place_of_settlement_id")
  private Long settPartPlaceOfSettlementId;

  @Column(name = "sett_part_place_of_settlement_identifier")
  private String settPartPlaceOfSettlementIdentifier;

  @Column(name = "sett_part_place_of_settlement_ds_schema")
  private String settPartPlaceOfSettlementDsSchema;

  @Column(name = "sett_part_place_of_settlement_property_code")
  private String settPartPlaceOfSettlementPropertyCode;

  @Column(name = "sett_part_rea_gid")
  private Long settPartREAGid;

  @Column(name = "sett_part_reag_identifier")
  private String settPartREAGIdentifier;

  @Column(name = "sett_part_reag_ds_schema")
  private String settPartREAGDsSchema;

  @Column(name = "sett_part_reag_property_code")
  private String settPartREAGPropertyCode;

  @Column(name = "sett_part_deag_id")
  private Long settPartDEAGId;

  @Column(name = "sett_part_deag_identifier")
  private String settPartDEAGIdentifier;

  @Column(name = "sett_part_deag_ds_schema")
  private String settPartDEAGDsSchema;

  @Column(name = "sett_part_deag_property_code")
  private String settPartDEAGPropertyCode;

  @Column(name = "sett_part_buyr_id")
  private Long settPartBUYRId;

  @Column(name = "sett_part_buyr_identifier")
  private String settPartBUYRIdentifier;

  @Column(name = "sett_part_buyr_ds_schema")
  private String settPartBUYRDsSchema;

  @Column(name = "sett_part_buyr_property_code")
  private String settPartBUYRPropertyCode;

  @Column(name = "sett_part_sell_id")
  private Long settPartSELLId;

  @Column(name = "sett_part_sell_identifier")
  private String settPartSELLIdentifier;

  @Column(name = "sett_part_sell_ds_schema")
  private String settPartSELLDsSchema;

  @Column(name = "sett_part_sell_property_code")
  private String settPartSELLPropertyCode;

  @Column(name = "sett_part_othr_prty_1_id")
  private Long settPartOthrPrty1Id;

  @Column(name = "sett_part_othr_prty_1_identifier")
  private String settPartOthrPrty1Identifier;

  @Column(name = "sett_part_othr_prty_1_ds_schema")
  private String settPartOthrPrty1DsSchema;

  @Column(name = "sett_part_othr_prty_1_property_code")
  private String settPartOthrPrty1PropertyCode;

  @Column(name = "sett_part_othr_prty_2_id")
  private Long settPartOthrPrty2Id;

  @Column(name = "sett_part_othr_prty_2_identifier")
  private String settPartOthrPrty2Identifier;

  @Column(name = "sett_part_othr_prty_2_ds_schema")
  private String settPartOthrPrty2DsSchema;

  @Column(name = "sett_part_othr_prty_2_property_code")
  private String settPartOthrPrty2PropertyCode;

  @Column(name = "negociation_rate", precision = 21, scale = 2)
  private BigDecimal negociationRate;

  @Column(name = "client_provider_id")
  private String clientProviderID;

  @Column(name = "client_provider_code")
  private String clientProviderCode;

  @Column(name = "client_provider_description")
  private String clientProviderDescription;

  @Column(name = "client_cash_account")
  private String clientCashAccount;

  @Column(name = "client_cash_account_descr")
  private String clientCashAccountDescr;

  @ManyToOne
  @JsonIgnoreProperties(value = "notifications", allowSetters = true)
  @EqualsAndHashCode.Exclude
  private Flow flow;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "notification", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<NotificationError> notificationErrors = new HashSet<>();
}
