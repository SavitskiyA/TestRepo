package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/31/17.
 */

public class RestorePasswordResponse {

  @SerializedName("message")
  @Expose
  private String mMessage;

  public String getMessage() {
    return mMessage;
  }

  public void setMessage(String message) {
    this.mMessage = message;
  }
}
