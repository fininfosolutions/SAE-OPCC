package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Centraliser} entity. This class
 * is used in {@link com.fininfo.saeopcc.shared.controllers.CentraliserResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could
 * be a valid request: {@code
 * /centralisers?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class CentraliserCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  public CentraliserCriteria() {}

  public CentraliserCriteria(CentraliserCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
  }

  @Override
  public CentraliserCriteria copy() {
    return new CentraliserCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CentraliserCriteria that = (CentraliserCriteria) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CentraliserCriteria{" + (id != null ? "id=" + id + ", " : "") + "}";
  }
}
