package com.ryj;

public class Constants {

  public static final String PACKAGE_NAME = "com.ryj";
  public static final int TIMEOUT_DURATION_MS = 60;
  public static final int SPLASH_TEXT_APPEARANCE_DURATION_MS = 2000;
  public static final int MIN_PASSWORD_LENGTH = 8;
  public static final String PLATFORM = "web";
  public static final String EMAIL_PATTERN_JUDGE = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@court\\.gov\\.ua$";
  public static final String PHONE_PATTERN_UA = "^\\+380([0-9]{9})$";
  public static final String ONLY_LETTERS_PATTERN = "^[A-Za-z]$";
  private static final String BASE_HOST = "http://64192366.ngrok.io";
  private static final String VERSIONING = "/api/v1/";
  public static final String BASE_URL = BASE_HOST + VERSIONING;
}
