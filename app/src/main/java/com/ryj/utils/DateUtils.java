package com.ryj.utils;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Created by andrey on 11/16/17. */
public class DateUtils {
  private static String getFormatedDate(@Nullable String inputData, @NonNull String clientTemplate)
      throws ParseException {
    if (inputData != null && inputData.length() > 10) {
      return new SimpleDateFormat(clientTemplate, Resources.getSystem().getConfiguration().locale)
          .format(getDate(inputData.substring(0, inputData.lastIndexOf(StringUtils.DOT))));
    } else {
      throw new RuntimeException("input date is null or is wrong format");
    }
  }

  @Nullable
  private static Date getDate(String inputServerDate) throws ParseException {
    return new SimpleDateFormat(
            StringUtils.DATE_TEMPLATE_SERVER, Resources.getSystem().getConfiguration().locale)
        .parse(inputServerDate);
  }

  private static boolean isToday(Date date) {
    Calendar today = Calendar.getInstance();
    today.setTime(new Date());
    int todayDay = today.get(Calendar.DAY_OF_MONTH);
    int todayMonth = today.get(Calendar.MONTH);
    int todayYear = today.get(Calendar.YEAR);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
    int calendarMonth = calendar.get(Calendar.MONTH);
    int calendarYear = calendar.get(Calendar.YEAR);
    return ((todayDay == calendarDay)
        && (todayMonth == calendarMonth)
        && (todayYear == calendarYear));
  }

  public static String getFormatedDate(String inputServerDate) throws ParseException {
    if (isToday(getDate(inputServerDate))) {
      return getFormatedDate(inputServerDate, StringUtils.DATE_TEMPLATE_CLIENT_TODAY);
    } else {
      return getFormatedDate(inputServerDate, StringUtils.DATE_TEMPLATE_CLIENT);
    }
  }
}
