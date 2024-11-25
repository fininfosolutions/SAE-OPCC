package com.fininfo.saeopcc.configuration;

public class DoubleFilter extends RangeFilter<Double> {

  private static final long serialVersionUID = 1L;

  /** Constructor for DoubleFilter. */
  public DoubleFilter() {}

  public DoubleFilter(final DoubleFilter filter) {
    super(filter);
  }

  @Override
  public DoubleFilter copy() {
    return new DoubleFilter(this);
  }
}
