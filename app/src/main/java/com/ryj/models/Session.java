package com.ryj.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.Constants;

/**
 * Created by andrey on 7/28/17.
 */

public class Session {
  @SerializedName("device_platform")
  @Expose
  private String mPlatform = Constants.PLATFORM;

  @SerializedName("device_token")
  @Expose
  private String mToken;

  public String getPlatform() {
    return mPlatform;
  }

  public String getToken() {
    return mToken;
  }

  public void setToken(String token) {
    this.mToken = token;
  }
}
