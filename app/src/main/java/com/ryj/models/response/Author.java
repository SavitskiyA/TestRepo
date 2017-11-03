package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 11/2/17.
 */

public class Author {
  @SerializedName("first_name")
  @Expose
  private String mFirstName;
  @SerializedName("last_name")
  @Expose
  private String mLastName;
  @SerializedName("avatar_urls")
  @Expose
  private Avatar mAvatarUrls;

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(String firstName) {
    this.mFirstName = firstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(String lastName) {
    this.mLastName = lastName;
  }

  public Avatar getAvatarUrls() {
    return mAvatarUrls;
  }

  public void setAvatarUrls(Avatar avatarUrls) {
    this.mAvatarUrls = avatarUrls;
  }
}
