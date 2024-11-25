package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.SubActivity} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.SubActivityResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /sub-activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class SubActivityCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  private StringFilter activity;

  private StringFilter valueType;

  private StringFilter type;

  public SubActivityCriteria() {}

  public SubActivityCriteria(SubActivityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.activity = other.activity == null ? null : other.activity.copy();
    this.valueType = other.valueType == null ? null : other.valueType.copy();
    this.type = other.type == null ? null : other.type.copy();
  }

  @Override
  public SubActivityCriteria copy() {
    return new SubActivityCriteria(this);
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

  public StringFilter getActivity() {
    return activity;
  }

  public void setActivity(StringFilter activity) {
    this.activity = activity;
  }

  public StringFilter getValueType() {
    return valueType;
  }

  public void setValueType(StringFilter valueType) {
    this.valueType = valueType;
  }

  public StringFilter getType() {
    return type;
  }

  public void setType(StringFilter type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SubActivityCriteria that = (SubActivityCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(code, that.code)
        && Objects.equals(description, that.description)
        && Objects.equals(activity, that.activity)
        && Objects.equals(valueType, that.valueType)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, description, activity, valueType, type);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "SubActivityCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (activity != null ? "activity=" + activity + ", " : "")
        + (valueType != null ? "valueType=" + valueType + ", " : "")
        + (type != null ? "type=" + type + ", " : "")
        + "}";
  }
}
