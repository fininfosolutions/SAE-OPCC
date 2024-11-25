package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
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
public class NostroAccountCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter accountId;

  private StringFilter accShortName;

  private StringFilter accLongName;

  public NostroAccountCriteria() {}

  public NostroAccountCriteria(NostroAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.accountId = other.accountId == null ? null : other.accountId.copy();
    this.accShortName = other.accShortName == null ? null : other.accShortName.copy();
    this.accLongName = other.accLongName == null ? null : other.accLongName.copy();
  }

  @Override
  public NostroAccountCriteria copy() {
    return new NostroAccountCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getAccountId() {
    return accountId;
  }

  public void setAccountId(StringFilter accountId) {
    this.accountId = accountId;
  }

  public StringFilter getAccShortName() {
    return accShortName;
  }

  public void setAccShortName(StringFilter accShortName) {
    this.accShortName = accShortName;
  }

  public StringFilter getAccLongName() {
    return accLongName;
  }

  public void setAccLongName(StringFilter accLongName) {
    this.accLongName = accLongName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final NostroAccountCriteria that = (NostroAccountCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(accountId, that.accountId)
        && Objects.equals(accShortName, that.accShortName)
        && Objects.equals(accLongName, that.accLongName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountId, accShortName, accLongName);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "NostroAccountCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (accountId != null ? "accountId=" + accountId + ", " : "")
        + (accShortName != null ? "accShortName=" + accShortName + ", " : "")
        + (accLongName != null ? "accLongName=" + accLongName + ", " : "")
        + "}";
  }
}
