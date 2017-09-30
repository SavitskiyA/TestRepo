package com.ryj.utils;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import com.ryj.models.User;
import com.ryj.models.response.Judge;

import java.util.List;

public class StringUtils {
  public static final String ZERO = "0";
  public static final String SLASH = "/";
  public static final String COMA = ",";
  public static final String SPACE = " ";
  public static final String EMPTY_STRING = "";
  public static final String EMAIL_PATTERN =
      "^([ ]*)[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}([ ]*))$";
  public static final String DOT_COMMA = ";";
  public static final String DOT_COMMA_SPACE = "; ";
  public static final String EMAIL_PATTERN_JUDGE =
      "^([ ]*)[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@court\\.gov\\.ua([ ]*)$";
  public static final String PHONE_PATTERN_UA = "^([ ]*)(\\+380){1}([0-9]{9}){1}([ ]*)$";
  public static final String ONLY_LETTERS_PATTERN = "^([ ]*)[A-Za-zА-Яа-яЁёІіҐґЇї]+([ ]*)$";
  public static final String SURNAME_PATTERN =
      "^([ ]*)[A-Za-zА-Яа-яЁёІіҐґЇї]+([ ][A-Za-zА-Яа-яЁёІіҐґЇї]+)*([ ]*)$";
  public static final String LETTERS_SPACE_PATTERN =
      "^([ ]*)[A-Za-zА-Яа-яЁёІіҐґЇї]+([ ][A-Za-zА-Яа-яЁёІіҐґЇї]+)*([ ]*)$";
  public static final String ONLY_DIGITS_PATTERN = "^([ ]*)[0-9]+([ ]*)$";
  public static final String LETTERS_DIGITS_PATTERN = "^([ ]*)[A-Za-zА-Яа-яЁёІіҐґЇї0-9_ ]*([ ]*)$";
  private static final LruCache<Integer, String> CACHE_JUDGE = new LruCache<>(1000);

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

  @Nullable
  public static String getFullName(@Nullable Judge judge) {
    if (judge == null || judge.getId() == null) {
      return EMPTY_STRING;
    }
    String cacheData = CACHE_JUDGE.get(judge.getId());
    if (cacheData != null) {
      return cacheData;
    }
    StringBuilder builder = new StringBuilder();
    cacheData =
        builder.append(judge.getLastName()).append(SPACE).append(judge.getFirstName()).toString();
    CACHE_JUDGE.put(judge.getId(), cacheData);
    return cacheData;
  }

  public static String getFullName(User user) {
    return user.getLastName() + SPACE + user.getFirstName();
  }
}
