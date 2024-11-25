package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.DayWeek;
import com.fininfo.saeopcc.shared.domains.enumeration.DetentionForm;
import com.fininfo.saeopcc.shared.domains.enumeration.FundClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.FundType;
import com.fininfo.saeopcc.shared.domains.enumeration.Periodicity;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria.MarketTypeFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Fund} entity. This class is used
 * in {@link com.fininfo.saeopcc.shared.controllers.FundResource} to receive all the possible filtering
 * options from the Http GET request parameters. For example the following could be a valid request:
 * {@code /funds?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class FundCriteria implements Serializable, Criteria {
  /** Class for filtering DayWeek */
  public static class DayWeekFilter extends Filter<DayWeek> {

    public DayWeekFilter() {}

    public DayWeekFilter(DayWeekFilter filter) {
      super(filter);
    }

    @Override
    public DayWeekFilter copy() {
      return new DayWeekFilter(this);
    }
  }

  /** Class for filtering Periodicity */
  public static class PeriodicityFilter extends Filter<Periodicity> {

    public PeriodicityFilter() {}

    public PeriodicityFilter(PeriodicityFilter filter) {
      super(filter);
    }

    @Override
    public PeriodicityFilter copy() {
      return new PeriodicityFilter(this);
    }
  }

  /** Class for filtering Periodicity */
  public static class SecurityFormFilter extends Filter<SecurityForm> {

    public SecurityFormFilter() {}

    public SecurityFormFilter(SecurityFormFilter filter) {
      super(filter);
    }

    @Override
    public SecurityFormFilter copy() {
      return new SecurityFormFilter(this);
    }
  }

  public static class DetentionFormFilter extends Filter<DetentionForm> {

    public DetentionFormFilter() {}

    public DetentionFormFilter(DetentionFormFilter filter) {
      super(filter);
    }

    @Override
    public DetentionFormFilter copy() {
      return new DetentionFormFilter(this);
    }
  }

  public static class FundClassificationFilter extends Filter<FundClassification> {

    public FundClassificationFilter() {}

    public FundClassificationFilter(FundClassificationFilter filter) {
      super(filter);
    }

    @Override
    public FundClassificationFilter copy() {
      return new FundClassificationFilter(this);
    }
  }

  /** Class for filtering Periodicity */
  public static class FundTypeFilter extends Filter<FundType> {

    public FundTypeFilter() {}

    public FundTypeFilter(FundTypeFilter filter) {
      super(filter);
    }

    @Override
    public FundTypeFilter copy() {
      return new FundTypeFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter isin;

  private StringFilter reference;
  private SecurityFormFilter securityForm;
  private DetentionFormFilter detentionForm;
  private MarketTypeFilter marketType;

  public MarketTypeFilter getMarketType() {
    return marketType;
  }

  public void setMarketType(MarketTypeFilter marketType) {
    this.marketType = marketType;
  }

  public DetentionFormFilter getDetentionForm() {
    return detentionForm;
  }

  public void setDetentionForm(DetentionFormFilter detentionForm) {
    this.detentionForm = detentionForm;
  }

  private LocalDateFilter premiumDate;

  public LocalDateFilter getPremiumDate() {
    return premiumDate;
  }

  public void setPremiumDate(LocalDateFilter premiumDate) {
    this.premiumDate = premiumDate;
  }

  public SecurityFormFilter getSecurityForm() {
    return securityForm;
  }

  public void setSecurityForm(SecurityFormFilter securityForm) {
    this.securityForm = securityForm;
  }

  public StringFilter getReference() {
    return reference;
  }

  public void setReference(StringFilter reference) {
    this.reference = reference;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  private StringFilter shortCode;
  private StringFilter code;

  public StringFilter getCode() {
    return code;
  }

  public void setCode(StringFilter code) {
    this.code = code;
  }

  private StringFilter description;

  private StringFilter aditionalDescription;

  public StringFilter getAditionalDescription() {
    return aditionalDescription;
  }

  public void setAditionalDescription(StringFilter aditionalDescription) {
    this.aditionalDescription = aditionalDescription;
  }

  private StringFilter promoter;

  public StringFilter getIsin() {
    return isin;
  }

  public void setIsin(StringFilter isin) {
    this.isin = isin;
  }

  public StringFilter getShortCode() {
    return shortCode;
  }

  public void setShortCode(StringFilter shortCode) {
    this.shortCode = shortCode;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  private LocalDateFilter maturityDate;

  private BooleanFilter knownNAV;

  private BooleanFilter higherLimitCommercialFundSensibility;

  private BooleanFilter lowerLimitCommercialFundSensibility;

  private BooleanFilter investMoreThanFivePercent;

  private DayWeekFilter dayNAV;

  private PeriodicityFilter frequencyNAV;

  private BigDecimalFilter netAssetValueAtIssuing;

  private BigDecimalFilter rightExitAmount;

  private BigDecimalFilter rightEnterAmount;

  private BigDecimalFilter rightEnterPercentage;

  private BigDecimalFilter rightExitPercentage;

  private BigDecimalFilter fundSensibilityLowerLimit;

  private BigDecimalFilter fundSensibilityHigherLimit;

  private BigDecimalFilter subscriptionTerm;

  private BigDecimalFilter refundTerm;

  private BigDecimalFilter settlementSubscriptionTerm;

  private BigDecimalFilter priceDivisionFactor;

  private BigDecimalFilter priceNumber;

  private BigDecimalFilter decimalizationUnity;

  private BigDecimalFilter maxLimitByXOA;

  private FundClassificationFilter fundClassification;

  private LongFilter fundOrganismId;

  private FundTypeFilter fundType;

  private LongFilter businessRiskCategoryId;
  private BooleanFilter active;

  public FundCriteria() {}

  public FundCriteria(FundCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.promoter = other.promoter == null ? null : other.promoter.copy();
    this.maturityDate = other.maturityDate == null ? null : other.maturityDate.copy();
    this.knownNAV = other.knownNAV == null ? null : other.knownNAV.copy();
    this.higherLimitCommercialFundSensibility =
        other.higherLimitCommercialFundSensibility == null
            ? null
            : other.higherLimitCommercialFundSensibility.copy();
    this.lowerLimitCommercialFundSensibility =
        other.lowerLimitCommercialFundSensibility == null
            ? null
            : other.lowerLimitCommercialFundSensibility.copy();
    this.investMoreThanFivePercent =
        other.investMoreThanFivePercent == null ? null : other.investMoreThanFivePercent.copy();
    this.dayNAV = other.dayNAV == null ? null : other.dayNAV.copy();
    this.frequencyNAV = other.frequencyNAV == null ? null : other.frequencyNAV.copy();
    this.netAssetValueAtIssuing =
        other.netAssetValueAtIssuing == null ? null : other.netAssetValueAtIssuing.copy();
    this.rightExitAmount = other.rightExitAmount == null ? null : other.rightExitAmount.copy();
    this.rightEnterAmount = other.rightEnterAmount == null ? null : other.rightEnterAmount.copy();
    this.rightEnterPercentage =
        other.rightEnterPercentage == null ? null : other.rightEnterPercentage.copy();
    this.rightExitPercentage =
        other.rightExitPercentage == null ? null : other.rightExitPercentage.copy();
    this.fundSensibilityLowerLimit =
        other.fundSensibilityLowerLimit == null ? null : other.fundSensibilityLowerLimit.copy();
    this.fundSensibilityHigherLimit =
        other.fundSensibilityHigherLimit == null ? null : other.fundSensibilityHigherLimit.copy();
    this.subscriptionTerm = other.subscriptionTerm == null ? null : other.subscriptionTerm.copy();
    this.refundTerm = other.refundTerm == null ? null : other.refundTerm.copy();
    this.settlementSubscriptionTerm =
        other.settlementSubscriptionTerm == null ? null : other.settlementSubscriptionTerm.copy();
    this.priceDivisionFactor =
        other.priceDivisionFactor == null ? null : other.priceDivisionFactor.copy();
    this.priceNumber = other.priceNumber == null ? null : other.priceNumber.copy();
    this.decimalizationUnity =
        other.decimalizationUnity == null ? null : other.decimalizationUnity.copy();
    this.maxLimitByXOA = other.maxLimitByXOA == null ? null : other.maxLimitByXOA.copy();
    this.fundClassification =
        other.fundClassification == null ? null : other.fundClassification.copy();
    this.fundOrganismId = other.fundOrganismId == null ? null : other.fundOrganismId.copy();
    this.fundType = other.fundType == null ? null : other.fundType.copy();
    this.businessRiskCategoryId =
        other.businessRiskCategoryId == null ? null : other.businessRiskCategoryId.copy();
  }

  @Override
  public FundCriteria copy() {
    return new FundCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getPromoter() {
    return promoter;
  }

  public void setPromoter(StringFilter promoter) {
    this.promoter = promoter;
  }

  public LocalDateFilter getMaturityDate() {
    return maturityDate;
  }

  public void setMaturityDate(LocalDateFilter maturityDate) {
    this.maturityDate = maturityDate;
  }

  public BooleanFilter getKnownNAV() {
    return knownNAV;
  }

  public void setKnownNAV(BooleanFilter knownNAV) {
    this.knownNAV = knownNAV;
  }

  public BooleanFilter getHigherLimitCommercialFundSensibility() {
    return higherLimitCommercialFundSensibility;
  }

  public void setHigherLimitCommercialFundSensibility(
      BooleanFilter higherLimitCommercialFundSensibility) {
    this.higherLimitCommercialFundSensibility = higherLimitCommercialFundSensibility;
  }

  public BooleanFilter getLowerLimitCommercialFundSensibility() {
    return lowerLimitCommercialFundSensibility;
  }

  public void setLowerLimitCommercialFundSensibility(
      BooleanFilter lowerLimitCommercialFundSensibility) {
    this.lowerLimitCommercialFundSensibility = lowerLimitCommercialFundSensibility;
  }

  public BooleanFilter getInvestMoreThanFivePercent() {
    return investMoreThanFivePercent;
  }

  public void setInvestMoreThanFivePercent(BooleanFilter investMoreThanFivePercent) {
    this.investMoreThanFivePercent = investMoreThanFivePercent;
  }

  public DayWeekFilter getDayNAV() {
    return dayNAV;
  }

  public void setDayNAV(DayWeekFilter dayNAV) {
    this.dayNAV = dayNAV;
  }

  public PeriodicityFilter getFrequencyNAV() {
    return frequencyNAV;
  }

  public void setFrequencyNAV(PeriodicityFilter frequencyNAV) {
    this.frequencyNAV = frequencyNAV;
  }

  public BigDecimalFilter getNetAssetValueAtIssuing() {
    return netAssetValueAtIssuing;
  }

  public void setNetAssetValueAtIssuing(BigDecimalFilter netAssetValueAtIssuing) {
    this.netAssetValueAtIssuing = netAssetValueAtIssuing;
  }

  public BigDecimalFilter getRightExitAmount() {
    return rightExitAmount;
  }

  public void setRightExitAmount(BigDecimalFilter rightExitAmount) {
    this.rightExitAmount = rightExitAmount;
  }

  public BigDecimalFilter getRightEnterAmount() {
    return rightEnterAmount;
  }

  public void setRightEnterAmount(BigDecimalFilter rightEnterAmount) {
    this.rightEnterAmount = rightEnterAmount;
  }

  public BigDecimalFilter getRightEnterPercentage() {
    return rightEnterPercentage;
  }

  public void setRightEnterPercentage(BigDecimalFilter rightEnterPercentage) {
    this.rightEnterPercentage = rightEnterPercentage;
  }

  public BigDecimalFilter getRightExitPercentage() {
    return rightExitPercentage;
  }

  public void setRightExitPercentage(BigDecimalFilter rightExitPercentage) {
    this.rightExitPercentage = rightExitPercentage;
  }

  public BigDecimalFilter getFundSensibilityLowerLimit() {
    return fundSensibilityLowerLimit;
  }

  public void setFundSensibilityLowerLimit(BigDecimalFilter fundSensibilityLowerLimit) {
    this.fundSensibilityLowerLimit = fundSensibilityLowerLimit;
  }

  public BigDecimalFilter getFundSensibilityHigherLimit() {
    return fundSensibilityHigherLimit;
  }

  public void setFundSensibilityHigherLimit(BigDecimalFilter fundSensibilityHigherLimit) {
    this.fundSensibilityHigherLimit = fundSensibilityHigherLimit;
  }

  public BigDecimalFilter getSubscriptionTerm() {
    return subscriptionTerm;
  }

  public void setSubscriptionTerm(BigDecimalFilter subscriptionTerm) {
    this.subscriptionTerm = subscriptionTerm;
  }

  public BigDecimalFilter getRefundTerm() {
    return refundTerm;
  }

  public void setRefundTerm(BigDecimalFilter refundTerm) {
    this.refundTerm = refundTerm;
  }

  public BigDecimalFilter getSettlementSubscriptionTerm() {
    return settlementSubscriptionTerm;
  }

  public void setSettlementSubscriptionTerm(BigDecimalFilter settlementSubscriptionTerm) {
    this.settlementSubscriptionTerm = settlementSubscriptionTerm;
  }

  public BigDecimalFilter getPriceDivisionFactor() {
    return priceDivisionFactor;
  }

  public void setPriceDivisionFactor(BigDecimalFilter priceDivisionFactor) {
    this.priceDivisionFactor = priceDivisionFactor;
  }

  public BigDecimalFilter getPriceNumber() {
    return priceNumber;
  }

  public void setPriceNumber(BigDecimalFilter priceNumber) {
    this.priceNumber = priceNumber;
  }

  public BigDecimalFilter getDecimalizationUnity() {
    return decimalizationUnity;
  }

  public void setDecimalizationUnity(BigDecimalFilter decimalizationUnity) {
    this.decimalizationUnity = decimalizationUnity;
  }

  public BigDecimalFilter getMaxLimitByXOA() {
    return maxLimitByXOA;
  }

  public void setMaxLimitByXOA(BigDecimalFilter maxLimitByXOA) {
    this.maxLimitByXOA = maxLimitByXOA;
  }

  public FundClassificationFilter getFundClassification() {
    return fundClassification;
  }

  public void setFundClassification(FundClassificationFilter fundClassification) {
    this.fundClassification = fundClassification;
  }

  public LongFilter getFundOrganismId() {
    return fundOrganismId;
  }

  public void setFundOrganismId(LongFilter fundOrganismId) {
    this.fundOrganismId = fundOrganismId;
  }

  public FundTypeFilter getFundType() {
    return fundType;
  }

  public void setFundType(FundTypeFilter fundType) {
    this.fundType = fundType;
  }

  public LongFilter getBusinessRiskCategoryId() {
    return businessRiskCategoryId;
  }

  public void setBusinessRiskCategoryId(LongFilter businessRiskCategoryId) {
    this.businessRiskCategoryId = businessRiskCategoryId;
  }

  public BooleanFilter getActive() {
    return active;
  }

  public void setActive(BooleanFilter active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final FundCriteria that = (FundCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(promoter, that.promoter)
        && Objects.equals(maturityDate, that.maturityDate)
        && Objects.equals(knownNAV, that.knownNAV)
        && Objects.equals(
            higherLimitCommercialFundSensibility, that.higherLimitCommercialFundSensibility)
        && Objects.equals(
            lowerLimitCommercialFundSensibility, that.lowerLimitCommercialFundSensibility)
        && Objects.equals(investMoreThanFivePercent, that.investMoreThanFivePercent)
        && Objects.equals(dayNAV, that.dayNAV)
        && Objects.equals(frequencyNAV, that.frequencyNAV)
        && Objects.equals(netAssetValueAtIssuing, that.netAssetValueAtIssuing)
        && Objects.equals(rightExitAmount, that.rightExitAmount)
        && Objects.equals(rightEnterAmount, that.rightEnterAmount)
        && Objects.equals(rightEnterPercentage, that.rightEnterPercentage)
        && Objects.equals(rightExitPercentage, that.rightExitPercentage)
        && Objects.equals(fundSensibilityLowerLimit, that.fundSensibilityLowerLimit)
        && Objects.equals(fundSensibilityHigherLimit, that.fundSensibilityHigherLimit)
        && Objects.equals(subscriptionTerm, that.subscriptionTerm)
        && Objects.equals(refundTerm, that.refundTerm)
        && Objects.equals(settlementSubscriptionTerm, that.settlementSubscriptionTerm)
        && Objects.equals(priceDivisionFactor, that.priceDivisionFactor)
        && Objects.equals(priceNumber, that.priceNumber)
        && Objects.equals(decimalizationUnity, that.decimalizationUnity)
        && Objects.equals(maxLimitByXOA, that.maxLimitByXOA)
        && Objects.equals(fundClassification, that.fundClassification)
        && Objects.equals(fundOrganismId, that.fundOrganismId)
        && Objects.equals(fundType, that.fundType)
        && Objects.equals(businessRiskCategoryId, that.businessRiskCategoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        promoter,
        maturityDate,
        knownNAV,
        higherLimitCommercialFundSensibility,
        lowerLimitCommercialFundSensibility,
        investMoreThanFivePercent,
        dayNAV,
        frequencyNAV,
        netAssetValueAtIssuing,
        rightExitAmount,
        rightEnterAmount,
        rightEnterPercentage,
        rightExitPercentage,
        fundSensibilityLowerLimit,
        fundSensibilityHigherLimit,
        subscriptionTerm,
        refundTerm,
        settlementSubscriptionTerm,
        priceDivisionFactor,
        priceNumber,
        decimalizationUnity,
        maxLimitByXOA,
        fundClassification,
        fundOrganismId,
        fundType,
        businessRiskCategoryId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FundCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (promoter != null ? "promoter=" + promoter + ", " : "")
        + (maturityDate != null ? "maturityDate=" + maturityDate + ", " : "")
        + (knownNAV != null ? "knownNAV=" + knownNAV + ", " : "")
        + (higherLimitCommercialFundSensibility != null
            ? "higherLimitCommercialFundSensibility=" + higherLimitCommercialFundSensibility + ", "
            : "")
        + (lowerLimitCommercialFundSensibility != null
            ? "lowerLimitCommercialFundSensibility=" + lowerLimitCommercialFundSensibility + ", "
            : "")
        + (investMoreThanFivePercent != null
            ? "investMoreThanFivePercent=" + investMoreThanFivePercent + ", "
            : "")
        + (dayNAV != null ? "dayNAV=" + dayNAV + ", " : "")
        + (frequencyNAV != null ? "frequencyNAV=" + frequencyNAV + ", " : "")
        + (netAssetValueAtIssuing != null
            ? "netAssetValueAtIssuing=" + netAssetValueAtIssuing + ", "
            : "")
        + (rightExitAmount != null ? "rightExitAmount=" + rightExitAmount + ", " : "")
        + (rightEnterAmount != null ? "rightEnterAmount=" + rightEnterAmount + ", " : "")
        + (rightEnterPercentage != null
            ? "rightEnterPercentage=" + rightEnterPercentage + ", "
            : "")
        + (rightExitPercentage != null ? "rightExitPercentage=" + rightExitPercentage + ", " : "")
        + (fundSensibilityLowerLimit != null
            ? "fundSensibilityLowerLimit=" + fundSensibilityLowerLimit + ", "
            : "")
        + (fundSensibilityHigherLimit != null
            ? "fundSensibilityHigherLimit=" + fundSensibilityHigherLimit + ", "
            : "")
        + (subscriptionTerm != null ? "subscriptionTerm=" + subscriptionTerm + ", " : "")
        + (refundTerm != null ? "refundTerm=" + refundTerm + ", " : "")
        + (settlementSubscriptionTerm != null
            ? "settlementSubscriptionTerm=" + settlementSubscriptionTerm + ", "
            : "")
        + (priceDivisionFactor != null ? "priceDivisionFactor=" + priceDivisionFactor + ", " : "")
        + (priceNumber != null ? "priceNumber=" + priceNumber + ", " : "")
        + (decimalizationUnity != null ? "decimalizationUnity=" + decimalizationUnity + ", " : "")
        + (maxLimitByXOA != null ? "maxLimitByXOA=" + maxLimitByXOA + ", " : "")
        + (fundClassification != null ? "fundClassification=" + fundClassification + ", " : "")
        + (fundOrganismId != null ? "fundOrganismId=" + fundOrganismId + ", " : "")
        + (fundType != null ? "fundType=" + fundType + ", " : "")
        + (businessRiskCategoryId != null
            ? "businessRiskCategoryId=" + businessRiskCategoryId + ", "
            : "")
        + "}";
  }
}
