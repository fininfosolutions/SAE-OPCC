package com.fininfo.saeopcc.configuration;

public class IntegerFilter extends RangeFilter<Integer> {

  private static final long serialVersionUID = 1L;

  /** Constructor for IntegerFilter. */
  public IntegerFilter() {}

  public IntegerFilter(final IntegerFilter filter) {
    super(filter);
  }

  @Override
  public IntegerFilter copy() {
    return new IntegerFilter(this);
  }
}
