package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.CashAccount} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.CashAccountResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /cash-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class CashAccountCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter name;

  private StringFilter number;

  private LongFilter clientId;

  private LongFilter borrowerId;

  private LongFilter lenderId;

  private LongFilter proxyId;

  private LongFilter correspondentId;

  private LongFilter fundCustodianId;

  private LongFilter accountsTypeId;

  private LongFilter linkedCashAccountId;

  public CashAccountCriteria() {}

  public CashAccountCriteria(CashAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.name = other.name == null ? null : other.name.copy();
    this.number = other.number == null ? null : other.number.copy();
    this.clientId = other.clientId == null ? null : other.clientId.copy();
    this.borrowerId = other.borrowerId == null ? null : other.borrowerId.copy();
    this.lenderId = other.lenderId == null ? null : other.lenderId.copy();
    this.proxyId = other.proxyId == null ? null : other.proxyId.copy();
    this.correspondentId = other.correspondentId == null ? null : other.correspondentId.copy();
    this.fundCustodianId = other.fundCustodianId == null ? null : other.fundCustodianId.copy();
    this.accountsTypeId = other.accountsTypeId == null ? null : other.accountsTypeId.copy();
    this.linkedCashAccountId =
        other.linkedCashAccountId == null ? null : other.linkedCashAccountId.copy();
  }

  @Override
  public CashAccountCriteria copy() {
    return new CashAccountCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getName() {
    return name;
  }

  public void setName(StringFilter name) {
    this.name = name;
  }

  public StringFilter getNumber() {
    return number;
  }

  public void setNumber(StringFilter number) {
    this.number = number;
  }

  public LongFilter getClientId() {
    return clientId;
  }

  public void setClientId(LongFilter clientId) {
    this.clientId = clientId;
  }

  public LongFilter getBorrowerId() {
    return borrowerId;
  }

  public void setBorrowerId(LongFilter borrowerId) {
    this.borrowerId = borrowerId;
  }

  public LongFilter getLenderId() {
    return lenderId;
  }

  public void setLenderId(LongFilter lenderId) {
    this.lenderId = lenderId;
  }

  public LongFilter getProxyId() {
    return proxyId;
  }

  public void setProxyId(LongFilter proxyId) {
    this.proxyId = proxyId;
  }

  public LongFilter getCorrespondentId() {
    return correspondentId;
  }

  public void setCorrespondentId(LongFilter correspondentId) {
    this.correspondentId = correspondentId;
  }

  public LongFilter getFundCustodianId() {
    return fundCustodianId;
  }

  public void setFundCustodianId(LongFilter fundCustodianId) {
    this.fundCustodianId = fundCustodianId;
  }

  public LongFilter getAccountsTypeId() {
    return accountsTypeId;
  }

  public void setAccountsTypeId(LongFilter accountsTypeId) {
    this.accountsTypeId = accountsTypeId;
  }

  public LongFilter getLinkedCashAccountId() {
    return linkedCashAccountId;
  }

  public void setLinkedCashAccountId(LongFilter linkedCashAccountId) {
    this.linkedCashAccountId = linkedCashAccountId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CashAccountCriteria that = (CashAccountCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(number, that.number)
        && Objects.equals(clientId, that.clientId)
        && Objects.equals(borrowerId, that.borrowerId)
        && Objects.equals(lenderId, that.lenderId)
        && Objects.equals(proxyId, that.proxyId)
        && Objects.equals(correspondentId, that.correspondentId)
        && Objects.equals(fundCustodianId, that.fundCustodianId)
        && Objects.equals(accountsTypeId, that.accountsTypeId)
        && Objects.equals(linkedCashAccountId, that.linkedCashAccountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        name,
        number,
        clientId,
        borrowerId,
        lenderId,
        proxyId,
        correspondentId,
        fundCustodianId,
        accountsTypeId,
        linkedCashAccountId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CashAccountCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (name != null ? "name=" + name + ", " : "")
        + (number != null ? "number=" + number + ", " : "")
        + (clientId != null ? "clientId=" + clientId + ", " : "")
        + (borrowerId != null ? "borrowerId=" + borrowerId + ", " : "")
        + (lenderId != null ? "lenderId=" + lenderId + ", " : "")
        + (proxyId != null ? "proxyId=" + proxyId + ", " : "")
        + (correspondentId != null ? "correspondentId=" + correspondentId + ", " : "")
        + (fundCustodianId != null ? "fundCustodianId=" + fundCustodianId + ", " : "")
        + (accountsTypeId != null ? "accountsTypeId=" + accountsTypeId + ", " : "")
        + (linkedCashAccountId != null ? "linkedCashAccountId=" + linkedCashAccountId + ", " : "")
        + "}";
  }
}
