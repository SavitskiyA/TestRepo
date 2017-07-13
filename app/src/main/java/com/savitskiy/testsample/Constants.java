package com.savitskiy.testsample;

public class Constants {

  public static final String PACKAGE_NAME = "com.ryj";
  public static final int TIMEOUT_DURATION_MS = 60;
  public static final int SPLASH_TEXT_APPEARANCE_DURATION_MS = 2000;
  public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  public static final String EMAIL_PATTERN_JUDGE = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@court\\.gov\\.ua$";
  public static final String PHONE_PATTERN_UA = "^\\+380([0-9]{9})$";
  public static final String ONLY_LETTERS_PATTERN = "^[A-Za-z]$";
  private static final String BASE_HOST_URL = "http://dev.bookjane.com/";
  private static final String VERSIONING = "api/v3/";
  public static final String BASE_URL = BASE_HOST_URL + VERSIONING;

}
