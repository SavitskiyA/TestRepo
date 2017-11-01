package com.ryj.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.models.enums.UserType;
import com.ryj.models.events.SignOutEvent;
import com.ryj.rx.RxBus;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 10/27/17.
 */

public class SettingsActivity extends BaseActivity {
  @Inject
  Prefs mPrefs;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.switcher)
  Switch mSwitch;

  @BindView(R.id.edit)
  TextView mEdit;

  @BindView(R.id.change_password)
  TextView mChangePassword;

  @BindView(R.id.contact_us)
  TextView mContactUs;

  @BindView(R.id.about)
  TextView mAbout;

  @BindView(R.id.logout)
  TextView mLogOut;

  private boolean mIsChecked;

  public static void start(Context context) {
    Intent i = new Intent(context, SettingsActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mSwitch.setChecked(mPrefs.getIsMessageToEmail());
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolBar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @OnClick({R.id.switcher, R.id.edit, R.id.logout})
  void onClick(View v) {
    switch (v.getId()) {
      case R.id.switcher:
        toSwitch();
        return;
      case R.id.edit:
        if (mPrefs.getUserType() == UserType.JUDGE) {
          EditProfileJudgeActivity.start(this);
          return;
        } else if (mPrefs.getUserType() == UserType.LAWYER) {
          EditProfileLawyerActivity.start(this);
          return;
        } else {
          EditProfileJudgeActivity.start(this);
          return;
        }
      case R.id.logout:
        RxBus.postEvent(new SignOutEvent());
        return;
    }
  }

  private void toSwitch() {
    if (mIsChecked) {
      mSwitch.setChecked(true);
      mPrefs.setIsMessageToEmail(true);
    } else {
      mSwitch.setChecked(false);
      mPrefs.setIsMessageToEmail(false);
    }
    mIsChecked = !mIsChecked;
  }

}
