package com.ryj.utils;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.models.User;
import com.ryj.models.response.Judge;
import com.ryj.models.response.Lawyer;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
  public static final String ZERO = "0";
  public static final String SLASH = "/";
  public static final String COMA = ",";
  public static final String DOT = ".";
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
  public static final String DATE_TEMPLATE_CLIENT = "dd.MM.yyyy 'at' HH:mm";
  public static final String DATE_TEMPLATE_CLIENT_TODAY = "'Today at' HH:mm";
  public static final String DATE_TEMPLATE_SERVER = "yyyy-MM-dd'T'HH:mm:ss";
  private static final LruCache<Integer, String> CACHE_JUDGE = new LruCache<>(1000);
  private static final LruCache<Integer, String> CACHE_JUDGE_ABBR = new LruCache<>(1000);
  private static final LruCache<Integer, String> CACHE_AUTHOR_ABBR = new LruCache<>(1000);

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

  public static String getAbbrFromFullName(@Nullable Lawyer lawyer) {
    if (lawyer == null) {
      return EMPTY_STRING;
    }
    String cacheData = CACHE_AUTHOR_ABBR.get(lawyer.hashCode());
    if (cacheData != null) {
      return cacheData;
    }
    StringBuilder builder = new StringBuilder();
    cacheData =
        builder
            .append(lawyer.getLastName().substring(0, 1))
            .append(lawyer.getFirstName().substring(0, 1))
            .toString();
    CACHE_AUTHOR_ABBR.put(lawyer.hashCode(), cacheData);
    return cacheData;
  }

  public static String toUpperCase(String value, int from, int to) {
    if (from >= 0 && to > 0) {
      if (from < to && to <= value.length()) {
        return new StringBuilder()
            .append(value.substring(from, to).toUpperCase())
            .append(value.substring(to))
            .toString();
      } else {
        throw new RuntimeException();
      }
    } else {
      throw new RuntimeException();
    }
  }

  public static String firstUpperCase(String value) {
    return value.substring(0, 1).toUpperCase().concat(value.substring(1));
  }

  public static String getFullName(Lawyer lawyer) {
    return lawyer.getLastName() + SPACE + lawyer.getFirstName();
  }

  public static int getFullNameLength(@Nullable Lawyer lawyer) {
    return getFullName(lawyer).length();
  }

  public static boolean isPasswordValid(String password) {
    return password.length() > Constants.MIN_PASSWORD_LENGTH && password.length() > 0;
  }

  public static boolean isFieldEmpty(TextView... views) {
    for (TextView v : views) {
      if (TextUtils.isEmpty(v.getText())) return false;
    }
    return true;
  }
}
