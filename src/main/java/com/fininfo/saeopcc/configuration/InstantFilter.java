package com.fininfo.saeopcc.configuration;

import java.time.Instant;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class InstantFilter extends RangeFilter<Instant> {

  private static final long serialVersionUID = 1L;

  /** Constructor for InstantFilter. */
  public InstantFilter() {}

  public InstantFilter(final InstantFilter filter) {
    super(filter);
  }

  @Override
  public InstantFilter copy() {
    return new InstantFilter(this);
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setEquals(Instant equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setNotEquals(Instant equals) {
    super.setNotEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setIn(List<Instant> in) {
    super.setIn(in);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setNotIn(List<Instant> notIn) {
    super.setNotIn(notIn);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setGreaterThan(Instant equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setLessThan(Instant equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setGreaterThanOrEqual(Instant equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setGreaterOrEqualThan(Instant equals) {
    super.setGreaterOrEqualThan(equals);
    return this;
  }

  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setLessThanOrEqual(Instant equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }

  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setLessOrEqualThan(Instant equals) {
    super.setLessOrEqualThan(equals);
    return this;
  }
}
