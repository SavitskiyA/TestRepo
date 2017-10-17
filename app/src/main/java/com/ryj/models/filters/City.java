package com.ryj.models.filters;

/** Created by andrey on 10/17/17. */
public class City {
  private Integer mId;
  private String mName;
  private Integer mRegionId;

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

  public Integer getRegionId() {
    return mRegionId;
  }

  public void setRegionId(Integer regionId) {
    this.mRegionId = regionId;
  }
}
