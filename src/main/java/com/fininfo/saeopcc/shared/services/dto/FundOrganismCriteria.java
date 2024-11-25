package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.FundOrganism} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.FundOrganismResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /fund-organisms?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class FundOrganismCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter identifier;

  private StringFilter description;

  private StringFilter longName;

  private LongFilter fundCustodianId;

  private LongFilter fundManagerId;

  public FundOrganismCriteria() {}

  public FundOrganismCriteria(FundOrganismCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.identifier = other.identifier == null ? null : other.identifier.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.longName = other.longName == null ? null : other.longName.copy();
    this.fundCustodianId = other.fundCustodianId == null ? null : other.fundCustodianId.copy();
    this.fundManagerId = other.fundManagerId == null ? null : other.fundManagerId.copy();
  }

  @Override
  public FundOrganismCriteria copy() {
    return new FundOrganismCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getIdentifier() {
    return identifier;
  }

  public void setIdentifier(StringFilter identifier) {
    this.identifier = identifier;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  public StringFilter getLongName() {
    return longName;
  }

  public void setLongName(StringFilter longName) {
    this.longName = longName;
  }

  public LongFilter getFundCustodianId() {
    return fundCustodianId;
  }

  public void setFundCustodianId(LongFilter fundCustodianId) {
    this.fundCustodianId = fundCustodianId;
  }

  public LongFilter getFundManagerId() {
    return fundManagerId;
  }

  public void setFundManagerId(LongFilter fundManagerId) {
    this.fundManagerId = fundManagerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final FundOrganismCriteria that = (FundOrganismCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(identifier, that.identifier)
        && Objects.equals(description, that.description)
        && Objects.equals(longName, that.longName)
        && Objects.equals(fundCustodianId, that.fundCustodianId)
        && Objects.equals(fundManagerId, that.fundManagerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identifier, description, longName, fundCustodianId, fundManagerId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FundOrganismCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (identifier != null ? "identifier=" + identifier + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (longName != null ? "longName=" + longName + ", " : "")
        + (fundCustodianId != null ? "fundCustodianId=" + fundCustodianId + ", " : "")
        + (fundManagerId != null ? "fundManagerId=" + fundManagerId + ", " : "")
        + "}";
  }
}
