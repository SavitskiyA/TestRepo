package com.ryj.eventbus;

/**
 * Created by andrey on 7/18/17.
 */

public class CategoryEvent {
  private String mString;

  public CategoryEvent(String mString) {
    this.mString = mString;
  }

  public String getString() {
    return mString;
  }

  public void setString(String mString) {
    this.mString = mString;
  }
}
