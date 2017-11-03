package com.ryj.models.response;

/**
 * Created by andrey on 11/2/17.
 */

public class Mark {
  private String mName;
  private int mValue;
  private int mDenominator = 10;

  public Mark(String mName, int mValue) {
    this.mName = mName;
    this.mValue = mValue;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    this.mName = mName;
  }

  public int getValue() {
    return mValue;
  }

  public void setValue(int value) {
    this.mValue = mValue;
  }

  public int getDenominator() {
    return mDenominator;
  }

  public void setDenominator(int denominator) {
    this.mDenominator = mDenominator;
  }
}
