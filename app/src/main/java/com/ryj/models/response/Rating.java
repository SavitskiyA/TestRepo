package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

/** Created by andrey on 12/1/17. */
public class Rating {
  @SerializedName("key")
  @Expose
  private String mKey;

  @SerializedName("value")
  @Expose
  private double mValue;

  @SerializedName("title")
  @Expose
  private String mTitle;

  public String getKey() {
    return mKey == null ? StringUtils.EMPTY_STRING : mKey;
  }

  public void setKey(String key) {
    mKey = key;
  }

  public double getValue() {
    return mValue;
  }

  public void setValue(double value) {
    value = value;
  }

  public String getTitle() {
    return mTitle == null ? StringUtils.EMPTY_STRING : mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }
}
