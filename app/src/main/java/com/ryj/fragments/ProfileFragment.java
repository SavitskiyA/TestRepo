package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;

import butterknife.BindString;
import butterknife.ButterKnife;

/** Created by andrey on 8/24/17. */
public class ProfileFragment extends BaseFragment {
  public static final String TAG = "ProfileFragment";

  @BindString(R.string.text_profile)
  String mTitle;

  public static ProfileFragment newInstance() {
    return new ProfileFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (this.getActivity() != null) {
      setActivityToolBarTitle(mTitle);
      switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
      setActivityOptionsMenuVisibility(false);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
    setActivityOptionsMenuVisibility(true);
  }
}
