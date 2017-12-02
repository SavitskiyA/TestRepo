package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 8/10/17.
 */

public class Avatar {
  @SerializedName("origin")
  @Expose
  String mOrigin;
  @SerializedName("small")
  @Expose
  String mSmall;

  public String getOrigin() {
    return mOrigin;
  }

  public void setOrigin(String origin) {
    mOrigin = origin;
  }

  public String getSmall() {
    return mSmall;
  }

  public void setSmall(String small) {
    mSmall = small;
  }
}
