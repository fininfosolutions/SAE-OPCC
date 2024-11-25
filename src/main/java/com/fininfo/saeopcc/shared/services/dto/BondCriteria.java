package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.AmortizationType;
import com.fininfo.saeopcc.shared.domains.enumeration.BondCategory;
import com.fininfo.saeopcc.shared.domains.enumeration.BondClassification;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria.MarketTypeFilter;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria.SecurityFormFilter;
import com.fininfo.saeopcc.shared.services.dto.FundCriteria.DetentionFormFilter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BondCriteria implements Serializable, Criteria {

  public static class BondClassificationFilter extends Filter<BondClassification> {

    public BondClassificationFilter() {}

    public BondClassificationFilter(BondClassificationFilter filter) {
      super(filter);
    }

    @Override
    public BondClassificationFilter copy() {
      return new BondClassificationFilter(this);
    }
  }

  public static class BondCategoryFilter extends Filter<BondCategory> {

    public BondCategoryFilter() {}

    public BondCategoryFilter(BondCategoryFilter filter) {
      super(filter);
    }

    @Override
    public BondCategoryFilter copy() {
      return new BondCategoryFilter(this);
    }
  }

  public static class AmortizationTypeFilter extends Filter<AmortizationType> {

    public AmortizationTypeFilter() {}

    public AmortizationTypeFilter(AmortizationTypeFilter filter) {
      super(filter);
    }

    @Override
    public AmortizationTypeFilter copy() {
      return new AmortizationTypeFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter isin;

  private LocalDateFilter premiumDate;

  private AmortizationTypeFilter amortizationType;
  private BondCategoryFilter bondCategory;

  private StringFilter shortCode;

  private StringFilter description;

  private BooleanFilter indexed;

  private BooleanFilter eligibilityIndicator;

  private LocalDateFilter maturityDate;

  private BooleanFilter convertibilityIndicator;

  private BooleanFilter subordinationIndicator;

  private BooleanFilter amortizationTableManagement;

  private BooleanFilter variableRate;

  private BigDecimalFilter poolFactor;

  private BigDecimalFilter riskPremium;

  private LongFilter maturityId;

  private BondClassificationFilter bondClassification;

  private StringFilter reference;
  private SecurityFormFilter securityForm;
  private DetentionFormFilter detentionForm;
  private MarketTypeFilter marketType;
  private StringFilter aditionalDescription;
  private StringFilter code;
  private BooleanFilter active;

  public BondCriteria(BondCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.indexed = other.indexed == null ? null : other.indexed.copy();
    this.eligibilityIndicator =
        other.eligibilityIndicator == null ? null : other.eligibilityIndicator.copy();
    this.maturityDate = other.maturityDate == null ? null : other.maturityDate.copy();
    this.convertibilityIndicator =
        other.convertibilityIndicator == null ? null : other.convertibilityIndicator.copy();
    this.subordinationIndicator =
        other.subordinationIndicator == null ? null : other.subordinationIndicator.copy();
    this.amortizationTableManagement =
        other.amortizationTableManagement == null ? null : other.amortizationTableManagement.copy();
    this.variableRate = other.variableRate == null ? null : other.variableRate.copy();
    this.poolFactor = other.poolFactor == null ? null : other.poolFactor.copy();
    this.riskPremium = other.riskPremium == null ? null : other.riskPremium.copy();
    this.maturityId = other.maturityId == null ? null : other.maturityId.copy();
    this.bondClassification =
        other.bondClassification == null ? null : other.bondClassification.copy();
    this.isin = other.isin == null ? null : other.isin.copy();
    this.premiumDate = other.premiumDate == null ? null : other.premiumDate.copy();
    this.amortizationType = other.amortizationType == null ? null : other.amortizationType.copy();
    this.shortCode = other.shortCode == null ? null : other.shortCode.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.bondCategory = other.bondCategory == null ? null : other.bondCategory.copy();
    this.securityForm = other.securityForm == null ? null : other.securityForm.copy();
    this.detentionForm = other.detentionForm == null ? null : other.detentionForm.copy();
    this.marketType = other.marketType == null ? null : other.marketType.copy();
    this.aditionalDescription =
        other.aditionalDescription == null ? null : other.aditionalDescription.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.active = other.active == null ? null : other.active.copy();
  }

  @Override
  public BondCriteria copy() {
    return new BondCriteria(this);
  }
}
