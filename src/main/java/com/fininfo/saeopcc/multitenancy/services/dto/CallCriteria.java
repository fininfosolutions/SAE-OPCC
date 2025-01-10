package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventCriteria.EventStatusFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallCriteria implements Serializable, Criteria {

  public static class CallStatusFilter extends Filter<CallStatus> {
    public CallStatusFilter() {}

    public CallStatusFilter(CallStatusFilter filter) {
      super(filter);
    }

    @Override
    public CallStatusFilter copy() {
      return new CallStatusFilter(this);
    }
  }

  private LongFilter id;
  private BigDecimalFilter percentage;
  private StringFilter description;
  private StringFilter reference;

  private LocalDateFilter callDate;
  private BigDecimalFilter calledAmount;

  private EventStatusFilter status;
  private BigDecimalFilter calledQuantity;
  private BigDecimalFilter remainingQuantity;
  private BigDecimalFilter remainingAmount;

  public CallCriteria(CallCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.percentage = other.percentage == null ? null : other.percentage.copy();
    this.reference = other.reference == null ? null : other.reference.copy();

    this.description = other.description == null ? null : other.description.copy();
    this.callDate = other.callDate == null ? null : other.callDate.copy();
    this.calledAmount = other.calledAmount == null ? null : other.calledAmount.copy();
    this.status = other.status == null ? null : other.status.copy();
    this.calledQuantity = other.calledQuantity == null ? null : other.calledQuantity.copy();
    this.remainingQuantity =
        other.remainingQuantity == null ? null : other.remainingQuantity.copy();
    this.remainingAmount = other.remainingAmount == null ? null : other.remainingAmount.copy();
  }

  public CallCriteria(
      LongFilter id,
      StringFilter description,
      StringFilter reference,
      BigDecimalFilter percentage,
      LocalDateFilter callDate,
      BigDecimalFilter calledAmount,
      BigDecimalFilter calledQuantity,
      BigDecimalFilter remainingAmount,
      BigDecimalFilter remainingQuantity,
      EventStatusFilter status) {

    this.id = id;
    this.description = description;
    this.reference = reference;
    this.percentage = percentage;
    this.callDate = callDate;
    this.calledAmount = calledAmount;
    this.status = status;
    this.calledQuantity = calledQuantity;
    this.remainingQuantity = remainingQuantity;
    this.remainingAmount = remainingAmount;
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
