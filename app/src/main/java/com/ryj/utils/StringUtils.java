package com.ryj.utils;

import java.util.List;

public class StringUtils {
  public static final String ZERO = "0";
  public static final String SLASH = "/";
  public static final String COMA = ",";
  public static final String SPACE = " ";
  public static final String EMPTY_STRING = "";
  public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  public static final String DOT_COMMA = ";";
  public static final String DOT_COMMA_SPACE = "; ";
  public static final String EMAIL_PATTERN_JUDGE = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@court\\.gov\\.ua$";
  public static final String PHONE_PATTERN_UA = "^(\\+380([0-9]{9})){0,1}$";
  public static final String ONLY_LETTERS_PATTERN = "^[A-Za-z]+$";
  public static final String LETTERS_SPACE_PATTERN = "^[A-Za-z]+([ ][A-Za-z]+)*$";
  public static final String ONLY_DIGITS_PATTERN = "^[0-9]+$";

  public static String getStringFromList(List<String> list, String separator) {
    StringBuilder builder = new StringBuilder();
    for (String string : list) {
      builder.append(string).append(separator);
    }
    if (builder.toString().length() > 0) {
      return builder.toString().substring(0, builder.toString().length() - 2);
    } else {
      return EMPTY_STRING;
    }
  }
}
