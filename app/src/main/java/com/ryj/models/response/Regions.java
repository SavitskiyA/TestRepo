package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/** Created by andrey on 9/8/17. */
public class Regions{
  @SerializedName("objects")
  @Expose
  private List<Region> mRegions;

  @SerializedName("previous_page")
  @Expose
  private Integer mPreviousPage;

  @SerializedName("current_page")
  @Expose
  private Integer mCurrentPage;

  @SerializedName("next_page")
  @Expose
  private Integer mNextPage;

  public List<Region> getRegions() {
    return mRegions;
  }

  public void setRegions(List<Region> regions) {
    this.mRegions = regions;
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

  public Object getNextPage() {
    return mNextPage;
  }

  public void setNextPage(Integer nextPage) {
    this.mNextPage = nextPage;
  }
}
