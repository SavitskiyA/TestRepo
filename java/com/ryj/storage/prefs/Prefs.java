package com.ryj.storage.prefs;

public interface Prefs {
  void clear();

  boolean getIsFirstTutorialLaunch();

  void setIsFirstTutorialLaunch(boolean isFirstLaunch);
}
