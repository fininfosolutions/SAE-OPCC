package com.fininfo.saeopcc.configuration;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class LocalDateFilter extends RangeFilter<LocalDate> {

  private static final long serialVersionUID = 1L;

  /** Constructor for LocalDateFilter. */
  public LocalDateFilter() {}

  /**
   * Constructor for LocalDateFilter.
   *
   * @param filter a {@link com.fininfo.sae.primarymarket.configuration.LocalDateFilter} object.
   */
  public LocalDateFilter(final LocalDateFilter filter) {
    super(filter);
  }

  /**
   * copy.
   *
   * @return a {@link com.fininfo.sae.primarymarket.configuration.LocalDateFilter} object.
   */
  @Override
  public LocalDateFilter copy() {
    return new LocalDateFilter(this);
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setEquals(LocalDate equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setNotEquals(LocalDate equals) {
    super.setNotEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setIn(List<LocalDate> in) {
    super.setIn(in);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setNotIn(List<LocalDate> notIn) {
    super.setNotIn(notIn);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThan(LocalDate equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThan(LocalDate equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThanOrEqual(LocalDate equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterOrEqualThan(LocalDate equals) {
    super.setGreaterOrEqualThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThanOrEqual(LocalDate equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateFilter setLessOrEqualThan(LocalDate equals) {
    super.setLessOrEqualThan(equals);
    return this;
  }
}
