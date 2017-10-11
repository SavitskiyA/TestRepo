package com.ryj.listeners;

/** Created by andrey on 9/21/17. */
public interface Switchable {
  void switchTab(int position, boolean isSelected);

  void setToolBarTitle(String title);

  void setToolbarVisibility(int visible);

  void setOptionsMenuVisibility(boolean isVisible);

  void setCurrentFragmentTag(String tag);
}
