package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.EquityClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.Holding;
import com.fininfo.saeopcc.shared.domains.enumeration.PaiementStatusEquity;
import com.fininfo.saeopcc.shared.domains.enumeration.VotingRight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class EquityDTO extends AssetDTO {

  private Long id;

  private Long issuerRiskCategoryId;

  private EquityClassification equityClassification;

  private VotingRight votingRight;

  private PaiementStatusEquity paiementStatus;

  private Holding holding;

  private String securityTypeCode;

  private Boolean socialPart;

  private String custudianDescription;
}
