package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueAccountCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;
  private LongFilter id;
  private BigDecimalFilter issueaccountNumber;
  private StringFilter description;
  private StringFilter valueDescription;
  private LocalDateFilter openingaccountDate;
  private BigDecimalFilter securitiesquantity;
  private BooleanFilter actif;
  private StringFilter reference;
  private LongFilter assetId;

  public IssueAccountCriteria() {}

  public IssueAccountCriteria(IssueAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.issueaccountNumber =
        other.issueaccountNumber == null ? null : other.issueaccountNumber.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.valueDescription = other.valueDescription == null ? null : other.valueDescription.copy();
    this.openingaccountDate =
        other.openingaccountDate == null ? null : other.openingaccountDate.copy();
    this.securitiesquantity =
        other.securitiesquantity == null ? null : other.securitiesquantity.copy();
    this.actif = other.actif == null ? null : other.actif.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.assetId = other.assetId == null ? null : other.assetId.copy();
  }

  @Override
  public IssueAccountCriteria copy() {
    return new IssueAccountCriteria(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final IssueAccountCriteria that = (IssueAccountCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(issueaccountNumber, that.issueaccountNumber)
        && Objects.equals(description, that.description)
        && Objects.equals(valueDescription, that.valueDescription)
        && Objects.equals(openingaccountDate, that.openingaccountDate)
        && Objects.equals(securitiesquantity, that.securitiesquantity)
        && Objects.equals(actif, that.actif)
        && Objects.equals(reference, that.reference)
        && Objects.equals(assetId, that.assetId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        issueaccountNumber,
        description,
        valueDescription,
        openingaccountDate,
        securitiesquantity,
        actif,
        reference,
        assetId);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
