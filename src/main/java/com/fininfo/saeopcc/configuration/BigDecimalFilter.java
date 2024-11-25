package com.fininfo.saeopcc.configuration;

import java.math.BigDecimal;

public class BigDecimalFilter extends RangeFilter<BigDecimal> {

  private static final long serialVersionUID = 1L;

  /** Constructor for BigDecimalFilter. */
  public BigDecimalFilter() {}

  public BigDecimalFilter(final BigDecimalFilter filter) {
    super(filter);
  }

  @Override
  public BigDecimalFilter copy() {
    return new BigDecimalFilter(this);
  }
}
