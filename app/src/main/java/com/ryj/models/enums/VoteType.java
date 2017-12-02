package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/** Created by andrey on 11/23/17. */
public enum VoteType {
  @SerializedName("like")
  LIKE {
    @Override
    public String toString() {
      return "like";
    }
  },
  @SerializedName("dislike")
  DISLIKE {
    @Override
    public String toString() {
      return "dislike";
    }
  };
}
