package com.ryj.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by andrey on 8/8/17.
 */

public class ScreenUtils {

  public static int dpToPx(int dp, Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int pxToDp(int px, Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }
}
