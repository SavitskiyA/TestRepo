package com.ryj.storage.prefs;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.ryj.Constants;
import com.ryj.models.enums.UserType;

public class PrefsImpl implements Prefs {

  private static final String KEY_IS_TUTORIAL_FIRST_LAUNCH =
      Constants.PACKAGE_NAME + ".IS_FIRST_LAUNCH";
  private static final String KEY_SESSION_TOKEN = Constants.PACKAGE_NAME + ".SESSION_TOKEN";
  private static final String KEY_USERTYPE = Constants.PACKAGE_NAME + ".USERTYPE";
  private static final String KEY_USER_ID = Constants.PACKAGE_NAME + ".USER_ID";
  private static final String KEY_MESSAGE_TO_EMAIL = Constants.PACKAGE_NAME + ".MESSAGE_TO_EMAIL";
  private static final String KEY_USER_CATEGORIES = Constants.PACKAGE_NAME + ".USER_CATEGORIES";
  private static final String KEY_USER_EMAIL = Constants.PACKAGE_NAME + ".USER_EMAIL";
  private static final String KEY_USER_PHONE = Constants.PACKAGE_NAME + ".USER_PHONE";
  private static final String KEY_USER_PHOTO = Constants.PACKAGE_NAME + ".USER_PHOTO";

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

  @Nullable
  @Override
  public Integer getCurrentUserId() {
    return mSharedPreferences.getInt(KEY_USER_ID, 0);
  }

  @Override
  public void setCurrentUserId(Integer id) {
    saveInt(KEY_USER_ID, id == null ? 0 : id);
  }

  @Override
  public boolean getIsMessageToEmail() {
    return mSharedPreferences.getBoolean(KEY_MESSAGE_TO_EMAIL, false);
  }

  @Override
  public void setIsMessageToEmail(boolean isActive) {
    saveBoolean(KEY_MESSAGE_TO_EMAIL, isActive);
  }

  @Nullable
  @Override
  public String getCategories() {
    return mSharedPreferences.getString(KEY_USER_CATEGORIES, null);
  }

  public void setCategoriest(String categories) {
    saveString(KEY_USER_CATEGORIES, categories);
  }

  @Nullable
  @Override
  public String getEmail() {
    return mSharedPreferences.getString(KEY_USER_EMAIL, null);
  }

  public void setEmail(String email) {
    saveString(KEY_USER_EMAIL, email);
  }

  @Nullable
  @Override
  public String getPhone() {
    return mSharedPreferences.getString(KEY_USER_PHONE, null);
  }

  public void setPhone(String phone) {
    saveString(KEY_USER_PHONE, phone);
  }

  @Nullable
  @Override
  public String getPhoto() {
    return mSharedPreferences.getString(KEY_USER_PHOTO, null);
  }

  @Override
  public void setPhoto(String photo) {
    saveString(KEY_USER_PHOTO, photo);
  }
}
