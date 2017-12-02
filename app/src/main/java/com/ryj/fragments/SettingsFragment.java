package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.ChangePasswordActivity;
import com.ryj.activities.EditProfileCitizenActivity;
import com.ryj.activities.EditProfileJudgeActivity;
import com.ryj.activities.EditProfileLawyerActivity;
import com.ryj.activities.auth.TutorialActivity;
import com.ryj.models.enums.UserType;
import com.ryj.models.events.SignOutEvent;
import com.ryj.rx.RxBus;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/** Created by andrey on 8/24/17. */
public class SettingsFragment extends BaseFragment {
  public static final String TAG = "SettingsFragment";
  @Inject Prefs mPrefs;

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

  public static SettingsFragment newInstance() {
    return new SettingsFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = bindView(inflater.inflate(R.layout.fragment_profile, container, false));
    mIsChecked = mPrefs.getIsMessageToEmail();
    mSwitch.setChecked(mIsChecked);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
  }

  @OnClick({R.id.about, R.id.back, R.id.edit, R.id.logout, R.id.change_password})
  void onClick(View v) {
    switch (v.getId()) {
      case R.id.back:
        onBackPressed();
        break;
      case R.id.edit:
        if (mPrefs.getUserType() == UserType.JUDGE) {
          EditProfileJudgeActivity.start(this.getContext());
          return;
        } else if (mPrefs.getUserType() == UserType.LAWYER) {
          EditProfileLawyerActivity.start(this.getContext());
          return;
        } else {
          EditProfileCitizenActivity.start(this.getContext());
          break;
        }
      case R.id.logout:
        RxBus.postEvent(new SignOutEvent());
        break;

      case R.id.about:
        TutorialActivity.start(this.getContext());
        break;
      case R.id.change_password:
        ChangePasswordActivity.start(this.getContext());
        break;
    }
  }

  @OnCheckedChanged(R.id.switcher)
  void onCheckedChange() {
    mSwitch.setChecked(mIsChecked);
    mPrefs.setIsMessageToEmail(mIsChecked);
    mIsChecked = !mIsChecked;
  }
}
