package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

  @SerializedName("id")
  @Expose
  private Integer mId;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    this.mId = id;
  }
}
