package com.fininfo.saeopcc.configuration;

public class FloatFilter extends RangeFilter<Float> {

  private static final long serialVersionUID = 1L;

  /** Constructor for FloatFilter. */
  public FloatFilter() {}

  public FloatFilter(final FloatFilter filter) {
    super(filter);
  }

  @Override
  public FloatFilter copy() {
    return new FloatFilter(this);
  }
}
