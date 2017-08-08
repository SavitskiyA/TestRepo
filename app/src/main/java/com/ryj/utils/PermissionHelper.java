package com.ryj.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 8/5/17.
 */

public class PermissionHelper {
  public static void requestPermissions(Activity activity, int requestCode, PermissionsChecker permissionsChecker, String... permissions) {
    List<String> requestedPermissions = new ArrayList<>();
    for (String p : permissions) {
      if (ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
        requestedPermissions.add(p);
      }
    }
    if (requestedPermissions.size() == 0) {
      permissionsChecker.allPermissionsGranted();
      return;
    }

    ActivityCompat.requestPermissions(activity, requestedPermissions.toArray(new String[requestedPermissions.size()]), requestCode);
  }

  public static boolean allPermissionsGranted(int[] grantResults) {
    boolean allPermissionsGranted = true;
    for (int i = 0; i < grantResults.length; i++) {
      if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
        allPermissionsGranted = false;
        break;
      }
    }
    return allPermissionsGranted;
  }

  public interface PermissionsChecker {
    void allPermissionsGranted();
  }
}
