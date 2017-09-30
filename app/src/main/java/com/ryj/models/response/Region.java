package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/** Created by andrey on 9/8/17. */
public class Region {
  @SerializedName("id")
  @Expose
  private Integer mId;

  @SerializedName("name")
  @Expose
  private String mName;

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
}
