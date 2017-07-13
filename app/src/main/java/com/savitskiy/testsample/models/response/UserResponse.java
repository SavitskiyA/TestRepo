package com.savitskiy.testsample.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.savitskiy.testsample.models.User;


public class UserResponse {

  @SerializedName("object")
  @Expose
  private User mObject;

  public User getObject() {
    return mObject;
  }

  public void setObject(User object) {
    mObject = object;
  }
}
