package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;

import butterknife.BindString;

/** Created by andrey on 8/24/17. */
public class NewsFragment extends BaseFragment {
  public static final String TAG = "NewsFragment";

  @BindString(R.string.text_news)
  String mTitle;

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = bindView(inflater.inflate(R.layout.fragment_news, container, false));
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
  }
}
