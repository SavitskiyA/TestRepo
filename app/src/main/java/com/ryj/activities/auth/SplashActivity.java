package com.ryj.activities.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ryj.Constants.SPLASH_TEXT_APPEARANCE_DURATION_MS;

public class SplashActivity extends BaseActivity {
  private static final String TAG = "SplashActivity";
  @Inject Prefs mPrefs;

  @BindView(R.id.image)
  ImageView mLogo;

  private Handler mHandler;
  private Runnable runnable = () -> redirect();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    getComponent().inject(this);
    hideStatusBar();
    mHandler = new Handler();
    mLogo.animate().alpha(1f).setDuration(SPLASH_TEXT_APPEARANCE_DURATION_MS);
    mHandler.postDelayed(runnable, SPLASH_TEXT_APPEARANCE_DURATION_MS);
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

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mHandler.removeCallbacks(runnable);
  }

  private void redirect() {
    if (mPrefs.getSessionToken() != null) {
      BottomBarContainerActivity.start(this);
    } else if (mPrefs.getIsFirstTutorialLaunch()) {
      TutorialActivity.start(this);
    } else {
      SignInActivity.start(this);
    }
    finish();
  }
}
