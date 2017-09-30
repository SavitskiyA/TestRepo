package com.ryj;

public class Constants {

  public static final String PACKAGE_NAME = "com.ryj";
  public static final int TIMEOUT_DURATION_MS = 60;
  public static final int SPLASH_TEXT_APPEARANCE_DURATION_MS = 2000;
  public static final int MIN_PASSWORD_LENGTH = 7;
  public static final String PLATFORM = "android";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_CONTENT_TYPE = "application/json";
  public static final int RESPONSE_STATUS_UNAUTHORIZED = 401;
  public static final int RESPONSE_STATUS_MISSING_PARAM = 400;
  public static final int RESPONSE_STATUS_NOT_FOUND = 404;
  public static final int RESPONSE_STATUS_SERVER_ERROR = 500;
  public static final int RESPONSE_STATUS_INVALID_PLATFORM = 422;
  public static final String TEXT_PLAIN = "text/plain";
  public static final String MULTIPART_FORM_DATA = "multipart/form-data";
  public static final int IMAGE_TYPE = 1;
  public static final int UA_PHONE_NUMBER_DIGITS_COUNT = 13;
  public static final int UA_PHONE_NUMBER_MASK_DOGITS_COUNT = 4;
  public static final String HEADER_SESSION_TOKEN = "Session-Token";
  private static final String BASE_HOST = "http://api.rateyourjudge.org/";
  private static final String VERSIONING = "v1/";
  public static final String BASE_URL = BASE_HOST + VERSIONING;
}
