package com.ryj.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.ryj.interfaces.IBottomBarsTabs.TAB_ANALYTICS;
import static com.ryj.interfaces.IBottomBarsTabs.TAB_JUDGE;
import static com.ryj.interfaces.IBottomBarsTabs.TAB_NEWS;
import static com.ryj.interfaces.IBottomBarsTabs.TAB_PROFILE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({TAB_JUDGE, TAB_ANALYTICS, TAB_NEWS, TAB_PROFILE})
public @interface IBottomBarsTabs {
  public static final int TAB_JUDGE = 0;
  public static final int TAB_ANALYTICS = 1;
  public static final int TAB_NEWS = 2;
  public static final int TAB_PROFILE = 3;
}
