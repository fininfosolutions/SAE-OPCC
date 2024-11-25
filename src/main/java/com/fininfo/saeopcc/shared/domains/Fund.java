package com.fininfo.saeopcc.shared.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fund", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Fund extends Asset {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "promoter")
  private String promoter;

  @Column(name = "maturity_date")
  private LocalDate maturityDate;

  @Column(name = "known_nav")
  private Boolean knownNAV;

  @Column(name = "higher_limit_commercial_fund_sensibility")
  private Boolean higherLimitCommercialFundSensibility;

  @Column(name = "lower_limit_commercial_fund_sensibility")
  private Boolean lowerLimitCommercialFundSensibility;

  @Column(name = "invest_more_than_five_percent")
  private Boolean investMoreThanFivePercent;

  @Enumerated(EnumType.STRING)
  @Column(name = "day_nav")
  private DayWeek dayNAV;

  @Enumerated(EnumType.STRING)
  @Column(name = "frequency_nav")
  private Periodicity frequencyNAV;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_classification")
  private FundClassification fundClassification;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_categorie")
  private FundCategory fundCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_type")
  private FundType fundType;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_rate")
  private FundRate fundRate;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_guarantee")
  private FundGuarantee fundGuarantee;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_status")
  private FundStatus fundStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "fund_form")
  private FundForm fundForm;

  @Enumerated(EnumType.STRING)
  @Column(name = "unit_category")
  private UnitCategory unitCategory;

  @Column(name = "net_asset_value_at_issuing", precision = 21, scale = 2)
  private BigDecimal netAssetValueAtIssuing;

  @Column(name = "right_exit_amount", precision = 21, scale = 2)
  private BigDecimal rightExitAmount;

  @Column(name = "right_enter_amount", precision = 21, scale = 2)
  private BigDecimal rightEnterAmount;

  @Column(name = "right_enter_percentage", precision = 21, scale = 2)
  private BigDecimal rightEnterPercentage;

  @Column(name = "right_exit_percentage", precision = 21, scale = 2)
  private BigDecimal rightExitPercentage;

  @Column(name = "fund_sensibility_lower_limit", precision = 21, scale = 2)
  private BigDecimal fundSensibilityLowerLimit;

  @Column(name = "fund_sensibility_higher_limit", precision = 21, scale = 2)
  private BigDecimal fundSensibilityHigherLimit;

  @Column(name = "subscription_term", precision = 21, scale = 2)
  private BigDecimal subscriptionTerm;

  @Column(name = "refund_term", precision = 21, scale = 2)
  private BigDecimal refundTerm;

  @Column(name = "settlement_subscription_term", precision = 21, scale = 2)
  private BigDecimal settlementSubscriptionTerm;

  @Column(name = "price_division_factor", precision = 21, scale = 2)
  private BigDecimal priceDivisionFactor;

  @Column(name = "price_number", precision = 21, scale = 2)
  private BigDecimal priceNumber;

  @Column(name = "decimalization_unity", precision = 21, scale = 2)
  private BigDecimal decimalizationUnity;

  @Column(name = "max_limit_by_xoa", precision = 21, scale = 2)
  private BigDecimal maxLimitByXOA;

  @ManyToOne
  @JsonIgnoreProperties(value = "funds", allowSetters = true)
  private FundOrganism fundOrganism;

  @ManyToOne
  @JsonIgnoreProperties(value = "funds", allowSetters = true)
  private BusinessRiskCategory businessRiskCategory;

  public Fund promoter(String promoter) {
    this.promoter = promoter;
    return this;
  }

  public Fund maturityDate(LocalDate maturityDate) {
    this.maturityDate = maturityDate;
    return this;
  }

  public Fund knownNAV(Boolean knownNAV) {
    this.knownNAV = knownNAV;
    return this;
  }

  public Fund higherLimitCommercialFundSensibility(Boolean higherLimitCommercialFundSensibility) {
    this.higherLimitCommercialFundSensibility = higherLimitCommercialFundSensibility;
    return this;
  }

  public Fund lowerLimitCommercialFundSensibility(Boolean lowerLimitCommercialFundSensibility) {
    this.lowerLimitCommercialFundSensibility = lowerLimitCommercialFundSensibility;
    return this;
  }

  public Fund investMoreThanFivePercent(Boolean investMoreThanFivePercent) {
    this.investMoreThanFivePercent = investMoreThanFivePercent;
    return this;
  }

  public Fund dayNAV(DayWeek dayNAV) {
    this.dayNAV = dayNAV;
    return this;
  }

  public Fund frequencyNAV(Periodicity frequencyNAV) {
    this.frequencyNAV = frequencyNAV;
    return this;
  }

  public Fund netAssetValueAtIssuing(BigDecimal netAssetValueAtIssuing) {
    this.netAssetValueAtIssuing = netAssetValueAtIssuing;
    return this;
  }

  public Fund rightExitAmount(BigDecimal rightExitAmount) {
    this.rightExitAmount = rightExitAmount;
    return this;
  }

  public Fund rightEnterAmount(BigDecimal rightEnterAmount) {
    this.rightEnterAmount = rightEnterAmount;
    return this;
  }

  public Fund rightEnterPercentage(BigDecimal rightEnterPercentage) {
    this.rightEnterPercentage = rightEnterPercentage;
    return this;
  }

  public Fund rightExitPercentage(BigDecimal rightExitPercentage) {
    this.rightExitPercentage = rightExitPercentage;
    return this;
  }

  public Fund fundSensibilityLowerLimit(BigDecimal fundSensibilityLowerLimit) {
    this.fundSensibilityLowerLimit = fundSensibilityLowerLimit;
    return this;
  }

  public Fund fundSensibilityHigherLimit(BigDecimal fundSensibilityHigherLimit) {
    this.fundSensibilityHigherLimit = fundSensibilityHigherLimit;
    return this;
  }

  public Fund subscriptionTerm(BigDecimal subscriptionTerm) {
    this.subscriptionTerm = subscriptionTerm;
    return this;
  }

  public Fund refundTerm(BigDecimal refundTerm) {
    this.refundTerm = refundTerm;
    return this;
  }

  public Fund settlementSubscriptionTerm(BigDecimal settlementSubscriptionTerm) {
    this.settlementSubscriptionTerm = settlementSubscriptionTerm;
    return this;
  }

  public Fund priceDivisionFactor(BigDecimal priceDivisionFactor) {
    this.priceDivisionFactor = priceDivisionFactor;
    return this;
  }

  public Fund priceNumber(BigDecimal priceNumber) {
    this.priceNumber = priceNumber;
    return this;
  }

  public Fund decimalizationUnity(BigDecimal decimalizationUnity) {
    this.decimalizationUnity = decimalizationUnity;
    return this;
  }

  public Fund maxLimitByXOA(BigDecimal maxLimitByXOA) {
    this.maxLimitByXOA = maxLimitByXOA;
    return this;
  }

  public Fund fundClassification(FundClassification fundClassification) {
    this.fundClassification = fundClassification;
    return this;
  }

  public Fund fundCategorie(FundCategory fundCategory) {
    this.fundCategory = fundCategory;
    return this;
  }

  public Fund fundOrganism(FundOrganism fundOrganism) {
    this.fundOrganism = fundOrganism;
    return this;
  }

  public Fund fundType(FundType fundType) {
    this.fundType = fundType;
    return this;
  }

  public Fund businessRiskCategory(BusinessRiskCategory businessRiskCategory) {
    this.businessRiskCategory = businessRiskCategory;
    return this;
  }
}
