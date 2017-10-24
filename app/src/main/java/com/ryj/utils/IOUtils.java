package com.ryj.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.ryj.Constants;
import com.ryj.models.Reflectable;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by andrey on 8/11/17.
 */

public class IOUtils {
  private final static String MAP_BRACKETS = "[]";
  private final static String MAP_POSTFIX = "]";

  public static Map<String, RequestBody> getDataAsMap(Reflectable object,
                                                      @Nullable String parentPrefix) {
    final String prefix = parentPrefix == null
            ? object.getFieldMapPrefix()
            : parentPrefix + object.getFieldMapPrefix();
    Field[] fields = object.getClass().getDeclaredFields();
    Map<String, RequestBody> map = new HashMap<>();
    SerializedName serialName;
    Class fieldType;
    Object value;
    for (Field field : fields) {
      try {
        field.setAccessible(true);
        fieldType = field.getType();
        serialName = field.getAnnotation(SerializedName.class);
        value = field.get(object);
        if (serialName == null || value == null) {
          continue;
        } else if (value instanceof Reflectable) {
          map.putAll(getDataAsMap((Reflectable) value, prefix));
        } else if (fieldType.isEnum()) {
          map.put(prefix + serialName.value() + MAP_POSTFIX,
                  toBody(value.toString()));
        } else if (List.class.isAssignableFrom(fieldType)) {
          mapList(map, (List) value, prefix + serialName.value() + MAP_POSTFIX);
        } else {
          map.put(prefix + serialName.value() + MAP_POSTFIX, toBody(value));
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return map;
  }


  private static void mapList(@NonNull Map<String, RequestBody> map, @Nullable List list,
                              @NonNull String prefix) {
    if (list == null || list.size() == 0) {
      return;
    }
    for (int i = 0, size = list.size(); i < size; i++) {
      map.put(prefix + MAP_BRACKETS, toBody(list.get(i).toString()));
    }
  }

  private static RequestBody toBody(Object value) {
    return RequestBody.create(MediaType.parse(Constants.TEXT_PLAIN), value.toString());
  }

  public static MultipartBody.Part toPart(File file) {
    RequestBody requestFile =
            RequestBody.create(
                    MediaType.parse(Constants.MULTIPART_FORM_DATA), file);

    return MultipartBody.Part.createFormData(file.getName(), file.getName(), requestFile);
  }
}
