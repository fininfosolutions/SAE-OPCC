package com.fininfo.saeopcc.shared.domains.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/** The DayWeek enumeration. */
public enum DayWeek {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY;

  @JsonCreator
  public static DayWeek forName(String name) {
    for (DayWeek c : values()) {
      if (c.name().equals(name)) { // change accordingly
        return c;
      }
    }

    return null;
  }
}
