package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

import java.util.List;

/** Created by andrey on 8/17/17. */
public class Court {

  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("name")
  @Expose
  private String mName;

  @SerializedName("kind")
  @Expose
  private String mKind;

  @SerializedName("avg_rating")
  @Expose
  private float mRating;

  @SerializedName("ratings_count")
  @Expose
  private int mRatingCount;

  @SerializedName("judges")
  @Expose
  private List<Judge> mJudges;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public String getName() {
    return mName == null ? StringUtils.EMPTY_STRING : mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getKind() {
    return mKind == null ? StringUtils.EMPTY_STRING : mKind;
  }

  public void setKind(String kind) {
    mKind = kind;
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

  public List<Judge> getJudges() {
    return mJudges;
  }

  public void setJudges(List<Judge> judges) {
    mJudges = judges;
  }
}
