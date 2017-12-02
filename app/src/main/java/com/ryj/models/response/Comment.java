package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

/** Created by andrey on 11/2/17. */
public class Comment {
  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("body")
  @Expose
  private String mBody;

  @SerializedName("created_at")
  @Expose
  private String mCreatedAt;

  @SerializedName("likes_count")
  @Expose
  private int mLikesCount;

  @SerializedName("dislikes_count")
  @Expose
  private int mDislikesCount;

  @SerializedName("author")
  @Expose
  private Lawyer mLawyer;

  @SerializedName("judge")
  @Expose
  private Judge mJudge;

  @SerializedName("vote_kind")
  @Expose
  private Integer mVoteKind;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public String getBody() {
    return mBody == null ? StringUtils.EMPTY_STRING : mBody;
  }

  public void setBody(String body) {
    mBody = body;
  }

  public String getCreatedAt() {
    return mCreatedAt;
  }

  public void setCreatedAt(String createdAt) {
    mCreatedAt = createdAt;
  }

  public int getLikesCount() {
    return mLikesCount;
  }

  public void setLikesCount(int likesCount) {
    mLikesCount = likesCount;
  }

  public int getDislikesCount() {
    return mDislikesCount;
  }

  public void setDislikesCount(int dislikesCount) {
    mDislikesCount = dislikesCount;
  }

  public Lawyer getLawyer() {
    return mLawyer;
  }

  public void setLawyer(Lawyer lawyer) {
    mLawyer = lawyer;
  }

  public Judge getJudge() {
    return mJudge;
  }

  public void setJudge(Judge judge) {
    mJudge = judge;
  }

  public Integer getVoteKind() {
    return mVoteKind;
  }

  public void setVoteKind(Integer voteKind) {
    mVoteKind = voteKind;
  }
}
