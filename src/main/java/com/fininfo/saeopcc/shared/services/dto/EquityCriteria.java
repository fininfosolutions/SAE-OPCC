package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.EquityClassification;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria.MarketTypeFilter;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria.SecurityFormFilter;
import com.fininfo.saeopcc.shared.services.dto.FundCriteria.DetentionFormFilter;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Equity} entity. This class is
 * used in {@link com.fininfo.saeopcc.shared.controllers.EquityResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code /equities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used,
 * we need to use fix type specific filters.
 */
@Getter
@Setter
@NoArgsConstructor
public class EquityCriteria implements Serializable, Criteria {

  /** Class for filtering EquityClassification */
  public static class EquityClassificationFilter extends Filter<EquityClassification> {

    public EquityClassificationFilter() {}

    public EquityClassificationFilter(EquityClassificationFilter filter) {
      super(filter);
    }

    @Override
    public EquityClassificationFilter copy() {
      return new EquityClassificationFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter isin;
  private StringFilter code;

  private StringFilter shortCode;
  private BooleanFilter active;
  private BooleanFilter socialPart;

  private StringFilter description;

  private StringFilter reference;
  private SecurityFormFilter securityForm;
  private DetentionFormFilter detentionForm;
  private StringFilter aditionalDescription;
  private LocalDateFilter premiumDate;
  private MarketTypeFilter marketType;

  public EquityCriteria(
      LongFilter id,
      StringFilter isin,
      StringFilter code,
      StringFilter shortCode,
      BooleanFilter active,
      BooleanFilter socialPart,
      StringFilter description,
      StringFilter reference,
      SecurityFormFilter securityForm,
      DetentionFormFilter detentionForm,
      StringFilter aditionalDescription,
      LocalDateFilter premiumDate,
      MarketTypeFilter marketType) {
    this.id = id;
    this.isin = isin;
    this.code = code;
    this.shortCode = shortCode;
    this.active = active;
    this.socialPart = socialPart;
    this.description = description;
    this.reference = reference;
    this.securityForm = securityForm;
    this.detentionForm = detentionForm;
    this.aditionalDescription = aditionalDescription;
    this.premiumDate = premiumDate;
    this.marketType = marketType;
  }

  public EquityCriteria(EquityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.isin = other.isin == null ? null : other.isin.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.shortCode = other.shortCode == null ? null : other.shortCode.copy();
    this.active = other.active == null ? null : other.active.copy();
    this.socialPart = other.socialPart == null ? null : other.socialPart.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.securityForm = other.securityForm == null ? null : other.securityForm.copy();
    this.detentionForm = other.detentionForm == null ? null : other.detentionForm.copy();
    this.aditionalDescription =
        other.aditionalDescription == null ? null : other.aditionalDescription.copy();
    this.premiumDate = other.premiumDate == null ? null : other.premiumDate.copy();
    this.marketType = other.marketType == null ? null : other.marketType.copy();
  }

  @Override
  public EquityCriteria copy() {
    return new EquityCriteria(this);
  }
}
