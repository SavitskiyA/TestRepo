package com.ryj.utils;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import com.ryj.models.User;
import com.ryj.models.response.Judge;

import java.util.ArrayList;
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
  public static final String JUDGES_WERE_FOUND = "judges were found";
  private static final LruCache<Integer, String> CACHE_JUDGE = new LruCache<>(1000);
  private static final LruCache<Integer, String> CACHE_JUDGE_ABBR = new LruCache<>(1000);

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

  public static String getStringFromList(List<String> list, boolean[] booleans, String separator) {
    if (list != null && booleans != null && list.size() == booleans.length) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < list.size(); i++) {
        if (booleans[i]) builder.append(list.get(i)).append(separator);
      }
      if (builder.toString().length() > 0) {
        return builder.toString().substring(0, builder.toString().length() - 2);
      } else {
        return EMPTY_STRING;
      }
    } else {
      throw new RuntimeException("list is not equals booleans");
    }
  }

  public static <T> List<T> getListFromList(List<T> list, boolean[] booleans) {
    if (list != null && booleans != null && list.size() == booleans.length) {
      List<T> result = new ArrayList<T>();
      for (int i = 0; i < booleans.length; i++) {
        if (booleans[i]) {
          result.add(list.get(i));
        }
      }
      return result;
    } else {
      throw new RuntimeException("list is not equals booleans");
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

  public static int getFullNameLength(@Nullable Judge judge) {
    return getFullName(judge).length();
  }

  public static String getAbbrFromFullName(@Nullable Judge judge) {
    if (judge == null || judge.getId() == null) {
      return EMPTY_STRING;
    }
    String cacheData = CACHE_JUDGE_ABBR.get(judge.getId());
    if (cacheData != null) {
      return cacheData;
    }
    StringBuilder builder = new StringBuilder();
    cacheData =
        builder
            .append(judge.getLastName().substring(0, 1))
            .append(judge.getFirstName().substring(0, 1))
            .toString();
    CACHE_JUDGE_ABBR.put(judge.getId(), cacheData);
    return cacheData;
  }

}
