package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 9/5/17. */
public class LawyerChooseSectionFragment extends BaseFragment {
  public static final String TAG = "LawyerChooseSectionFragment";
  @Inject Prefs mPrefs;

  @BindView(R.id.judges)
  View mJudges;

  @BindView(R.id.most_discussed)
  View mMostDiscussed;

  @BindView(R.id.courts)
  View mCourts;

  @BindView(R.id.favourites)
  View mFavourites;

  public static LawyerChooseSectionFragment newInstance() {
    return new LawyerChooseSectionFragment();
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
      View view =
          bindView(inflater.inflate(R.layout.fragment_lawyer_choose_section, container, false));
      return view;
    }

  @OnClick({R.id.judges, R.id.most_discussed, R.id.courts, R.id.favourites})
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
            CourtsFragment.newInstance(), R.id.container, true, false, CourtsFragment.TAG);
        return;
      case R.id.favourites:
        replaceFragment(
            FavouritesFragment.newInstance(), R.id.container, true, false, CourtsFragment.TAG);
        return;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
  }
}
