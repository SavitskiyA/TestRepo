package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/** Created by andrey on 9/18/17. */
public class City {
  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("name")
  @Expose
  private String mName;

  @SerializedName("region_id")
  @Expose
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
