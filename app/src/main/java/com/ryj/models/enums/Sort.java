package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/** Created by andrey on 9/13/17. */
public enum Sort {
  @SerializedName("last_name")
  LAST_NAME {
    @Override
    public String toString() {
      return "last_name";
    }
  },
  @SerializedName("created_at")
  CREATED_AT {
    @Override
    public String toString() {
      return "created_at";
    }
  },
  @SerializedName("comments_count")
  COMMENTS_COUNT {
    @Override
    public String toString() {
      return "comments_count";
    }
  },
  @SerializedName("avg_rating")
  AVG_RATING {
    @Override
    public String toString() {
      return "avg_rating";
    }
  }
}
