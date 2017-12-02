package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

import java.util.List;

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
  private float mRating;

  @SerializedName("ratings_count")
  @Expose
  private int mRatingCount;

  @SerializedName("comments_count")
  @Expose
  private int mCommentsCount;

  @SerializedName("access_status")
  @Expose
  private int mAccessStatus;

  @SerializedName("rating_criteria")
  @Expose
  private List<Rating> mRatingCriteria;

  @SerializedName("is_favorite")
  @Expose
  private boolean mIsFavourite;

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
    return mAvatar;
  }

  public void setAvatar(Avatar avatar) {
    mAvatar = avatar;
  }

  public Court getCourt() {
    return mCourt;
  }

  public void setCourt(Court court) {
    mCourt = court;
  }

  public float getRating() {
    return mRating;
  }

  public void setRating(float rating) {
    mRating = rating;
  }

  public int getRatingCount() {
    return mRatingCount;
  }

  public void setRatingCount(int ratingCount) {
    mRatingCount = ratingCount;
  }

  public int getCommentsCount() {
    return mCommentsCount;
  }

  public void setCommentsCount(int commentsCount) {
    mCommentsCount = commentsCount;
  }

  public int getAccessStatus() {
    return mAccessStatus;
  }

  public void setAccessStatus(int accessStatus) {
    mAccessStatus = accessStatus;
  }

  public boolean isFavourite() {
    return mIsFavourite;
  }

  public void setIsFavourite(boolean isFavourite) {
    mIsFavourite = isFavourite;
  }

  public List<Rating> getRatingCriteria() {
    return mRatingCriteria;
  }

  public void setRatingCriteria(List<Rating> ratingCriteria) {
    this.mRatingCriteria = ratingCriteria;
  }
}
