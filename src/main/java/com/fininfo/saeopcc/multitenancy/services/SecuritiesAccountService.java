package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountDTO;
import com.fininfo.saeopcc.shared.domains.AccountCategory;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.domains.SAEConfig;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.repositories.AccountCategoryRepository;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.services.SAEConfigService;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecuritiesAccountService {
  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired private AssetRepository assetRepository;
  @Autowired private SAEConfigService SAEConfigService;
  @Autowired private AccountCategoryRepository accountCategoryRepository;
  private static final List<AccountType> VALID_ACCOUNT_TYPES =
      List.of(AccountType.SOUS, AccountType.APPELE, AccountType.LIBERE);
  private static final List<AccountType> old_ACCOUNT_TYPES =
      List.of(AccountType.BLOQUE, AccountType.LIBRE, AccountType.NANTI);

  public List<SecuritiesAccountDTO> save(SecuritiesAccountDTO secAccountDTO) {
    List<SecuritiesAccount> accounts = new ArrayList<>();
    List<SecuritiesAccountDTO> result = new ArrayList<>();
    Optional<Asset> assetOpt = assetRepository.findById(secAccountDTO.getAssetId());
    Asset asset = assetOpt.orElse(null);

    SAEConfig saeconfig = SAEConfigService.getSAEConfigByCode(TenantContext.getTenantId());

    String intermediaryAffiliatedCode = secAccountDTO.getIntermediaryAffiliedCode();
    String formattedIFH =
        (intermediaryAffiliatedCode != null && intermediaryAffiliatedCode.length() >= 3)
            ? intermediaryAffiliatedCode.substring(intermediaryAffiliatedCode.length() - 3)
            : "";

    String shareholderReference = secAccountDTO.getShareholderReference();
    String formattedShareholderReference =
        String.format(
            "%05d",
            shareholderReference.length() >= 5
                ? Integer.parseInt(
                    shareholderReference.substring(shareholderReference.length() - 5))
                : Integer.parseInt("0" + shareholderReference));

    if ((!"00".equals(secAccountDTO.getAccountCategoryCode()))
        && (!"20".equals(secAccountDTO.getAccountCategoryCode()))) {
      createSingleAccount(
          secAccountDTO, asset, saeconfig, formattedShareholderReference, formattedIFH, accounts);
    } else {
      if ("00".equals(secAccountDTO.getAccountCategoryCode())) {
        for (int i = 1; i <= 3; i++) {
          createMultipleAccounts(
              secAccountDTO,
              asset,
              saeconfig,
              formattedShareholderReference,
              formattedIFH,
              i,
              accounts);
        }
      } else if ("20".equals(secAccountDTO.getAccountCategoryCode())) {
        for (int i = 4; i <= 6; i++) {
          createMultipleAccounts(
              secAccountDTO,
              asset,
              saeconfig,
              formattedShareholderReference,
              formattedIFH,
              i,
              accounts);
        }
      }
    }

    result =
        accounts.stream()
            .map(account -> modelMapper.map(account, SecuritiesAccountDTO.class))
            .collect(Collectors.toList());

    return result;
  }

  private void createSingleAccount(
      SecuritiesAccountDTO secAccountDTO,
      Asset asset,
      SAEConfig saeconfig,
      String formattedShareholderReference,
      String formattedIFH,
      List<SecuritiesAccount> accounts) {
    String suffix = getSuffixFromAccountType(secAccountDTO.getAccountType());
    String accountNumber =
        saeconfig.getValue()
            + asset.getIsin().substring(asset.getIsin().length() - 5)
            + formattedShareholderReference
            + formattedIFH
            + suffix
            + secAccountDTO.getAccountCategoryCode();

    SecuritiesAccount secAccount = modelMapper.map(secAccountDTO, SecuritiesAccount.class);
    secAccount.setAccountNumber(accountNumber);
    secAccount.setAccountType(secAccountDTO.getAccountType());
    secAccount.setDescription(
        secAccountDTO.getDescription() + " " + secAccountDTO.getAccountType().name());
    // secAccount.setIsDisabled(false);
    secAccount.setIsActive(true);

    SecuritiesAccount existingAccount =
        securitiesAccountRepository
            .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountTypeAndAccountCategory_id(
                asset.getId(),
                secAccountDTO.getShareholderId(),
                secAccountDTO.getIntermediaryId(),
                secAccountDTO.getAccountType(),
                secAccountDTO.getAccountCategoryId());

    if (existingAccount != null) {
      throw new CustomUniqueConstraintViolationException(
          "Le numéro de compte " + accountNumber + " doit être unique.");
    }

    accounts.add(securitiesAccountRepository.save(secAccount));
  }

  private void createMultipleAccounts(
      SecuritiesAccountDTO secAccountDTO,
      Asset asset,
      SAEConfig saeconfig,
      String formattedShareholderReference,
      String formattedIFH,
      int accountTypeSuffix,
      List<SecuritiesAccount> accounts) {
    SecuritiesAccount secAccount = modelMapper.map(secAccountDTO, SecuritiesAccount.class);

    String accountTypeDescription = getAccountTypeDescription(accountTypeSuffix);
    secAccount.setAccountType(AccountType.valueOf(accountTypeDescription));
    secAccount.setDescription(secAccountDTO.getDescription() + " " + accountTypeDescription);

    String accountNumber =
        saeconfig.getValue()
            + asset.getIsin().substring(asset.getIsin().length() - 5)
            + formattedShareholderReference
            + formattedIFH
            + accountTypeSuffix
            + secAccountDTO.getAccountCategoryCode();

    secAccount.setAccountNumber(accountNumber);
    // secAccount.setIsDisabled(false);
    secAccount.setIsActive(true);

    SecuritiesAccount existingAccount =
        securitiesAccountRepository
            .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountTypeAndAccountCategory_id(
                asset.getId(),
                secAccountDTO.getShareholderId(),
                secAccountDTO.getIntermediaryId(),
                secAccount.getAccountType(),
                secAccountDTO.getAccountCategoryId());

    if (existingAccount != null) {
      throw new CustomUniqueConstraintViolationException(
          "Le numéro de compte " + accountNumber + " doit être unique.");
    }

    accounts.add(securitiesAccountRepository.save(secAccount));
  }

  private String getSuffixFromAccountType(AccountType accountType) {
    switch (accountType) {
      case LIBRE:
        return "1";
      case NANTI:
        return "2";
      case BLOQUE:
        return "3";
      case SOUS:
        return "4";
      case APPELE:
        return "5";
      case LIBERE:
        return "6";

      default:
        return "";
    }
  }

  private String getAccountTypeDescription(int accountTypeSuffix) {
    switch (accountTypeSuffix) {
      case 1:
        return "LIBRE";
      case 2:
        return "NANTI";
      case 3:
        return "BLOQUE";
      case 4:
        return "SOUS";
      case 5:
        return "APPELE";
      case 6:
        return "LIBERE";

      default:
        throw new IllegalArgumentException("Invalid account type suffix: " + accountTypeSuffix);
    }
  }

  public Page<SecuritiesAccountDTO> findAll(Pageable pageable) {
    return securitiesAccountRepository
        .findAll(pageable)
        .map(x -> modelMapper.map(x, SecuritiesAccountDTO.class));
  }

  public Long countAll() {
    return securitiesAccountRepository.count();
  }

  public SecuritiesAccount findSecAccount(
      Asset asset, Shareholder shareholder, Intermediary intermediary, AccountType accountType) {
    return securitiesAccountRepository.findSecAccountBy(
        asset.getId(), shareholder.getId(), intermediary.getId(), accountType.toString());
  }

  public SecuritiesAccount findSecAccountByCategory(
      Asset asset,
      Shareholder shareholder,
      Intermediary intermediary,
      AccountType accountType,
      String categoryCode) {
    return securitiesAccountRepository.findSecAccountByWithCategory(
        asset.getId(),
        shareholder.getId(),
        intermediary != null ? intermediary.getId() : null,
        accountType.toString(),
        categoryCode);
  }

  public SecuritiesAccount toggleActive(Long entityId) {
    SecuritiesAccount secaccount =
        securitiesAccountRepository
            .findById(entityId)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

    secaccount.setIsActive(!secaccount.getIsActive());

    return securitiesAccountRepository.save(secaccount);
  }

  public Optional<SecuritiesAccountDTO> findOne(Long id) {
    return securitiesAccountRepository
        .findById(id)
        .map(secaccount -> modelMapper.map(secaccount, SecuritiesAccountDTO.class));
  }

  public List<SecuritiesAccount> findSecAccountsByParams(
      Asset asset, Shareholder shareholder, Intermediary intermediary, AccountType accountType) {

    if (intermediary != null) {
      return securitiesAccountRepository
          .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
              asset.getId(), shareholder.getId(), intermediary.getId(), accountType);
    } else {
      return securitiesAccountRepository.findByAsset_IdAndShareholder_IdAndAccountType(
          asset.getId(), shareholder.getId(), accountType);
    }
  }

  public Optional<SecuritiesAccountDTO> findByAccountNumberAndIsActive(String accountNumber) {
    return securitiesAccountRepository
        .findByAccountNumberAndIsActiveTrue(accountNumber)
        .map(secaccount -> modelMapper.map(secaccount, SecuritiesAccountDTO.class));
  }

  public boolean securitiesAccountExist(
      Long assetId,
      Long shareholderId,
      Long intermediaryId,
      Long accountCategoryId,
      AccountType accountType) {

    AccountCategory accountCategory =
        accountCategoryRepository.findById(accountCategoryId).orElse(null);
    if (accountCategory != null && "00".equals(accountCategory.getDescription())) {
      return securitiesAccountRepository
          .existsByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountTypeIn(
              assetId, shareholderId, intermediaryId, old_ACCOUNT_TYPES);
    }
    if (accountCategory != null && "20".equals(accountCategory.getDescription())) {
      return securitiesAccountRepository
          .existsByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountTypeIn(
              assetId, shareholderId, intermediaryId, VALID_ACCOUNT_TYPES);
    } else {

      return securitiesAccountRepository
          .existsByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
              assetId, shareholderId, intermediaryId, accountType);
    }
  }
}
