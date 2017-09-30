package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 9/5/17.
 */
public class JudgesChooseSectionFragment extends BaseFragment {
  public static final String TAG = "JudgesChooseSectionFragment";

  @BindView(R.id.judges)
  View mJudges;

  @BindView(R.id.most_discussed)
  View mMostDiscussed;

  @BindView(R.id.courts)
  View mCourts;

  public static JudgesChooseSectionFragment newInstance() {
    return new JudgesChooseSectionFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_judges_choose_section, container, false);
    ButterKnife.bind(this, view);
    setActivityToolbarVisibility(View.GONE);
    return view;
  }

  @OnClick({R.id.judges, R.id.most_discussed, R.id.courts})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.judges:
        replaceFragment(
                JudgesFragment.newInstance(JudgesFragment.PARENT_JUDGES),
                R.id.container,
                true,
                false,
                JudgesFragment.TAG);
        return;
      case R.id.most_discussed:
        replaceFragment(
                JudgesFragment.newInstance(JudgesFragment.PARENT_MOST_DISCUSSED),
                R.id.container,
                true,
                false,
                JudgesFragment.TAG);
        return;
      case R.id.courts:
        replaceFragment(
                CourtsFragment.newInstance(),
                R.id.container,
                true,
                false,
                CourtsFragment.TAG);
        return;
    }
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

  @Override
  public void onStop() {
    super.onStop();
    setActivityToolbarVisibility(View.VISIBLE);
  }
}
