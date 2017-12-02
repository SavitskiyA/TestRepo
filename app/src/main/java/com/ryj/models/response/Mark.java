package com.ryj.models.response;

import javax.annotation.Nullable;

/**
 * Created by andrey on 11/2/17.
 */

public class Mark {
  private String mMarkNameClient;
  private double mValue;
  private int mDenominator = 10;

  public Mark(String mName, @Nullable Double value) {
    this.mMarkNameClient = mName;
    if (value != null) {
      this.mValue = mValue;
    } else {
      this.mValue = 0;
    }

  }

  public String getMarkNameClient() {
    return mMarkNameClient;
  }

  public void setMarkNameClient(String name) {
    this.mMarkNameClient = name;
  }

  public double getValue() {
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
