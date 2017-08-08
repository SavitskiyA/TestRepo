package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/13/17.
 */

public enum UserType {
  @SerializedName("сitizen")CITIZEN {
    @Override
    public String toString() {
      return "citizen";
    }
  },
  @SerializedName("lawyer")LAWYER {
    @Override
    public String toString() {
      return "lawyer";
    }
  },
  @SerializedName("judge")JUDGE {
    @Override
    public String toString() {
      return "judge";
    }
  }
}
