package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 11/2/17.
 */

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
  private Integer mLikesCount;
  @SerializedName("dislikes_count")
  @Expose
  private Integer mDislikesCount;
  @SerializedName("author")
  @Expose
  private Author mAuthor;
  @SerializedName("vote_kind")
  @Expose
  private Object mVoteKind;

  public Integer getId() {
    return mId;
  }

  public void setId(Integer id) {
    this.mId = id;
  }

  public String getBody() {
    return mBody;
  }

  public void setBody(String body) {
    this.mBody = body;
  }

  public String getCreatedAt() {
    return mCreatedAt;
  }

  public void setCreatedAt(String createdAt) {
    this.mCreatedAt = createdAt;
  }

  public Integer getLikesCount() {
    return mLikesCount;
  }

  public void setLikesCount(Integer likesCount) {
    this.mLikesCount = likesCount;
  }

  public Integer getDislikesCount() {
    return mDislikesCount;
  }

  public void setDislikesCount(Integer dislikesCount) {
    this.mDislikesCount = dislikesCount;
  }

  public Author getAuthor() {
    return mAuthor;
  }

  public void setAuthor(Author author) {
    this.mAuthor = author;
  }

  public java.lang.Object getVoteKind() {
    return mVoteKind;
  }

  public void setVoteKind(java.lang.Object voteKind) {
    this.mVoteKind = voteKind;
  }
}
