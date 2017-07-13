package com.savitskiy.testsample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrey on 7/10/17.
 */

public class FieldValidation {
  public static boolean isValid(String string, String regExp) {
    Pattern pattern = Pattern.compile(regExp);
    Matcher matcher = pattern.matcher(string);
    return matcher.matches();
  }

}
