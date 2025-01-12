package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GlobalLiberationCriteria implements Serializable, Criteria {

  public static class GlobalLiberationStatusFilter extends Filter<EventStatus> {
    public GlobalLiberationStatusFilter() {}

    public GlobalLiberationStatusFilter(GlobalLiberationStatusFilter filter) {
      super(filter);
    }

    @Override
    public GlobalLiberationStatusFilter copy() {
      return new GlobalLiberationStatusFilter(this);
    }
  }

  private LongFilter id;
  private BigDecimalFilter percentage;
  private StringFilter description;
  private StringFilter reference;

  private BigDecimalFilter calledQuantity;
  private BigDecimalFilter remainingQuantity;
  private BigDecimalFilter remainingAmount;

  public GlobalLiberationCriteria(GlobalLiberationCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.percentage = other.percentage == null ? null : other.percentage.copy();
    this.reference = other.reference == null ? null : other.reference.copy();

    this.description = other.description == null ? null : other.description.copy();
    this.calledQuantity = other.calledQuantity == null ? null : other.calledQuantity.copy();
    this.remainingQuantity =
        other.remainingQuantity == null ? null : other.remainingQuantity.copy();
    this.remainingAmount = other.remainingAmount == null ? null : other.remainingAmount.copy();
  }

  public GlobalLiberationCriteria(
      LongFilter id,
      StringFilter description,
      StringFilter reference,
      BigDecimalFilter percentage) {

    this.id = id;
    this.description = description;
    this.reference = reference;
    this.percentage = percentage;
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
