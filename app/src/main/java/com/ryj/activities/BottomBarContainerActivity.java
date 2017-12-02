package com.ryj.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.ryj.R;
import com.ryj.fragments.AnalyticsFragment;
import com.ryj.fragments.CommentsFragment;
import com.ryj.fragments.CourtFragment;
import com.ryj.fragments.CourtsFragment;
import com.ryj.fragments.FavouritesFragment;
import com.ryj.fragments.JudgeChooseSectionFragment;
import com.ryj.fragments.JudgeFragment;
import com.ryj.fragments.JudgesFragment;
import com.ryj.fragments.LawyerChooseSectionFragment;
import com.ryj.fragments.LawyerFragment;
import com.ryj.fragments.NewsFragment;
import com.ryj.fragments.SettingsFragment;
import com.ryj.interfaces.IBottomBarsTabs;
import com.ryj.interfaces.Switchable;
import com.ryj.models.enums.UserType;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/** Created by andrey on 8/24/17. */
public class BottomBarContainerActivity extends SwitchActivity implements Switchable {
  public static final Map<String, Integer> mBottomBarTabsMap = new HashMap<>();

  @BindView(R.id.bottomBar)
  BottomBar mBorromBar;

  @BindView(R.id.container)
  FrameLayout mContainer;

  private String mCurrentFragmentTag;
  private boolean mIsClick;

  public static void start(Context context) {
    Intent i = new Intent(context, BottomBarContainerActivity.class);
    context.startActivity(i);
  }

  private static void fillBottomBarTabsMap() {
    mBottomBarTabsMap.put(JudgeChooseSectionFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(LawyerChooseSectionFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(JudgesFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(AnalyticsFragment.TAG, IBottomBarsTabs.TAB_ANALYTICS);
    mBottomBarTabsMap.put(NewsFragment.TAG, IBottomBarsTabs.TAB_NEWS);
    mBottomBarTabsMap.put(SettingsFragment.TAG, IBottomBarsTabs.TAB_PROFILE);
    mBottomBarTabsMap.put(CourtsFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(CourtFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(JudgeFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(CommentsFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(LawyerFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
    mBottomBarTabsMap.put(FavouritesFragment.TAG, IBottomBarsTabs.TAB_JUDGE);
  }

  public static int getTabPosition(String tag) {
    return mBottomBarTabsMap.get(tag);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottombar_container);
    getComponent().inject(this);
    setSoftInputMode();
    setBottomBarSwitcher();
    fillBottomBarTabsMap();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void setBottomBarSwitcher() {
    mBorromBar.setOnTabSelectListener(
        tabId -> {
          if (!mIsClick) {
            switch (tabId) {
              case R.id.tab_judges:
                if (mPrefs.getUserType() == UserType.LAWYER) {
                  toJudgesAsLawyer();
                  return;
                } else {
                  toJudges();
                  return;
                }
              case R.id.tab_analytics:
                toAnalytics();
                return;
              case R.id.tab_news:
                toNews();
                return;
              case R.id.tab_profile:
                toProfile();
                return;
            }
          }
        });
  }

  @Override
  public void setCurrentFragmentTag(String tag) {
    mCurrentFragmentTag = tag;
  }

  @Override
  public void onBackPressed() {
    if (getFragmentsBackStackSize() == 1) {
      finish();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void switchTab(int position, boolean isSelected) {
    mIsClick = true;
    mBorromBar.getTabAtPosition(position).callOnClick();
    mIsClick = false;
  }

  private void toProfile() {
    switch (mPrefs.getUserType()) {
      case JUDGE:
        replaceFragment(
                JudgeFragment.newInstance(mPrefs.getCurrentUserId()),
                R.id.container,
                true,
                false,
                JudgeFragment.TAG);
        return;
      case LAWYER:
        replaceFragment(
                LawyerFragment.newInstance(mPrefs.getCurrentUserId()),
                R.id.container,
                true,
                false,
                LawyerFragment.TAG);
        return;
      case CITIZEN:
        replaceFragment(
                SettingsFragment.newInstance(), R.id.container, true, false, SettingsFragment.TAG);
        return;
    }
  }

  private void toJudgesAsLawyer() {
    replaceFragment(
        LawyerChooseSectionFragment.newInstance(),
        R.id.container,
        true,
        false,
        LawyerChooseSectionFragment.TAG);
  }

  private void toJudges() {
    replaceFragment(
        JudgeChooseSectionFragment.newInstance(),
        R.id.container,
        true,
        false,
        JudgeChooseSectionFragment.TAG);
  }

  private void toAnalytics() {
    replaceFragment(
        AnalyticsFragment.newInstance(), R.id.container, true, false, AnalyticsFragment.TAG);
  }

  private void toNews() {
    replaceFragment(NewsFragment.newInstance(), R.id.container, true, false, NewsFragment.TAG);
  }

}
