package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 9/30/17.
 */
public class Court {

  @SerializedName("id")
  @Expose
  private Integer mId;
  @SerializedName("name")
  @Expose
  private String mName;
  @SerializedName("kind")
  @Expose
  private String mKind;

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

  public String getKind() {
    return mKind;
  }

  public void setKind(String kind) {
    this.mKind = kind;
  }
}
