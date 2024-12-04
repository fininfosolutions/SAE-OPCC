package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.ClientSecAccount;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.repositories.ClientSecAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientSecAccountDTO;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.domains.SAEConfig;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.services.IntermediaryService;
import com.fininfo.saeopcc.shared.services.SAEConfigService;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientSecAccountService {
  @Autowired private ClientSecAccountRepository securitiesAccountRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired private AssetRepository assetRepository;
  @Autowired private SAEConfigService SAEConfigService;
  @Autowired private IntermediaryService intermediaryService;

  public List<ClientSecAccountDTO> save(ClientSecAccountDTO secAccountDTO) {
    List<ClientSecAccount> accounts = new ArrayList<>();
    List<ClientSecAccountDTO> result = new ArrayList<>();
    if (secAccountDTO != null
        && secAccountDTO.getIsPure()
        && secAccountDTO.getIntermediaryId() == null) {
      Optional<Intermediary> intermediaryOpt =
          Optional.ofNullable(intermediaryService.findByCode("000"));

      intermediaryOpt.ifPresent(
          intermediary -> {
            secAccountDTO.setIntermediaryId(intermediary.getId());
            secAccountDTO.setIntermediaryDescription(intermediary.getDescription());
            secAccountDTO.setIntermediaryAffiliedCode(intermediary.getAffiliedCode());
          });
    }
    Optional<Asset> assetOpt = assetRepository.findById(secAccountDTO.getAssetId());
    Asset asset = assetOpt.orElse(null);

    String assetReference = "";
    if (asset != null) {
      if (asset.getAssetType() == AssetType.FUND) {
        AssetDTO assetDTO = modelMapper.map(asset, AssetDTO.class);
        assetReference = assetDTO.getSdgReference();
      } else if (asset.getAssetType() == AssetType.EQUITY) {
        AssetDTO assetDTO = modelMapper.map(asset, AssetDTO.class);
        assetReference = assetDTO.getIssuerReference();
      }
    }

    SAEConfig saeconfig = SAEConfigService.getSAEConfigByCode(TenantContext.getTenantId());

    String intermediaryAffiliatedCode = secAccountDTO.getIntermediaryAffiliedCode();
    String formattedIFH =
        secAccountDTO.getIsPure()
            ? "000"
            : (intermediaryAffiliatedCode != null && intermediaryAffiliatedCode.length() >= 3)
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

    if (!"00".equals(secAccountDTO.getAccountCategoryCode())) {
      createSingleAccount(
          secAccountDTO, asset, saeconfig, formattedShareholderReference, formattedIFH, accounts);
    } else {
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
    }

    result =
        accounts.stream()
            .map(account -> modelMapper.map(account, ClientSecAccountDTO.class))
            .collect(Collectors.toList());

    return result;
  }

  private void createSingleAccount(
      ClientSecAccountDTO secAccountDTO,
      Asset asset,
      SAEConfig saeconfig,
      String formattedShareholderReference,
      String formattedIFH,
      List<ClientSecAccount> accounts) {
    String suffix = getSuffixFromAccountType(secAccountDTO.getAccountType());
    String accountNumber =
        saeconfig.getValue()
            + asset.getIsin().substring(asset.getIsin().length() - 5)
            + formattedShareholderReference
            + formattedIFH
            + suffix
            + secAccountDTO.getAccountCategoryCode();

    ClientSecAccount secAccount = modelMapper.map(secAccountDTO, ClientSecAccount.class);
    secAccount.setAccountNumber(accountNumber);
    secAccount.setAccountType(secAccountDTO.getAccountType());
    secAccount.setDescription(
        secAccountDTO.getDescription()
            + " "
            + secAccountDTO.getAccountType().name()
            + (secAccountDTO.getIsPure() ? " PUR" : " ADMINISTRÉ"));
    secAccount.setIsDisabled(false);
    secAccount.setIsActive(true);

    ClientSecAccount existingAccount =
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
      ClientSecAccountDTO secAccountDTO,
      Asset asset,
      SAEConfig saeconfig,
      String formattedShareholderReference,
      String formattedIFH,
      int accountTypeSuffix,
      List<ClientSecAccount> accounts) {
    ClientSecAccount secAccount = modelMapper.map(secAccountDTO, ClientSecAccount.class);

    String accountTypeDescription = getAccountTypeDescription(accountTypeSuffix);
    secAccount.setAccountType(AccountType.valueOf(accountTypeDescription));
    secAccount.setDescription(
        secAccountDTO.getDescription()
            + " "
            + accountTypeDescription
            + (secAccountDTO.getIsPure() ? " PUR" : " ADMINISTRÉ"));

    String accountNumber =
        saeconfig.getValue()
            + asset.getIsin().substring(asset.getIsin().length() - 5)
            + formattedShareholderReference
            + formattedIFH
            + accountTypeSuffix
            + "00";

    secAccount.setAccountNumber(accountNumber);
    secAccount.setIsDisabled(false);
    secAccount.setIsActive(true);

    ClientSecAccount existingAccount =
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
      default:
        throw new IllegalArgumentException("Invalid account type suffix: " + accountTypeSuffix);
    }
  }

  public Page<ClientSecAccountDTO> findAll(Pageable pageable) {
    return securitiesAccountRepository
        .findAll(pageable)
        .map(x -> modelMapper.map(x, ClientSecAccountDTO.class));
  }

  public Long countAll() {
    return securitiesAccountRepository.count();
  }

  public ClientSecAccount findSecAccount(
      Asset asset, Shareholder shareholder, Intermediary intermediary, AccountType accountType) {
    return securitiesAccountRepository.findSecAccountBy(
        asset.getId(), shareholder.getId(), intermediary.getId(), accountType.toString());
  }

  public ClientSecAccount findSecAccountByCategory(
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

  public ClientSecAccount toggleActive(Long entityId) {
    ClientSecAccount secaccount =
        securitiesAccountRepository
            .findById(entityId)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

    secaccount.setIsActive(!secaccount.getIsActive());

    return securitiesAccountRepository.save(secaccount);
  }

  public Optional<ClientSecAccountDTO> findOne(Long id) {
    return securitiesAccountRepository
        .findById(id)
        .map(secaccount -> modelMapper.map(secaccount, ClientSecAccountDTO.class));
  }

  public List<ClientSecAccount> findSecAccountsByParams(
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

  public Optional<ClientSecAccountDTO> findByAccountNumberAndIsActive(String accountNumber) {
    return securitiesAccountRepository
        .findByAccountNumberAndIsActiveTrue(accountNumber)
        .map(secaccount -> modelMapper.map(secaccount, ClientSecAccountDTO.class));
  }
}
