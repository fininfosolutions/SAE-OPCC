package com.fininfo.saeopcc.configuration;

/**
 * Filter class for {@link java.lang.Long} type attributes.
 *
 * @see RangeFilter
 */
public class LongFilter extends RangeFilter<Long> {

  private static final long serialVersionUID = 1L;

  /** Constructor for LongFilter. */
  public LongFilter() {}

  /**
   * Constructor for LongFilter.
   *
   * @param filter a {@link com.fininfo.sae.primarymarket.configuration.LongFilter} object.
   */
  public LongFilter(final LongFilter filter) {
    super(filter);
  }

  @Override
  public LongFilter copy() {
    return new LongFilter(this);
  }
}
