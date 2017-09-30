package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.models.events.SignOutEvent;
import com.ryj.rx.RxBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 8/24/17.
 */
public class AnalyticsFragment extends BaseFragment {
  public static final String TAG = "AnalyticsFragment";

  @BindString(R.string.text_analytics)
  public String mTitle;

  @BindView(R.id.logout)
  Button mLogOut;

  public static AnalyticsFragment newInstance() {
    return new AnalyticsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_analytics, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    setActivityToolBarTitle(mTitle);
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setActivityOptionsMenuVisibility(false);
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
    setActivityOptionsMenuVisibility(true);
  }

  @OnClick(R.id.logout)
  public void logOut() {
    RxBus.postEvent(new SignOutEvent());
  }
}
