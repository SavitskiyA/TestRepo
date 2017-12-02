package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

/** Created by andrey on 11/2/17. */
public class Lawyer {
  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("first_name")
  @Expose
  private String mFirstName;

  @SerializedName("last_name")
  @Expose
  private String mLastName;

  @SerializedName("avatar_urls")
  @Expose
  private Avatar mAvatarUrls;

  @SerializedName("company")
  @Expose
  private String mCompany;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public String getFirstName() {
    return mFirstName == null ? StringUtils.EMPTY_STRING : mFirstName;
  }

  public void setFirstName(String firstName) {
    mFirstName = firstName;
  }

  public String getLastName() {
    return mLastName == null ? StringUtils.EMPTY_STRING : mLastName;
  }

  public void setLastName(String lastName) {
    mLastName = lastName;
  }

  public Avatar getAvatar() {
    return mAvatarUrls;
  }

  public void setAvatar(Avatar avatarUrls) {
    mAvatarUrls = avatarUrls;
  }

  public String getCompany() {
    return mCompany == null ? StringUtils.EMPTY_STRING : mCompany;
  }

  public void setCompany(String company) {
    mCompany = company;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Lawyer lawyer = (Lawyer) o;

    if (!mFirstName.equals(lawyer.mFirstName)) return false;
    return mLastName.equals(lawyer.mLastName);
  }

  @Override
  public int hashCode() {
    int result = mFirstName.hashCode();
    result = 31 * result + mLastName.hashCode();
    return result;
  }
}
