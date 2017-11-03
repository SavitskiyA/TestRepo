package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrey on 11/2/17.
 */

public class Comments {
  @SerializedName("objects")
  @Expose
  private List<Comment> mComments;
  @SerializedName("total_entries")
  @Expose
  private Integer totalEntries;
  @SerializedName("previous_page")
  @Expose
  private Object previousPage;
  @SerializedName("current_page")
  @Expose
  private Integer currentPage;
  @SerializedName("next_page")
  @Expose
  private Object nextPage;

  public List<Comment> getComments() {
    return mComments;
  }

  public void setComments(List<Comment> comments) {
    this.mComments = comments;
  }

  public Integer getTotalEntries() {
    return totalEntries;
  }

  public void setTotalEntries(Integer totalEntries) {
    this.totalEntries = totalEntries;
  }

  public Object getPreviousPage() {
    return previousPage;
  }

  public void setPreviousPage(Object previousPage) {
    this.previousPage = previousPage;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Object getNextPage() {
    return nextPage;
  }

  public void setNextPage(Object nextPage) {
    this.nextPage = nextPage;
  }

}
