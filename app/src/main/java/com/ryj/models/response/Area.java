package com.ryj.models.response;

/** Created by andrey on 9/18/17. */
public class Area {
  private int mId;
  private String mName;

  public Area(int id, String name) {
    this.mId = id;
    this.mName = name;
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
}
