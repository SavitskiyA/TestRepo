package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/28/17.
 */

public enum Specializations {
  @SerializedName("administrative_process")ADMINISTRATIVE {
    @Override
    public String toString() {
      return "administrative_process";
    }
  },
  @SerializedName("economic_process")ECONOMIC {
    @Override
    public String toString() {
      return "economic_process";
    }
  },
  @SerializedName("criminal_process")CRIMINAL {
    @Override
    public String toString() {
      return "criminal_process";
    }
  },
  @SerializedName("civil_process")CIVIL {
    @Override
    public String toString() {
      return "civil_process";
    }
  }
}
