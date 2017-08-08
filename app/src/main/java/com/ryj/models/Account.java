package com.ryj.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/28/17.
 */

public class Account {
  @SerializedName("email")
  @Expose
  private String mEmail;

  public Account(CharSequence email) {
    this.mEmail = email.toString().trim();
  }

  public String getEmail() {
    return mEmail;
  }

  public void setEmail(CharSequence email) {
    this.mEmail = email.toString().trim();
  }
}
