package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.ClientCategory} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.ClientCategoryResource} to receive
 * all the possible filtering options from the Http GET request parameters. For example the
 * following could be a valid request: {@code
 * /client-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class ClientCategoryCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter type;

  private StringFilter description;

  private StringFilter nature;

  public ClientCategoryCriteria() {}

  public ClientCategoryCriteria(ClientCategoryCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.type = other.type == null ? null : other.type.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.nature = other.nature == null ? null : other.nature.copy();
  }

  @Override
  public ClientCategoryCriteria copy() {
    return new ClientCategoryCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getType() {
    return type;
  }

  public void setType(StringFilter type) {
    this.type = type;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  public StringFilter getNature() {
    return nature;
  }

  public void setNature(StringFilter nature) {
    this.nature = nature;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ClientCategoryCriteria that = (ClientCategoryCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(type, that.type)
        && Objects.equals(description, that.description)
        && Objects.equals(nature, that.nature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, description, nature);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ClientCategoryCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (type != null ? "type=" + type + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (nature != null ? "nature=" + nature + ", " : "")
        + "}";
  }
}
