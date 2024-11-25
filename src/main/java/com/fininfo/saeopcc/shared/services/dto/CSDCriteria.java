package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.CSD} entity. This class is used
 * in {@link com.fininfo.saeopcc.shared.controllers.CSDResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code /csds?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As
 * Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we
 * need to use fix type specific filters.
 */
public class CSDCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private BooleanFilter local;

  public CSDCriteria() {}

  public CSDCriteria(CSDCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.local = other.local == null ? null : other.local.copy();
  }

  @Override
  public CSDCriteria copy() {
    return new CSDCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public BooleanFilter getLocal() {
    return local;
  }

  public void setLocal(BooleanFilter local) {
    this.local = local;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CSDCriteria that = (CSDCriteria) o;
    return Objects.equals(id, that.id) && Objects.equals(local, that.local);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, local);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CSDCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (local != null ? "local=" + local + ", " : "")
        + "}";
  }
}
