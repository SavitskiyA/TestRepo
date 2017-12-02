package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.models.events.SignOutEvent;
import com.ryj.rx.RxBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 8/24/17. */
public class AnalyticsFragment extends BaseFragment {
  public static final String TAG = "AnalyticsFragment";

  @BindView(R.id.logout)
  Button mLogOut;

  public static AnalyticsFragment newInstance() {
    return new AnalyticsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return bindView(inflater.inflate(R.layout.fragment_analytics, container, false));
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
  }

  @OnClick(R.id.logout)
  public void onClick() {
    RxBus.postEvent(new SignOutEvent());
  }
}
