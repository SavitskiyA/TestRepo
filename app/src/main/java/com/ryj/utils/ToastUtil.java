/**
 * Copyright (c) 2014 CoderKiss
 * <p>
 * CoderKiss[AT]gmail.com
 */

package com.ryj.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ryj.App;

public class ToastUtil {

  public static Toast mToast;

  public static void show(int resId, boolean longTime) {
    initToast(longTime);
    if (resId < 0 || mToast == null) {
      return;
    }

    mToast.setText(resId);
    mToast.show();
  }

  public static void show(String message, boolean longTime) {
    initToast(longTime);
    if (TextUtils.isEmpty(message) || mToast == null) {
      return;
    }

    mToast.setText(message);
    mToast.show();
  }

  public static void dismiss() {
    if (mToast == null) {
      return;
    }
    mToast.cancel();
  }

  @SuppressLint("ShowToast")
  private static void initToast(boolean longTime) {
    if (mToast != null) {
      return;
    }
    Context context = App.get();
    if (context == null) {
      return;
    }
    mToast = Toast.makeText(context, "", longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
  }
}
