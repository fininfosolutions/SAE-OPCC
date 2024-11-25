package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.CodificationSource} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.CodificationSourceResource} to
 * receive all the possible filtering options from the Http GET request parameters. For example the
 * following could be a valid request: {@code
 * /codification-sources?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring
 * is unable to properly convert the types, unless specific {@link Filter} class are used, we need
 * to use fix type specific filters.
 */
public class CodificationSourceCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  public CodificationSourceCriteria() {}

  public CodificationSourceCriteria(CodificationSourceCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
  }

  @Override
  public CodificationSourceCriteria copy() {
    return new CodificationSourceCriteria(this);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CodificationSourceCriteria that = (CodificationSourceCriteria) o;
    return Objects.equals(id, that.id) && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CodificationSourceCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (code != null ? "code=" + code + ", " : "")
        + "}";
  }
}
