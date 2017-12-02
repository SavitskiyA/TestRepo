package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.utils.StringUtils;

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
    mId = id;
  }

  public String getName() {
    return mName == null ? StringUtils.EMPTY_STRING : mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public Integer getRegionId() {
    return mRegionId;
  }

  public void setRegionId(Integer regionId) {
    mRegionId = regionId;
  }
}
