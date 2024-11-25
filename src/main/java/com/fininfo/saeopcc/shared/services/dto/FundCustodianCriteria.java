package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.FundCustodian} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.FundCustodianResource} to receive all
 * the possible filtering options from the Http GET request parameters. For example the following
 * could be a valid request: {@code
 * /fund-custodians?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class FundCustodianCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private LongFilter cashAccountId;

  public FundCustodianCriteria() {}

  public FundCustodianCriteria(FundCustodianCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.cashAccountId = other.cashAccountId == null ? null : other.cashAccountId.copy();
  }

  @Override
  public FundCustodianCriteria copy() {
    return new FundCustodianCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public LongFilter getCashAccountId() {
    return cashAccountId;
  }

  public void setCashAccountId(LongFilter cashAccountId) {
    this.cashAccountId = cashAccountId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final FundCustodianCriteria that = (FundCustodianCriteria) o;
    return Objects.equals(id, that.id) && Objects.equals(cashAccountId, that.cashAccountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cashAccountId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FundCustodianCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (cashAccountId != null ? "cashAccountId=" + cashAccountId + ", " : "")
        + "}";
  }
}
