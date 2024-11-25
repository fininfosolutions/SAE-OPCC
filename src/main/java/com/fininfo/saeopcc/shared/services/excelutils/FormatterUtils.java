package com.fininfo.saeopcc.shared.services.excelutils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormatterUtils {

  private FormatterUtils() {}
  ;

  /**
   * format every type object, use runtime type checking
   *
   * @param obj
   * @param pattern
   * @return the string content
   */
  public static String fm(Object obj, String pattern) {
    if (obj == null) {
      return null;
    }

    // the order is on purpose here for the appear rank
    if (obj instanceof String) {
      return obj.toString();
    } else if (obj
        instanceof
        Integer) { // not call the format(int i, String pattern) method to avoid auto cast from
      // Integer to int
      return obj.toString();
    } else if (obj instanceof Long) {
      return obj.toString();
    } else if (obj instanceof Date) {
      return formatDate(pattern == null ? "dd-MM-yyyy" : pattern, (Date) obj);
    } else if (obj instanceof Boolean) {
      return obj.toString();
    } else if (obj instanceof BigDecimal) {
      return ((BigDecimal) obj).toPlainString();
    } else if (obj instanceof Double) {
      return obj.toString();
    } else if (obj instanceof Float) {
      return obj.toString();
    } else if (obj instanceof Character) {
      return obj.toString();
    } else if (obj instanceof char[]) {
      return format((char[]) obj, null);
    } else if (obj instanceof byte[]) {
      return format((byte[]) obj, null);
    }

    return obj.toString();
  }

  public static String format(Object obj, String pattern) {
    return (obj == null) ? null : obj.toString();
  }

  public static String format(Date date, String pattern) {
    if (date != null) {
      return formatDate(pattern == null ? "dd-MM-yyyy" : pattern, date);
    } else {
      return null;
    }
  }

  public static String format_Date(java.util.Date date, String pattern) {
    if (date != null) {
      return formatDate(pattern == null ? "dd-MM-yyyy" : pattern, date);
    } else {
      return null;
    }
  }

  public static synchronized String formatDate(String pattern, java.util.Date date) {
    DateFormat format = FastDateParser.getInstance(pattern);
    if (date instanceof TimeStampWithTz) {
      format.setTimeZone(((TimeStampWithTz) date).getTimeZone());
    } else {
      format.setTimeZone(TimeZone.getDefault());
    }
    return format.format(date);
  }
}
