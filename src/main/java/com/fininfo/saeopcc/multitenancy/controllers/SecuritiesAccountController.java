package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.SecuritiesAccountQueryService;
import com.fininfo.saeopcc.multitenancy.services.SecuritiesAccountService;
import com.fininfo.saeopcc.multitenancy.services.ShareholderService;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.services.AssetService;
import com.fininfo.saeopcc.shared.services.IntermediaryService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class SecuritiesAccountController {

  private static final String ENTITY_NAME = "registerdSecaccount";

  @Autowired SecuritiesAccountService secAccountService;

  @Autowired ModelMapper modelMapper;

  @Autowired SecuritiesAccountRepository secAccountRepository;

  @Autowired AssetService assetService;

  @Autowired ShareholderService shareholderService;

  @Autowired IntermediaryService intermediaryService;

  @Autowired SecuritiesAccountQueryService secaccountQueryService;

  @Value("${spring.application.name}")
  private String applicationName;

  @PostMapping("/secaccounts")
  public ResponseEntity<List<SecuritiesAccountDTO>> createSecAccounts(
      @RequestBody SecuritiesAccountDTO secAccountDTO) throws URISyntaxException {
    if (secAccountDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new securities account cannot already have an ID", ENTITY_NAME, "idexists");
    }

    List<SecuritiesAccountDTO> result = secAccountService.save(secAccountDTO);

    // Création d'une liste de liens URI pour chaque SecuritiesAccountDTO créé
    List<URI> uris =
        result.stream()
            .map(
                dto -> {
                  try {
                    return new URI("/api/secaccounts/" + dto.getId());
                  } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                  }
                })
            .collect(Collectors.toList());

    HttpHeaders headers =
        HeaderUtil.createEntityCreationAlert(
            applicationName,
            true,
            ENTITY_NAME,
            result.stream().map(dto -> dto.getId().toString()).collect(Collectors.joining(", ")));

    return ResponseEntity.created(
            uris.get(0)) // Utilisation de l'URI du premier élément comme référence
        .headers(headers)
        .body(result);
  }

  @GetMapping("/secaccounts")
  public ResponseEntity<List<SecuritiesAccountDTO>> getAllSecAccounts(Pageable pageable) {
    Page<SecuritiesAccountDTO> page = secAccountService.findAll(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/secaccountswithoutpagination")
  public ResponseEntity<List<SecuritiesAccountDTO>> getAllissuers() {
    List<SecuritiesAccountDTO> SecuritiesAccountDTOs =
        secAccountRepository.findAll().stream()
            .map(x -> modelMapper.map(x, SecuritiesAccountDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(SecuritiesAccountDTOs);
  }

  // @GetMapping("/secaccounts/count")
  // public ResponseEntity<Long> countAll() {
  //   Long count = secAccountService.countAll();
  //   return ResponseEntity.ok().body(count);
  // }

  // @PutMapping("/secaccounts/{id}/toggleactive")
  // public ResponseEntity<SecuritiesAccountDTO> toggleActive(@PathVariable Long id) {
  //   SecuritiesAccount secaccount = secAccountService.toggleActive(id);
  //   if (secaccount != null) {
  //     List<IRL> irls =
  //         IRLRepository.findByBeneficiarySecurityAccountEqualsOrClientSecurityAccountEquals(
  //             secaccount.getAccountNumber(), secaccount.getAccountNumber());
  //     for (IRL irl : irls) {
  //       irl.setIsSecAccountActive(!irl.getIsSecAccountActive());
  //       IRLRepository.save(irl);
  //     }
  //     SecuritiesAccountDTO secaccountDTO =
  //         modelMapper.map(secaccount, SecuritiesAccountDTO.class);
  //     return ResponseEntity.ok().body(secaccountDTO);
  //   } else {
  //     return ResponseEntity.notFound().build();
  //   }
  // }

  @GetMapping("/secaccounts/{id}")
  public ResponseEntity<SecuritiesAccountDTO> getSecAccount(@PathVariable Long id) {
    Optional<SecuritiesAccountDTO> secaccountDTO = secAccountService.findOne(id);
    return ResponseUtil.wrapOrNotFound(secaccountDTO);
  }

  @GetMapping("/secaccountsAllQuery")
  public ResponseEntity<List<SecuritiesAccountDTO>> getAllMovements(
      SecuritiesAccountCriteria criteria, Pageable pageable) {
    Page<SecuritiesAccountDTO> page = secaccountQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/secaccounts/count")
  public ResponseEntity<Object> getTotalSecAccountsCount(SecuritiesAccountCriteria criteria) {
    return ResponseEntity.ok().body(secaccountQueryService.countByCriteria(criteria));
  }

  @GetMapping("/secaccounts/findBy")
  public ResponseEntity<SecuritiesAccountDTO> findBy(
      String isin, String shareholderRef, String intermediaryCode, AccountType accountType) {

    Asset asset = assetService.findByIsin(isin);
    Shareholder shareholder = shareholderService.findByReference(shareholderRef);
    Intermediary intermediary = intermediaryService.findByCode(intermediaryCode);

    SecuritiesAccount secAccount =
        secAccountService.findSecAccount(asset, shareholder, intermediary, accountType);
    SecuritiesAccountDTO secAccountDTO = modelMapper.map(secAccount, SecuritiesAccountDTO.class);
    return ResponseEntity.ok().body(secAccountDTO);
  }

  @GetMapping("/secaccounts/findByParams")
  public ResponseEntity<List<SecuritiesAccountDTO>> findByParams(
      @RequestParam String isin,
      @RequestParam String shareholderRef,
      @RequestParam(required = false) String intermediaryCode,
      @RequestParam AccountType accountType) {

    if (isin == null || shareholderRef == null) {
      throw new IllegalArgumentException("ISIN and Shareholder Reference are required.");
    }

    Asset asset =
        Optional.ofNullable(assetService.findByIsin(isin))
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found for ISIN: " + isin));

    Shareholder shareholder =
        Optional.ofNullable(shareholderService.findByReference(shareholderRef))
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Shareholder not found for reference: " + shareholderRef));

    Intermediary intermediary = null;
    if (intermediaryCode != null) {
      intermediary =
          Optional.ofNullable(intermediaryService.findByCode(intermediaryCode))
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Intermediary not found for code: " + intermediaryCode));
    }

    List<SecuritiesAccount> secAccount =
        secAccountService.findSecAccountsByParams(asset, shareholder, intermediary, accountType);

    List<SecuritiesAccountDTO> secAccountDTO =
        secAccount.stream()
            .map(x -> modelMapper.map(x, SecuritiesAccountDTO.class))
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(secAccountDTO);
  }

  @GetMapping("/secaccounts/exists")
  public ResponseEntity<Boolean> doesSecuritiesAccountExist(
      @RequestParam Long assetId,
      @RequestParam Long shareholderId,
      @RequestParam Long intermediaryId,
      @RequestParam Long accountCategoryId,
      @RequestParam(required = false) AccountType accountType) {
    boolean exists =
        secAccountService.securitiesAccountExist(
            assetId, shareholderId, intermediaryId, accountCategoryId, accountType);
    return ResponseEntity.ok(exists);
  }
}
