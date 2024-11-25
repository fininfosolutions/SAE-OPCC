package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Borrower} entity. This class is
 * used in {@link com.fininfo.saeopcc.shared.controllers.BorrowerResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /borrowers?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is unable
 * to properly convert the types, unless specific {@link Filter} class are used, we need to use fix
 * type specific filters.
 */
public class BorrowerCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private LongFilter securitiesAccountId;

  private LongFilter cashAccountId;

  public BorrowerCriteria() {}

  public BorrowerCriteria(BorrowerCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.securitiesAccountId =
        other.securitiesAccountId == null ? null : other.securitiesAccountId.copy();
    this.cashAccountId = other.cashAccountId == null ? null : other.cashAccountId.copy();
  }

  @Override
  public BorrowerCriteria copy() {
    return new BorrowerCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public LongFilter getSecuritiesAccountId() {
    return securitiesAccountId;
  }

  public void setSecuritiesAccountId(LongFilter securitiesAccountId) {
    this.securitiesAccountId = securitiesAccountId;
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
    final BorrowerCriteria that = (BorrowerCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(securitiesAccountId, that.securitiesAccountId)
        && Objects.equals(cashAccountId, that.cashAccountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, securitiesAccountId, cashAccountId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "BorrowerCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (securitiesAccountId != null ? "securitiesAccountId=" + securitiesAccountId + ", " : "")
        + (cashAccountId != null ? "cashAccountId=" + cashAccountId + ", " : "")
        + "}";
  }
}
