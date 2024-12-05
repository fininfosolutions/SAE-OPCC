package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.DetentionForm;
import com.fininfo.saeopcc.shared.services.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecuritiesAccountDTO extends AccountDTO {

  private Long id;
  private AccountType accountType;
  private Long assetId;
  private String assetIsin;
  private String shareholderDescription;
  private String shareholderReference;
  private String assetIssuerDescription;
  private String assetSdgDescription;
  private String assetSdgReference;
  private DetentionForm assetDetentionForm;
  private String assetReference;
  private String intermediaryAffiliedCode;
  private String intermediaryDescription;
  private AssetType assetAssetType;
  private String assetIssuerReference;
  private String assetDescription;
  private Long intermediaryId;
  private Long shareholderId;
  private Long accountCategoryId;
  private String accountCategoryCode;
}
