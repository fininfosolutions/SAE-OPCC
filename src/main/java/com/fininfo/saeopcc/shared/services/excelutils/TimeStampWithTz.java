package com.fininfo.saeopcc.shared.services.excelutils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class TimeStampWithTz extends Date {
  Timestamp ts;

  TimeZone tz;

  public TimeStampWithTz(Timestamp ts, TimeZone tz) {
    super(ts.getTime());
    this.ts = ts;
    this.tz = tz;
  }

  public TimeZone getTimeZone() {
    return tz;
  }

  public Timestamp getTimestamp() {
    return ts;
  }

  public Calendar getCalendar() {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.setTimeInMillis(ts.getTime());
    calendar.setTimeZone(tz);
    return calendar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    TimeStampWithTz that = (TimeStampWithTz) o;
    return Objects.equals(ts, that.ts) && Objects.equals(tz, that.tz);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), ts, tz);
  }
}
