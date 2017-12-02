package com.ryj.models;

/** Created by andrey on 12/1/17. */
public class Rate {
  private String mTitle;
  private double mValue;
  private String mKey;

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public double getValue() {
    return mValue;
  }

  public void setValue(double value) {
    mValue = value;
  }

  public String getKey() {
    return mKey;
  }

  public void setKey(String key) {
    mKey = key;
  }
}
