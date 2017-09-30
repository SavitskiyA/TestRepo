package com.ryj.storage.prefs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.ryj.Constants;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.auth.TutorialActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.models.enums.UserType;

public class PrefsImpl implements Prefs {

  public static final String KEY_IS_TUTORIAL_FIRST_LAUNCH =
      Constants.PACKAGE_NAME + ".IS_FIRST_LAUNCH";
  public static final String KEY_SESSION_TOKEN = Constants.PACKAGE_NAME + ".SESSION_TOKEN";
  public static final String KEY_USERTYPE = Constants.PACKAGE_NAME + ".USERTYPE";

  private SharedPreferences mSharedPreferences;

  public PrefsImpl(SharedPreferences sharedPreferences) {
    mSharedPreferences = sharedPreferences;
  }

  @Override
  public boolean getIsFirstTutorialLaunch() {
    return mSharedPreferences.getBoolean(KEY_IS_TUTORIAL_FIRST_LAUNCH, true);
  }

  @Override
  public void setIsFirstTutorialLaunch(boolean isFirstLaunch) {
    saveBoolean(KEY_IS_TUTORIAL_FIRST_LAUNCH, isFirstLaunch);
  }

  @Override
  public void clear() {
    mSharedPreferences.edit().clear().apply();
  }

  private void saveString(String key, @Nullable String data) {
    mSharedPreferences.edit().putString(key, data).apply();
  }

  private void saveBoolean(String key, boolean data) {
    mSharedPreferences.edit().putBoolean(key, data).apply();
  }

  private void saveLong(String key, long data) {
    mSharedPreferences.edit().putLong(key, data).apply();
  }

  private void saveInt(String key, int data) {
    mSharedPreferences.edit().putInt(key, data).apply();
  }

  private void saveFloat(String key, float value) {
    mSharedPreferences.edit().putFloat(key, value).apply();
  }

  private void saveDouble(String key, double value) {
    mSharedPreferences.edit().putLong(key, Double.doubleToLongBits(value)).apply();
  }

  @Nullable
  @Override
  public String getSessionToken() {
    return mSharedPreferences.getString(KEY_SESSION_TOKEN, null);
  }

  @Override
  public void setSessionToken(@Nullable String token) {
    saveString(KEY_SESSION_TOKEN, token);
  }

  @Nullable
  @Override
  public UserType getUserType() {
    int ordinal = mSharedPreferences.getInt(KEY_USERTYPE, -1);
    return ordinal < 0 ? null : UserType.values()[ordinal];
  }

  @Override
  public void setUserType(@Nullable UserType userType) {
    mSharedPreferences.edit().putInt(KEY_USERTYPE, userType.ordinal()).apply();
  }
}
