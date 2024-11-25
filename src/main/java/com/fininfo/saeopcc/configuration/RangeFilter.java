package com.fininfo.saeopcc.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Filter class for Comparable types, where less than / greater than / etc relations could be
 * interpreted. It can be added to a criteria class as a member, to support the following query
 * parameters:
 *
 * <pre>
 *      fieldName.equals=42
 *      fieldName.notEquals=42
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=43,42
 *      fieldName.notIn=43,42
 *      fieldName.greaterThan=41
 *      fieldName.lessThan=44
 *      fieldName.greaterThanOrEqual=42
 *      fieldName.lessThanOrEqual=44
 * </pre>
 *
 * Due to problems with the type conversions, the descendant classes should be used, where the
 * generic type parameter is materialized.
 *
 * @param <T> the type of filter.
 * @see IntegerFilter
 * @see DoubleFilter
 * @see FloatFilter
 * @see LongFilter
 * @see LocalDateFilter
 * @see InstantFilter
 * @see ShortFilter
 * @see ZonedDateTimeFilter
 */
public class RangeFilter<T extends Comparable<? super T>> extends Filter<T> {

  private static final long serialVersionUID = 1L;

  private transient T greaterThan;
  private transient T lessThan;
  private transient T greaterThanOrEqual;
  private transient T lessThanOrEqual;

  /** Constructor for RangeFilter. */
  public RangeFilter() {}

  /**
   * Constructor for RangeFilter.
   *
   * @param filter a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter(final RangeFilter<T> filter) {
    super(filter);
    this.greaterThan = filter.greaterThan;
    this.lessThan = filter.lessThan;
    this.greaterThanOrEqual = filter.greaterThanOrEqual;
    this.lessThanOrEqual = filter.lessThanOrEqual;
  }

  /**
   * copy.
   *
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  @Override
  public RangeFilter<T> copy() {
    return new RangeFilter<>(this);
  }

  /**
   * Getter for the field <code>greaterThan</code>.
   *
   * @return a T object.
   */
  public T getGreaterThan() {
    return greaterThan;
  }

  /**
   * Setter for the field <code>greaterThan</code>.
   *
   * @param greaterThan a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter<T> setGreaterThan(T greaterThan) {
    this.greaterThan = greaterThan;
    return this;
  }

  /**
   * Getter for the field <code>lessThan</code>.
   *
   * @return a T object.
   */
  public T getLessThan() {
    return lessThan;
  }

  /**
   * Setter for the field <code>lessThan</code>.
   *
   * @param lessThan a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter<T> setLessThan(T lessThan) {
    this.lessThan = lessThan;
    return this;
  }

  /**
   * Getter for the field <code>greaterThanOrEqual</code>.
   *
   * @return a T object.
   */
  public T getGreaterThanOrEqual() {
    return greaterThanOrEqual;
  }

  /**
   * Setter for the field <code>greaterThanOrEqual</code>.
   *
   * @param greaterThanOrEqual a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter<T> setGreaterThanOrEqual(T greaterThanOrEqual) {
    this.greaterThanOrEqual = greaterThanOrEqual;
    return this;
  }

  /**
   * Setter for the field <code>greaterThanOrEqual</code>.
   *
   * @param greaterThanOrEqual a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object. Equivalent to
   *     {@link #setLessThanOrEqual}
   */
  public RangeFilter<T> setGreaterOrEqualThan(T greaterThanOrEqual) {
    this.setGreaterThanOrEqual(greaterThanOrEqual);
    return this;
  }

  /**
   * Getter for the field <code>lessThanOrEqual</code>.
   *
   * @return a T object.
   */
  public T getLessThanOrEqual() {
    return lessThanOrEqual;
  }

  /**
   * Setter for the field <code>lessThanOrEqual</code>.
   *
   * @param lessThanOrEqual a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter<T> setLessThanOrEqual(T lessThanOrEqual) {
    this.lessThanOrEqual = lessThanOrEqual;
    return this;
  }

  /**
   * Setter for the field <code>lessThanOrEqual</code>.
   *
   * @param lessThanOrEqual a T object.
   * @return a {@link com.fininfo.sae.primarymarket.configuration.RangeFilter} object.
   */
  public RangeFilter<T> setLessOrEqualThan(T lessThanOrEqual) {
    this.setLessThanOrEqual(lessThanOrEqual);
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final RangeFilter<?> that = (RangeFilter<?>) o;
    return (Objects.equals(greaterThan, that.greaterThan)
        && Objects.equals(lessThan, that.lessThan)
        && Objects.equals(greaterThanOrEqual, that.greaterThanOrEqual)
        && Objects.equals(lessThanOrEqual, that.lessThanOrEqual));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return (getFilterName()
        + " ["
        + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
        + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
        + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
        + (getIn() != null ? "in=" + getIn() + ", " : "")
        + (getNotIn() != null ? "notIn=" + getNotIn() + ", " : "")
        + (getGreaterThan() != null ? "greaterThan=" + getGreaterThan() + ", " : "")
        + (getLessThan() != null ? "lessThan=" + getLessThan() + ", " : "")
        + (getGreaterThanOrEqual() != null
            ? "greaterThanOrEqual=" + getGreaterThanOrEqual() + ", "
            : "")
        + (getLessThanOrEqual() != null ? "lessThanOrEqual=" + getLessThanOrEqual() : "")
        + "]");
  }

  public Map<String, String> getOperatorAndDate() {
    Map<String, String> result = new HashMap<>();
    if (getEquals() != null) {
      result.put("operator", "equals");
      result.put("date", getEquals().toString());
    } else if (getNotEquals() != null) {
      result.put("operator", "notEquals");
      result.put("date", getNotEquals().toString());
    } else if (getIn() != null && !getIn().isEmpty()) {
      result.put("operator", "in");
      result.put("date", getIn().toString());
    } else if (getNotIn() != null && !getNotIn().isEmpty()) {
      result.put("operator", "notIn");
      result.put("date", getNotIn().toString());
    } else if (getGreaterThan() != null) {
      result.put("operator", "greaterThan");
      result.put("date", getGreaterThan().toString());
    } else if (getLessThan() != null) {
      result.put("operator", "lessThan");
      result.put("date", getLessThan().toString());
    } else if (getGreaterThanOrEqual() != null) {
      result.put("operator", "greaterThanOrEqual");
      result.put("date", getGreaterThanOrEqual().toString());
    } else if (getLessThanOrEqual() != null) {
      result.put("operator", "lessThanOrEqual");
      result.put("date", getLessThanOrEqual().toString());
    }
    return result;
  }
}
