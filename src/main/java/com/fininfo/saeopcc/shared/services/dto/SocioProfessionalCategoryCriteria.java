package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.SocioProfessionalCategory}
 * entity. This class is used in {@link
 * com.fininfo.saeopcc.shared.controllers.SocioProfessionalCategoryResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /socio-professional-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used,
 * we need to use fix type specific filters.
 */
public class SocioProfessionalCategoryCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  private StringFilter shortDescription;

  private StringFilter centralBankCode;

  private LongFilter roleId;

  public SocioProfessionalCategoryCriteria() {}

  public SocioProfessionalCategoryCriteria(SocioProfessionalCategoryCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.shortDescription = other.shortDescription == null ? null : other.shortDescription.copy();
    this.centralBankCode = other.centralBankCode == null ? null : other.centralBankCode.copy();
    this.roleId = other.roleId == null ? null : other.roleId.copy();
  }

  @Override
  public SocioProfessionalCategoryCriteria copy() {
    return new SocioProfessionalCategoryCriteria(this);
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

  public StringFilter getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(StringFilter shortDescription) {
    this.shortDescription = shortDescription;
  }

  public StringFilter getCentralBankCode() {
    return centralBankCode;
  }

  public void setCentralBankCode(StringFilter centralBankCode) {
    this.centralBankCode = centralBankCode;
  }

  public LongFilter getRoleId() {
    return roleId;
  }

  public void setRoleId(LongFilter roleId) {
    this.roleId = roleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SocioProfessionalCategoryCriteria that = (SocioProfessionalCategoryCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(code, that.code)
        && Objects.equals(description, that.description)
        && Objects.equals(shortDescription, that.shortDescription)
        && Objects.equals(centralBankCode, that.centralBankCode)
        && Objects.equals(roleId, that.roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, description, shortDescription, centralBankCode, roleId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "SocioProfessionalCategoryCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (shortDescription != null ? "shortDescription=" + shortDescription + ", " : "")
        + (centralBankCode != null ? "centralBankCode=" + centralBankCode + ", " : "")
        + (roleId != null ? "roleId=" + roleId + ", " : "")
        + "}";
  }
}
