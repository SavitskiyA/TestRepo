package com.ryj.models.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 7/13/17.
 */

public enum UserType {
  @SerializedName("Citizen")CITIZEN,
  @SerializedName("Lawyer")LAWYER,
  @SerializedName("Judge")JUDGE,
}
