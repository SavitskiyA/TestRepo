package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/** Created by andrey on 8/17/17. */
public class Judge {
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
  private Avatar mAvatar;

  @SerializedName("court")
  @Expose
  private Court mCourt;

  @SerializedName("avg_rating")
  @Expose
  private Float mRating;

  @SerializedName("rating_marks_count")
  @Expose
  private Integer mRatingCount;

  @SerializedName("comments_count")
  @Expose
  private Integer mCommentsCount;

  @SerializedName("access_status")
  @Expose
  private Integer mAccessStatus;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    this.mId = id;
  }

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

  public Avatar getAvatar() {
    return mAvatar;
  }

  public void setAvatar(Avatar avatar) {
    this.mAvatar = avatar;
  }

  public Court getCourt() {
    return mCourt;
  }

  public void setCourt(Court court) {
    this.mCourt = court;
  }

  public Float getRating() {
    return mRating;
  }

  public void setRating(Float rating) {
    this.mRating = rating;
  }

  public Integer getRatingCount() {
    return mRatingCount;
  }

  public void setRatingCount(Integer ratingCount) {
    this.mRatingCount = ratingCount;
  }

  public Integer getCommentsCount() {
    return mCommentsCount;
  }

  public void setCommentsCount(Integer commentsCount) {
    this.mCommentsCount = commentsCount;
  }

  public Integer getAccessStatus() {
    return mAccessStatus;
  }

  public void setAccessStatus(Integer accessStatus) {
    this.mAccessStatus = accessStatus;
  }
}
