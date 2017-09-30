package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrey on 8/17/17.
 */

public class Courts {
  @SerializedName("objects")
  @Expose
  private List<Court> mObjects;
  @SerializedName("previous_page")
  @Expose
  private Integer mPreviousPage;
  @SerializedName("current_page")
  @Expose
  private Integer mCurrentPage;
  @SerializedName("next_page")
  @Expose
  private Integer mNextPage;

  public List<Court> getObjects() {
    return mObjects;
  }

  public void setObjects(List<Court> objects) {
    this.mObjects = objects;
  }

  public Integer getPreviousPage() {
    return mPreviousPage;
  }

  public void setPreviousPage(Integer previousPage) {
    this.mPreviousPage = previousPage;
  }

  public Integer getCurrentPage() {
    return mCurrentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.mCurrentPage = currentPage;
  }

  public Integer getNextPage() {
    return mNextPage;
  }

  public void setNextPage(Integer nextPage) {
    this.mNextPage = nextPage;
  }
}
