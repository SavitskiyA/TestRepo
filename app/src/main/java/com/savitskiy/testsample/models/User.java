package com.savitskiy.testsample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

  @SerializedName("first_name")
  @Expose
  private String mFirstName;

  @SerializedName("last_name")
  @Expose
  private String mLastName;

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(String firstName) {
    mFirstName = firstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(String lastName) {
    mLastName = lastName;
  }
}

