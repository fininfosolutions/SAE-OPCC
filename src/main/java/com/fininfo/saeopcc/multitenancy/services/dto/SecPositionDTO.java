package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecPositionDTO extends PositionDTO {

  private Long id;

  private BigDecimal quantity;
  private String assetCode;
  private Long assetId;

  private String assetIsin;

  private String assetDescription;

  private Boolean assetInscriMaroclear;

  private AssetType assetAssetType;

  private String assetReference;
  private AccountType accountAccountType;
}
