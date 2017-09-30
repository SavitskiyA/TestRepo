package com.ryj.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ryj.listeners.Switchable;

/**
 * Created by andrey on 9/30/17.
 */

public class SwitchActivity extends BaseActivity implements Switchable {
  @Override
  public void switchTab(int position, boolean isSelected) {

  }

  @Override
  public void setToolBarTitle(String title) {

  }

  @Override
  public void setToolbarVisibility(int visible) {

  }

  @Override
  public void setOptionsMenuVisibility(boolean isVisible) {

  }

  @Override
  public void setCurrentFragmentTag(String tag) {

  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return null;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return null;
  }
}
