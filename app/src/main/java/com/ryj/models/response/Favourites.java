package com.ryj.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrey on 12/1/17.
 */

public class Favourites {
  @SerializedName("favorites")
  @Expose
  private List<Judge> mFavorites;

  public List<Judge> getFavorites() {
    return mFavorites;
  }

  public void setFavorites(List<Judge> favorites) {
    this.mFavorites = favorites;
  }
}
