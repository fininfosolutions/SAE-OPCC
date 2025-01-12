package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LiberationCriteria implements Serializable, Criteria {

  public static class LiberationStatusFilter extends Filter<LiberationStatus> {
    public LiberationStatusFilter() {}

    public LiberationStatusFilter(LiberationStatusFilter filter) {
      super(filter);
    }

    @Override
    public LiberationStatusFilter copy() {
      return new LiberationStatusFilter(this);
    }
  }

  private LongFilter id;
  private BigDecimalFilter percentage;
  private StringFilter description;
  private StringFilter reference;

  private LocalDateFilter liberationDate;
  private BigDecimalFilter releasedAmount;

  private LiberationStatusFilter status;
  private BigDecimalFilter releasedQuantity;
  private BigDecimalFilter remainingQuantity;
  private BigDecimalFilter remainingAmount;

  public LiberationCriteria(LiberationCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.percentage = other.percentage == null ? null : other.percentage.copy();
    this.reference = other.reference == null ? null : other.reference.copy();

    this.description = other.description == null ? null : other.description.copy();
    this.liberationDate = other.liberationDate == null ? null : other.liberationDate.copy();
    this.releasedAmount = other.releasedAmount == null ? null : other.releasedAmount.copy();
    this.status = other.status == null ? null : other.status.copy();
    this.releasedQuantity = other.releasedQuantity == null ? null : other.releasedQuantity.copy();
    this.remainingQuantity =
        other.remainingQuantity == null ? null : other.remainingQuantity.copy();
    this.remainingAmount = other.remainingAmount == null ? null : other.remainingAmount.copy();
  }

  public LiberationCriteria(
      LongFilter id,
      StringFilter description,
      StringFilter reference,
      BigDecimalFilter percentage,
      LocalDateFilter liberationDate,
      BigDecimalFilter releasedAmount,
      BigDecimalFilter releasedQuantity,
      BigDecimalFilter remainingAmount,
      BigDecimalFilter remainingQuantity,
      LiberationStatusFilter status) {

    this.id = id;
    this.description = description;
    this.reference = reference;
    this.percentage = percentage;
    this.liberationDate = liberationDate;
    this.releasedAmount = releasedAmount;
    this.status = status;
    this.releasedQuantity = releasedQuantity;
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
