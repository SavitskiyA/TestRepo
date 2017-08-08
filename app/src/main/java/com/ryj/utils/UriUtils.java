package com.ryj.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.ryj.Constants;

/**
 * Created by rkom on 31.03.16.
 */
public class UriUtils {
    public static final String PATH_TO_DRAWABLE_RES = "res:/";
    public static final String PATH_TO_RAW_RES = "android.resource://" + Constants.PACKAGE_NAME + "/";
    public static final String EXTENSION_JPG = ".jpg";

    @NonNull
    public static Uri getDrawableResourceUri(int resId) {
        return Uri.parse(PATH_TO_DRAWABLE_RES + resId);
    }

    @NonNull
    public static Uri getRawResourceUri(int resId) {
        return Uri.parse(PATH_TO_RAW_RES + resId);
    }
}
