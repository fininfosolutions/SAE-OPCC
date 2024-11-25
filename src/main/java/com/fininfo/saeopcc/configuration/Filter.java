package com.fininfo.saeopcc.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Filter<T1> implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient T1 equals;
  private transient T1 notEquals;
  private transient Boolean specified;
  private transient List<T1> in;
  private transient List<T1> notIn;

  /** Constructor for Filter. */
  public Filter() {}

  public Filter(Filter<T1> filter) {
    this.equals = filter.equals;
    this.notEquals = filter.notEquals;
    this.specified = filter.specified;
    this.in = filter.in == null ? null : new ArrayList<>(filter.in);
    this.notIn = filter.notIn == null ? null : new ArrayList<>(filter.notIn);
  }

  public Filter<T1> copy() {
    return new Filter<>(this);
  }

  public T1 getEquals() {
    return equals;
  }

  public Filter<T1> setEquals(T1 equals) {
    this.equals = equals;
    return this;
  }

  public T1 getNotEquals() {
    return notEquals;
  }

  public Filter<T1> setNotEquals(T1 notEquals) {
    this.notEquals = notEquals;
    return this;
  }

  public Boolean getSpecified() {
    return specified;
  }

  public Filter<T1> setSpecified(Boolean specified) {
    this.specified = specified;
    return this;
  }

  public List<T1> getIn() {
    return in;
  }

  public Filter<T1> setIn(List<T1> in) {
    this.in = in;
    return this;
  }

  public List<T1> getNotIn() {
    return notIn;
  }

  public Filter<T1> setNotIn(List<T1> notIn) {
    this.notIn = notIn;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Filter<?> filter = (Filter<?>) o;
    return Objects.equals(equals, filter.equals)
        && Objects.equals(notEquals, filter.notEquals)
        && Objects.equals(specified, filter.specified)
        && Objects.equals(in, filter.in)
        && Objects.equals(notIn, filter.notIn);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(equals, notEquals, specified, in, notIn);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getFilterName()
        + " ["
        + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
        + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
        + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
        + (getIn() != null ? "in=" + getIn() + ", " : "")
        + (getNotIn() != null ? "notIn=" + getNotIn() : "")
        + "]";
  }

  protected String getFilterName() {
    return this.getClass().getSimpleName();
  }
}
