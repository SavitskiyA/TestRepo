package com.ryj.storage.prefs;

import android.support.annotation.Nullable;

import com.ryj.models.enums.UserType;

public interface Prefs {
  void clear();

  boolean getIsFirstTutorialLaunch();

  void setIsFirstTutorialLaunch(boolean isFirstLaunch);

  @Nullable
  String getSessionToken();

  void setSessionToken(@Nullable String token);

  @Nullable
  UserType getUserType();

  void setUserType(@Nullable UserType userType);

  @Nullable
  Integer getCurrentUserId();

  void setCurrentUserId(Integer id);

  @Nullable
  boolean getIsMessageToEmail();

  void setIsMessageToEmail(boolean isActive);
}
