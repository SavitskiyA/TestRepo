package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cities {
  @SerializedName("objects")
  @Expose
  private List<City> mCities;

  @SerializedName("previous_page")
  @Expose
  private Integer mPreviousPage;

  @SerializedName("current_page")
  @Expose
  private Integer mCurrentPage;

  @SerializedName("next_page")
  @Expose
  private Integer mNextPage;

  @SerializedName("total_entries")
  @Expose
  private Integer mTotalEntries;

  public List<City> getCities() {
    return mCities;
  }

  public void setRegions(List<City> regions) {
    mCities = regions;
  }

  public Integer getPreviousPage() {
    return mPreviousPage;
  }

  public void setPreviousPage(Integer previousPage) {
    mPreviousPage = previousPage;
  }

  public Integer getCurrentPage() {
    return mCurrentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    mCurrentPage = currentPage;
  }

  public Object getNextPage() {
    return mNextPage;
  }

  public void setNextPage(Integer nextPage) {
    mNextPage = nextPage;
  }

  public Integer getTotalEntries() {
    return mTotalEntries;
  }

  public void setTotalEntries(Integer totalEntries) {
    mTotalEntries = totalEntries;
  }
}
