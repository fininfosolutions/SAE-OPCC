package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.OperationType;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.LinkedCashAccount} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.LinkedCashAccountResource} to receive
 * all the possible filtering options from the Http GET request parameters. For example the
 * following could be a valid request: {@code
 * /linked-cash-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring
 * is unable to properly convert the types, unless specific {@link Filter} class are used, we need
 * to use fix type specific filters.
 */
public class LinkedCashAccountCriteria implements Serializable, Criteria {
  /** Class for filtering OperationType */
  public static class OperationTypeFilter extends Filter<OperationType> {

    public OperationTypeFilter() {}

    public OperationTypeFilter(OperationTypeFilter filter) {
      super(filter);
    }

    @Override
    public OperationTypeFilter copy() {
      return new OperationTypeFilter(this);
    }
  }

  /** Class for filtering TransactionType */
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

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private OperationTypeFilter operationType;

  private TransactionTypeFilter transactionType;

  private LongFilter cashAccountId;

  private LongFilter securitiesAccountId;

  private StringFilter securitiesAccountkeyIdentifier;

  public LinkedCashAccountCriteria() {}

  public LinkedCashAccountCriteria(LinkedCashAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.operationType = other.operationType == null ? null : other.operationType.copy();
    this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
    this.cashAccountId = other.cashAccountId == null ? null : other.cashAccountId.copy();
    this.securitiesAccountId =
        other.securitiesAccountId == null ? null : other.securitiesAccountId.copy();
    this.securitiesAccountkeyIdentifier =
        other.securitiesAccountkeyIdentifier == null
            ? null
            : other.securitiesAccountkeyIdentifier.copy();
  }

  @Override
  public LinkedCashAccountCriteria copy() {
    return new LinkedCashAccountCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public OperationTypeFilter getOperationType() {
    return operationType;
  }

  public void setOperationType(OperationTypeFilter operationType) {
    this.operationType = operationType;
  }

  public TransactionTypeFilter getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionTypeFilter transactionType) {
    this.transactionType = transactionType;
  }

  public LongFilter getCashAccountId() {
    return cashAccountId;
  }

  public void setCashAccountId(LongFilter cashAccountId) {
    this.cashAccountId = cashAccountId;
  }

  public LongFilter getSecuritiesAccountId() {
    return securitiesAccountId;
  }

  public void setSecuritiesAccountId(LongFilter securitiesAccountId) {
    this.securitiesAccountId = securitiesAccountId;
  }

  public StringFilter getSecuritiesAccountkeyIdentifier() {
    return securitiesAccountkeyIdentifier;
  }

  public void setSecuritiesAccountkeyIdentifier(StringFilter securitiesAccountkeyIdentifier) {
    this.securitiesAccountkeyIdentifier = securitiesAccountkeyIdentifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final LinkedCashAccountCriteria that = (LinkedCashAccountCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(operationType, that.operationType)
        && Objects.equals(transactionType, that.transactionType)
        && Objects.equals(cashAccountId, that.cashAccountId)
        && Objects.equals(securitiesAccountId, that.securitiesAccountId)
        && Objects.equals(securitiesAccountkeyIdentifier, that.securitiesAccountkeyIdentifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        operationType,
        transactionType,
        cashAccountId,
        securitiesAccountId,
        securitiesAccountkeyIdentifier);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "LinkedCashAccountCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (operationType != null ? "operationType=" + operationType + ", " : "")
        + (transactionType != null ? "transactionType=" + transactionType + ", " : "")
        + (cashAccountId != null ? "cashAccountId=" + cashAccountId + ", " : "")
        + (securitiesAccountId != null ? "securitiesAccountId=" + securitiesAccountId + ", " : "")
        + "}";
  }
}
