package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/** Created by andrey on 9/29/17. */
public enum Courts {
  @SerializedName("supreme")
  SUPREME {
    @Override
    public String toString() {
      return "supreme";
    }
  },
  @SerializedName("highest_civil_criminal")
  HIGHEST_CIVIL_CRIMINAL {
    @Override
    public String toString() {
      return "highest_civil_criminal";
    }
  },
  @SerializedName("highest_economic")
  HIGHEST_ECONOMIC {
    @Override
    public String toString() {
      return "highest_economic";
    }
  },
  @SerializedName("highest_administrative")
  HIGHEST_ADMINISTRATIVE {
    @Override
    public String toString() {
      return "highest_administrative";
    }
  },
  @SerializedName("appellate")
  APPELLATE {
    @Override
    public String toString() {
      return "appellate";
    }
  },
  @SerializedName("appellate_economic")
  APPELLATE_ECONOMIC {
    @Override
    public String toString() {
      return "appellate_economic";
    }
  },
  @SerializedName("appellate_admin")
  APPELLATE_ADMIN {
    @Override
    public String toString() {
      return "appellate_admin";
    }
  },
  @SerializedName("local")
  LOCAL {
    @Override
    public String toString() {
      return "local";
    }
  },
  @SerializedName("local_economic")
  LOCAL_ECONOMIC {
    @Override
    public String toString() {
      return "local_economic";
    }
  },
  @SerializedName("local_administrative")
  LOCAL_ADMINISTRATIVE {
    @Override
    public String toString() {
      return "local_administrative";
    }
  }
}
