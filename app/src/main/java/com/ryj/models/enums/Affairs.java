package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/** Created by andrey on 7/27/17. */
public enum Affairs {
  @SerializedName("admin_violation")
  ADMIN_VIOLATION {
    @Override
    public String toString() {
      return "admin_violation";
    }
  },
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
  @SerializedName("urgent_lawsuits")
  URGENT_LAWSUITS {
    @Override
    public String toString() {
      return "urgent_lawsuits";
    }
  }
}
