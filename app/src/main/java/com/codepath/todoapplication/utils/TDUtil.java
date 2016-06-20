package com.codepath.todoapplication.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TDUtil {
  public static final String DATE_FORMATTER = "MMMM dd, yyyy";

  public static long convertDateToMillis(String formattedDate) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER);
      Date date = sdf.parse(formattedDate);
      return date.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    }
  }

  public static String convertMillisToDate(long milliseconds) {
    return DateFormat.format(DATE_FORMATTER, new Date(milliseconds)).toString();
  }

  public static long getCurrentDateInMillis() {
    Calendar c = Calendar.getInstance();
    return c.getTimeInMillis();
  }
}
