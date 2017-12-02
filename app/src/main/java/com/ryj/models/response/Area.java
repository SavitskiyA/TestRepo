package com.ryj.models.response;

import com.ryj.utils.StringUtils;

/** Created by andrey on 9/18/17. */
public class Area {
  private int mId;
  private String mName;

  public Area(int id, String name) {
    mId = id;
    mName = name;
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public String getName() {
    return mName == null ? StringUtils.EMPTY_STRING : mName;
  }

  public void setName(String name) {
    mName = name;
  }
}
