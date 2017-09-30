package com.ryj.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.Constants;

/**
 * Created by andrey on 7/28/17.
 */

public class Session implements Reflectable {
  private final static String MAP_PREFIX = "session[";
  @SerializedName("device_platform")
  @Expose
  private String mPlatform = Constants.PLATFORM;
  @SerializedName("device_token")
  @Expose
  private String mToken;

  public Session() {
  }

  public Session(String token) {
    this.mToken = token;
  }

  public String getPlatform() {
    return mPlatform;
  }

  public String getToken() {
    return mToken;
  }

  public void setToken(String token) {
    this.mToken = token;
  }

  @NonNull
  @Override
  public String getFieldMapPrefix() {
    return MAP_PREFIX;
  }
}
