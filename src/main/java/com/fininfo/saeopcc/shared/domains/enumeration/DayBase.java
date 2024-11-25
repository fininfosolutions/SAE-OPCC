package com.fininfo.saeopcc.shared.domains.enumeration;

public enum DayBase {
  DAY_BASE_365(365),
  DAY_BASE_360(360),
  DAY_BASE_366(366),
  DAY_BASE_252(252);
  private final int value;

  DayBase(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
