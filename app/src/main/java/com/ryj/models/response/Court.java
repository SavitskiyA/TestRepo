package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 8/17/17.
 */
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
  private Float mRating;

  @SerializedName("rating_marks_count")
  @Expose
  private Integer mRatingCount;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    this.mId = id;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getKind() {
    return mKind;
  }

  public void setKind(String kind) {
    this.mKind = kind;
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
}
