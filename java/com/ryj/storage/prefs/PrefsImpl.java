package com.ryj.storage.prefs;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.ryj.Constants;

public class PrefsImpl implements Prefs {

  public static final String KEY_IS_TUTORIAL_FIRST_LAUNCH = Constants.PACKAGE_NAME + ".IS_FIRST_LAUNCH";

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
}
