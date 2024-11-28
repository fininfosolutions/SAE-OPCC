package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.DayWeek;
import com.fininfo.saeopcc.shared.domains.enumeration.FundCategory;
import com.fininfo.saeopcc.shared.domains.enumeration.FundClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.FundForm;
import com.fininfo.saeopcc.shared.domains.enumeration.FundGuarantee;
import com.fininfo.saeopcc.shared.domains.enumeration.FundRate;
import com.fininfo.saeopcc.shared.domains.enumeration.FundStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.FundType;
import com.fininfo.saeopcc.shared.domains.enumeration.Periodicity;
import com.fininfo.saeopcc.shared.domains.enumeration.UnitCategory;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class FundDTO extends AssetDTO {

  private Long id;

  private String promoter;

  private LocalDate maturityDate;

  private Boolean knownNAV;

  private Boolean higherLimitCommercialFundSensibility;

  private Boolean lowerLimitCommercialFundSensibility;

  private Boolean investMoreThanFivePercent;

  private DayWeek dayNAV;
  private UnitCategory unitCategory;

  private Periodicity frequencyNAV;

  private BigDecimal netAssetValueAtIssuing;

  private BigDecimal rightExitAmount;

  private BigDecimal rightEnterAmount;

  private BigDecimal rightEnterPercentage;

  private BigDecimal rightExitPercentage;

  private BigDecimal fundSensibilityLowerLimit;

  private BigDecimal fundSensibilityHigherLimit;

  private BigDecimal subscriptionTerm;

  private BigDecimal refundTerm;

  private BigDecimal settlementSubscriptionTerm;

  private BigDecimal priceDivisionFactor;

  private BigDecimal priceNumber;

  private BigDecimal decimalizationUnity;

  private BigDecimal maxLimitByXOA;

  private FundClassification fundClassification;
  private FundCategory fundCategory;

  private Long fundOrganismId;

  private FundType fundType;

  private Long businessRiskCategoryId;

  private Long fundManagerId;

  private String businessRiskCategoryCode;

  private String securityTypeCode;

  private String custudianDescription;

  // private String fundOrganismCode;

  private FundRate fundRate;
  private FundGuarantee fundGuarantee;
  private FundStatus fundStatus;
  private FundForm fundForm;
  private String fundSdgDescription;
}
