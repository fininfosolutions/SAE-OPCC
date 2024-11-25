package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Languages} entity. This class is
 * used in {@link com.fininfo.saeopcc.shared.controllers.LanguagesResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code /languages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used,
 * we need to use fix type specific filters.
 */
public class LanguagesCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  private IntegerFilter position;

  private LongFilter clientId;

  public LanguagesCriteria() {}

  public LanguagesCriteria(LanguagesCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.position = other.position == null ? null : other.position.copy();
    this.clientId = other.clientId == null ? null : other.clientId.copy();
  }

  @Override
  public LanguagesCriteria copy() {
    return new LanguagesCriteria(this);
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

  public IntegerFilter getPosition() {
    return position;
  }

  public void setPosition(IntegerFilter position) {
    this.position = position;
  }

  public LongFilter getClientId() {
    return clientId;
  }

  public void setClientId(LongFilter clientId) {
    this.clientId = clientId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final LanguagesCriteria that = (LanguagesCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(code, that.code)
        && Objects.equals(description, that.description)
        && Objects.equals(position, that.position)
        && Objects.equals(clientId, that.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, description, position, clientId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "LanguagesCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (position != null ? "position=" + position + ", " : "")
        + (clientId != null ? "clientId=" + clientId + ", " : "")
        + "}";
  }
}
