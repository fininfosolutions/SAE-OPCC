package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.SubManagementActivity} entity.
 * This class is used in {@link
 * com.fininfo.saeopcc.shared.controllers.SubManagementActivityResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code
 * /sub-management-activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As
 * Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we
 * need to use fix type specific filters.
 */
public class SubManagementActivityCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter description;

  private StringFilter code;

  public SubManagementActivityCriteria() {}

  public SubManagementActivityCriteria(SubManagementActivityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.code = other.code == null ? null : other.code.copy();
  }

  @Override
  public SubManagementActivityCriteria copy() {
    return new SubManagementActivityCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  public StringFilter getCode() {
    return code;
  }

  public void setCode(StringFilter code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SubManagementActivityCriteria that = (SubManagementActivityCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(description, that.description)
        && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, code);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "SubManagementActivityCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + "}";
  }
}
