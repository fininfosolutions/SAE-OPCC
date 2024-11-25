package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.ExternalCode} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.ExternalCodeResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /external-codes?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class ExternalCodeCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  private LongFilter codificationSourceId;

  private LongFilter roleId;

  private LongFilter businessEntityId;

  private LongFilter cashAccountId;

  private LongFilter securitiesAccountId;

  private LongFilter assetId;

  public ExternalCodeCriteria() {}

  public ExternalCodeCriteria(ExternalCodeCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.codificationSourceId =
        other.codificationSourceId == null ? null : other.codificationSourceId.copy();
    this.roleId = other.roleId == null ? null : other.roleId.copy();
    this.businessEntityId = other.businessEntityId == null ? null : other.businessEntityId.copy();
    this.cashAccountId = other.cashAccountId == null ? null : other.cashAccountId.copy();
    this.securitiesAccountId =
        other.securitiesAccountId == null ? null : other.securitiesAccountId.copy();
    this.assetId = other.assetId == null ? null : other.assetId.copy();
  }

  @Override
  public ExternalCodeCriteria copy() {
    return new ExternalCodeCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getCode() {
    return code;
  }

  public void setCode(StringFilter code) {
    this.code = code;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  public LongFilter getCodificationSourceId() {
    return codificationSourceId;
  }

  public void setCodificationSourceId(LongFilter codificationSourceId) {
    this.codificationSourceId = codificationSourceId;
  }

  public LongFilter getRoleId() {
    return roleId;
  }

  public void setRoleId(LongFilter roleId) {
    this.roleId = roleId;
  }

  public LongFilter getBusinessEntityId() {
    return businessEntityId;
  }

  public void setBusinessEntityId(LongFilter businessEntityId) {
    this.businessEntityId = businessEntityId;
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

  public LongFilter getAssetId() {
    return assetId;
  }

  public void setAssetId(LongFilter assetId) {
    this.assetId = assetId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ExternalCodeCriteria that = (ExternalCodeCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(code, that.code)
        && Objects.equals(description, that.description)
        && Objects.equals(codificationSourceId, that.codificationSourceId)
        && Objects.equals(roleId, that.roleId)
        && Objects.equals(businessEntityId, that.businessEntityId)
        && Objects.equals(cashAccountId, that.cashAccountId)
        && Objects.equals(securitiesAccountId, that.securitiesAccountId)
        && Objects.equals(assetId, that.assetId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        code,
        description,
        codificationSourceId,
        roleId,
        businessEntityId,
        cashAccountId,
        securitiesAccountId,
        assetId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ExternalCodeCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (codificationSourceId != null
            ? "codificationSourceId=" + codificationSourceId + ", "
            : "")
        + (roleId != null ? "roleId=" + roleId + ", " : "")
        + (businessEntityId != null ? "businessEntityId=" + businessEntityId + ", " : "")
        + (cashAccountId != null ? "cashAccountId=" + cashAccountId + ", " : "")
        + (securitiesAccountId != null ? "securitiesAccountId=" + securitiesAccountId + ", " : "")
        + (assetId != null ? "assetId=" + assetId + ", " : "")
        + "}";
  }
}
