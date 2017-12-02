package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

/** Created by andrey on 11/23/17. */
public class Vote {
  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("judge_id")
  @Expose
  private Integer mJudgeId;

  @SerializedName("lawyer_id")
  @Expose
  private Integer mLawyerId;

  @SerializedName("body")
  @Expose
  private String mBody;

  @SerializedName("created_at")
  @Expose
  private String mCreatedAt;

  @SerializedName("updated_at")
  @Expose
  private String mUpdatedAt;

  @SerializedName("likes_count")
  @Expose
  private int mLikesCount;

  @SerializedName("dislikes_count")
  @Expose
  private int mDislikesCount;

  @SerializedName("vote_kind")
  @Expose
  private Integer mVoteKind;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    mId = id;
  }

  public Integer getJudgeId() {
    return mJudgeId;
  }

  public void setJudgeId(Integer judgeId) {
    mJudgeId = judgeId;
  }

  public Integer getLawyerId() {
    return mLawyerId;
  }

  public void setLawyerId(Integer lawyerId) {
    mLawyerId = lawyerId;
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

  public String getUpdatedAt() {
    return mUpdatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    mUpdatedAt = updatedAt;
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

  public Integer getVoteKind() {
    return mVoteKind;
  }

  public void setVoteKind(Integer voteKind) {
    mVoteKind = voteKind;
  }
}
