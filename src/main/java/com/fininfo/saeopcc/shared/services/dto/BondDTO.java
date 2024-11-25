package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class BondDTO extends AssetDTO {

  private Long id;

  private Boolean indexed;

  private AmortizationType amortizationType;
  private LocalDate datedDate;

  private String reference;
  private BondNature bondNature;

  private Boolean listed;
  private PaiementStatusBond paiementStatus;

  private Boolean eligibilityIndicator;

  private LocalDate maturityDate;

  private AdvanceType advanceType;

  private Periodicity periodicity;

  private BigDecimal rate;

  private LocalDate delistingDate;

  private Boolean convertibilityIndicator;

  private Boolean subordinationIndicator;

  private Boolean amortizationTableManagement;

  private Boolean variableRate;

  private BigDecimal poolFactor;

  private RateType rateType;

  private BigDecimal riskPremium;

  private BondClassification bondClassification;

  private Guarantee guarantee;
  private DayBase dayBase;
  private RoundingMode roundingMode;
  private BondCategory bondCategory;
}
