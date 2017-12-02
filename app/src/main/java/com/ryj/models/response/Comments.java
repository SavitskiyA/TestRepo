package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/** Created by andrey on 11/2/17. */
public class Comments {
  @SerializedName("objects")
  @Expose
  private List<Comment> mComments;

  @SerializedName("total_entries")
  @Expose
  private Integer mTotalEntries;

  @SerializedName("previous_page")
  @Expose
  private Integer mPreviousPage;

  @SerializedName("current_page")
  @Expose
  private Integer currentPage;

  @SerializedName("next_page")
  @Expose
  private Integer mNextPage;

  public List<Comment> getComments() {
    return mComments;
  }

  public void setComments(List<Comment> comments) {
    mComments = comments;
  }

  public Integer getTotalEntries() {
    return mTotalEntries;
  }

  public void setTotalEntries(Integer totalEntries) {
    mTotalEntries = totalEntries;
  }

  public Object getPreviousPage() {
    return mPreviousPage;
  }

  public void setPreviousPage(Integer previousPage) {
    mPreviousPage = previousPage;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    currentPage = currentPage;
  }

  public Object getNextPage() {
    return mNextPage;
  }

  public void setNextPage(Integer nextPage) {
    mNextPage = nextPage;
  }
}
