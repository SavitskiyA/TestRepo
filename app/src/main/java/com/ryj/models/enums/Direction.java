package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 9/13/17.
 */

public enum Direction {
  @SerializedName("asc")ASC {
    @Override
    public String toString() {
      return "asc";
    }
  },
  @SerializedName("desc")DESC {
    @Override
    public String toString() {
      return "desc";
    }
  };
}
