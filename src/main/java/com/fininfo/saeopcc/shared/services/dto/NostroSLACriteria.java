package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.OperationType;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.NostroAccount} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.NostroAccountResource} to receive all
 * the possible filtering options from the Http GET request parameters. For example the following
 * could be a valid request: {@code
 * /nostro-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class NostroSLACriteria implements Serializable, Criteria {

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

  public NostroSLACriteria() {}

  public NostroSLACriteria(NostroSLACriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.operationType = other.operationType == null ? null : other.operationType.copy();
    this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
  }

  @Override
  public NostroSLACriteria copy() {
    return new NostroSLACriteria(this);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final NostroSLACriteria that = (NostroSLACriteria) o;
    return Objects.equals(id, that.id)
        // && Objects.equals(id, that.id)
        && Objects.equals(operationType, that.operationType)
        && Objects.equals(transactionType, that.transactionType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, operationType, transactionType);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "NostroAccountCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (operationType != null ? "operationType=" + operationType + ", " : "")
        + (transactionType != null ? "transactionType=" + transactionType + ", " : "")
        + "}";
  }
}
