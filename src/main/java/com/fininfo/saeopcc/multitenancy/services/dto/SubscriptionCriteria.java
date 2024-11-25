package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriptionCriteria implements Serializable, Criteria {

  public static class TransactionTypeFilter extends Filter<TransactionType> {

    public TransactionTypeFilter() {}

    public TransactionTypeFilter(TransactionTypeFilter filter) {
      super(filter);
    }

    @Override
    public TransactionTypeFilter copy() {
      return new TransactionTypeFilter(this);
    }
  }

  public static class SubscriptionStatusFilter extends Filter<SubscriptionStatus> {

    public SubscriptionStatusFilter() {}

    public SubscriptionStatusFilter(SubscriptionStatusFilter filter) {
      super(filter);
    }

    @Override
    public Filter<SubscriptionStatus> copy() {
      return new SubscriptionStatusFilter(this);
    }
  }

  private LongFilter id;

  private BigDecimalFilter amount;

  private BigDecimalFilter quantity;

  private StringFilter reference;

  private StringFilter assetIsin;

  private StringFilter shareholderReference;

  private StringFilter custodianReference;

  public SubscriptionCriteria(SubscriptionCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.reference = other.reference == null ? null : other.reference.copy();

    this.quantity = other.quantity == null ? null : other.quantity.copy();

    this.amount = other.amount == null ? null : other.amount.copy();

    this.assetIsin = other.assetIsin == null ? null : other.assetIsin.copy();

    this.custodianReference =
        other.custodianReference == null ? null : other.custodianReference.copy();

    this.shareholderReference =
        other.shareholderReference == null ? null : other.shareholderReference.copy();
  }

  public SubscriptionCriteria(
      LongFilter id,
      BigDecimalFilter amount,
      StringFilter assetIsin,
      StringFilter custodianReference,
      StringFilter shareholderReference) {
    this.id = id;

    this.assetIsin = assetIsin;

    this.amount = amount;

    this.custodianReference = custodianReference;

    this.shareholderReference = shareholderReference;
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
