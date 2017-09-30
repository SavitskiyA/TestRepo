package com.ryj.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/28/17.
 */

public class Account implements Reflectable {
  private final static String MAP_PREFIX = "account[";
  @SerializedName("email")
  @Expose
  private String mEmail;

  public Account() {
  }

  public Account(CharSequence email) {
    this.mEmail = email.toString().trim();
  }

  public String getEmail() {
    return mEmail;
  }

  public void setEmail(CharSequence email) {
    this.mEmail = email.toString().trim();
  }

  @NonNull
  @Override
  public String getFieldMapPrefix() {
    return MAP_PREFIX;
  }
}
