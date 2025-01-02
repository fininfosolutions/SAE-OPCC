package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallEventCriteria implements Serializable, Criteria {

  public static class EventStatusFilter extends Filter<EventStatus> {
    public EventStatusFilter() {}

    public EventStatusFilter(EventStatusFilter filter) {
      super(filter);
    }

    @Override
    public EventStatusFilter copy() {
      return new EventStatusFilter(this);
    }
  }

  private LongFilter id;
  private StringFilter description;
  private LocalDateFilter closingDate;
  private LocalDateFilter appealDate;
  private BigDecimalFilter globalSousInitialAmount;
  private BigDecimalFilter globalSousInitialQuantity;
  private BigDecimalFilter globalSousAmount;
  private BigDecimalFilter globalSousQuantity;
  private BigDecimalFilter globalAppealAmount;
  private BigDecimalFilter globalAppealQuantity;
  private BigDecimalFilter percentage;
  private EventStatusFilter eventStatus;
  private BigDecimalFilter globalUnfundedAmount;
  private BigDecimalFilter globalUnfundedQuantity;
  private StringFilter dinvestmentPeriod;
  private StringFilter investmentPeriod;

  private StringFilter issueDescription;
  private LocalDateFilter issueClosingDate;
  private LocalDateFilter issueOpeningDate;
  private BigDecimalFilter issueQuantity;
  private BigDecimalFilter issueAmount;
  private BigDecimalFilter issuePrice;

  public CallEventCriteria(CallEventCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.issueDescription = other.issueDescription == null ? null : other.issueDescription.copy();
    this.issueClosingDate = other.issueClosingDate == null ? null : other.issueClosingDate.copy();
    this.issueOpeningDate = other.issueOpeningDate == null ? null : other.issueOpeningDate.copy();
    this.issueQuantity = other.issueQuantity == null ? null : other.issueQuantity.copy();
    this.issueAmount = other.issueAmount == null ? null : other.issueAmount.copy();
    this.issuePrice = other.issuePrice == null ? null : other.issuePrice.copy();

    this.description = other.description == null ? null : other.description.copy();
    this.closingDate = other.closingDate == null ? null : other.closingDate.copy();
    this.appealDate = other.appealDate == null ? null : other.appealDate.copy();
    this.globalSousInitialAmount =
        other.globalSousInitialAmount == null ? null : other.globalSousInitialAmount.copy();
    this.globalSousInitialQuantity =
        other.globalSousInitialQuantity == null ? null : other.globalSousInitialQuantity.copy();
    this.globalSousAmount = other.globalSousAmount == null ? null : other.globalSousAmount.copy();
    this.globalSousQuantity =
        other.globalSousQuantity == null ? null : other.globalSousQuantity.copy();
    this.globalAppealAmount =
        other.globalAppealAmount == null ? null : other.globalAppealAmount.copy();
    this.globalAppealQuantity =
        other.globalAppealQuantity == null ? null : other.globalAppealQuantity.copy();
    this.percentage = other.percentage == null ? null : other.percentage.copy();
    this.eventStatus = other.eventStatus == null ? null : other.eventStatus.copy();
    this.globalUnfundedAmount =
        other.globalUnfundedAmount == null ? null : other.globalUnfundedAmount.copy();
    this.globalUnfundedQuantity =
        other.globalUnfundedQuantity == null ? null : other.globalUnfundedQuantity.copy();
    this.dinvestmentPeriod =
        other.dinvestmentPeriod == null ? null : other.dinvestmentPeriod.copy();
    this.investmentPeriod = other.investmentPeriod == null ? null : other.investmentPeriod.copy();
  }

  public CallEventCriteria(
      LongFilter id,
      StringFilter description,
      LocalDateFilter closingDate,
      LocalDateFilter appealDate,
      BigDecimalFilter globalSousInitialAmount,
      BigDecimalFilter globalSousInitialQuantity,
      BigDecimalFilter globalSousAmount,
      BigDecimalFilter globalSousQuantity,
      BigDecimalFilter globalAppealAmount,
      BigDecimalFilter globalAppealQuantity,
      BigDecimalFilter percentage,
      EventStatusFilter eventStatus,
      BigDecimalFilter globalUnfundedAmount,
      BigDecimalFilter globalUnfundedQuantity,
      StringFilter dinvestmentPeriod,
      StringFilter investmentPeriod,
      StringFilter issueDescription,
      LocalDateFilter issueClosingDate,
      LocalDateFilter issueOpeningDate,
      BigDecimalFilter issueQuantity,
      BigDecimalFilter issueAmount,
      BigDecimalFilter issuePrice) {

    this.id = id;
    this.description = description;
    this.closingDate = closingDate;
    this.appealDate = appealDate;
    this.globalSousInitialAmount = globalSousInitialAmount;
    this.globalSousInitialQuantity = globalSousInitialQuantity;
    this.globalSousAmount = globalSousAmount;
    this.globalSousQuantity = globalSousQuantity;
    this.globalAppealAmount = globalAppealAmount;
    this.globalAppealQuantity = globalAppealQuantity;
    this.percentage = percentage;
    this.eventStatus = eventStatus;
    this.globalUnfundedAmount = globalUnfundedAmount;
    this.globalUnfundedQuantity = globalUnfundedQuantity;
    this.dinvestmentPeriod = dinvestmentPeriod;
    this.investmentPeriod = investmentPeriod;
    this.issueDescription = issueDescription;
    this.issueClosingDate = issueClosingDate;
    this.issueOpeningDate = issueOpeningDate;
    this.issueQuantity = issueQuantity;
    this.issueAmount = issueAmount;
    this.issuePrice = issuePrice;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public Criteria copy() {
    throw new UnsupportedOperationException("Unimplemented method 'copy'");
  }
}
