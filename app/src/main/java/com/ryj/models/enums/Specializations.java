package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/** Created by andrey on 7/28/17. */
public enum Specializations {
  @SerializedName("administrative")
  ADMINISTRATIVE {
    @Override
    public String toString() {
      return "administrative";
    }
  },
  @SerializedName("civil")
  CIVIL {
    @Override
    public String toString() {
      return "civil";
    }
  },
  @SerializedName("criminal")
  CRIMINAL {
    @Override
    public String toString() {
      return "criminal";
    }
  },
  @SerializedName("economic")
  ECONOMIC {
    @Override
    public String toString() {
      return "economic";
    }
  },
  @SerializedName("legal")
  LEGAL {
    @Override
    public String toString() {
      return "legal";
    }
  }
}
