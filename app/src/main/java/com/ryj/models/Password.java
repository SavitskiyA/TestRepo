package com.ryj.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/** Created by andrey on 11/21/17. */
public class Password implements Serializable {
  @SerializedName("reset_password_token")
  @Expose
  private String mCurrentPassword;

  @SerializedName("password")
  @Expose
  private String mNewPassword;

  @SerializedName("password_confirmation")
  @Expose
  private String mRepeatPassword;

  public Password(String currentPassword, String newPassword, String repeatPassword) {
    mCurrentPassword = currentPassword;
    mNewPassword = newPassword;
    mRepeatPassword = repeatPassword;
  }

  public String getCurrentPassword() {
    return mCurrentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    mCurrentPassword = currentPassword;
  }

  public String getNewPassword() {
    return mNewPassword;
  }

  public void setNewPassword(String newPassword) {
    mNewPassword = newPassword;
  }

  public String getRepeatPassword() {
    return mRepeatPassword;
  }

  public void setRepeatPassword(String repeatPassword) {
    mRepeatPassword = repeatPassword;
  }
}
