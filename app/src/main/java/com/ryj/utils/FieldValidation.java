package com.ryj.utils;

import com.ryj.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrey on 7/10/17.
 */

public class FieldValidation {
  public static boolean isValid(String email, String regExp) {
    Pattern pattern = Pattern.compile(regExp);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isEmailFieldValid(String email) {
    return FieldValidation.isValid(email, StringUtils.EMAIL_PATTERN);
  }

  public static boolean isPasswordFieldValid(String password) {
    return password.length() >= Constants.MIN_PASSWORD_LENGTH;
  }
}
