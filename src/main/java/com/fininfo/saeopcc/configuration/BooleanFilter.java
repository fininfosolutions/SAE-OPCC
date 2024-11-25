package com.fininfo.saeopcc.configuration;

/**
 * Class for filtering attributes with {@link java.lang.Boolean} type. It can be added to a criteria
 * class as a member, to support the following query parameters:
 *
 * <pre>
 *      fieldName.equals=true
 *      fieldName.notEquals=true
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=true,false
 *      fieldName.notIn=true,false
 * </pre>
 */
public class BooleanFilter extends Filter<Boolean> {

  private static final long serialVersionUID = 1L;

  /** Constructor for BooleanFilter. */
  public BooleanFilter() {}

  /**
   * Constructor for BooleanFilter.
   *
   * @param filter a {@link com.fininfo.saeopcc.configuration.BooleanFilter} object.
   */
  public BooleanFilter(final BooleanFilter filter) {
    super(filter);
  }

  /**
   * copy.
   *
   * @return a {@link com.fininfo.saeopcc.configuration.BooleanFilter} object.
   */
  @Override
  public BooleanFilter copy() {
    return new BooleanFilter(this);
  }
}
