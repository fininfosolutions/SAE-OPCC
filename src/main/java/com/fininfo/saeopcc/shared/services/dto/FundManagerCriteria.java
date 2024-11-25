package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.FundManager} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.FundManagerResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /fund-managers?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class FundManagerCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private LongFilter managedFundsId;

  public FundManagerCriteria() {}

  public FundManagerCriteria(FundManagerCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.managedFundsId = other.managedFundsId == null ? null : other.managedFundsId.copy();
  }

  @Override
  public FundManagerCriteria copy() {
    return new FundManagerCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public LongFilter getManagedFundsId() {
    return managedFundsId;
  }

  public void setManagedFundsId(LongFilter managedFundsId) {
    this.managedFundsId = managedFundsId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final FundManagerCriteria that = (FundManagerCriteria) o;
    return Objects.equals(id, that.id) && Objects.equals(managedFundsId, that.managedFundsId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, managedFundsId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FundManagerCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (managedFundsId != null ? "managedFundsId=" + managedFundsId + ", " : "")
        + "}";
  }
}
