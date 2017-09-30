package com.ryj.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.ryj.R;
import com.ryj.activities.filters.FiltersActivity;
import com.ryj.fragments.AnalyticsFragment;
import com.ryj.fragments.JudgesChooseSectionFragment;
import com.ryj.fragments.JudgesFragment;
import com.ryj.fragments.NewsFragment;
import com.ryj.fragments.ProfileFragment;
import com.ryj.listeners.Switchable;
import com.ryj.models.JudgesQuery;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by andrey on 8/24/17. */
public class BottomBarContainerActivity extends BaseActivity implements Switchable {
  public static final Map<String, Integer> mBottomBarTabsMap = new HashMap<>();
  @Inject JudgesQuery mJudgesQuery;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.bottomBar)
  BottomBar mBorromBar;

  @BindView(R.id.container)
  FrameLayout mContainer;

  public static void start(Context context) {
    Intent i = new Intent(context, BottomBarContainerActivity.class);
    context.startActivity(i);
  }

  private static void fillBottomBarTabsMap() {
    mBottomBarTabsMap.put(JudgesChooseSectionFragment.TAG, 0);
    mBottomBarTabsMap.put(JudgesFragment.TAG, 0);
    mBottomBarTabsMap.put(AnalyticsFragment.TAG, 1);
    mBottomBarTabsMap.put(NewsFragment.TAG, 2);
    mBottomBarTabsMap.put(ProfileFragment.TAG, 3);
  }

  public static int getTabPosition(String tag) {
    return mBottomBarTabsMap.get(tag);
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolbar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottombar_container);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    setBottomBarSwitcher();
    fillBottomBarTabsMap();
  }

  private void setBottomBarSwitcher() {
    mBorromBar.setOnTabSelectListener(
        tabId -> {
          switch (tabId) {
            case R.id.tab_judges:
              replaceFragment(
                  JudgesChooseSectionFragment.newInstance(),
                  R.id.container,
                  true,
                  false,
                  JudgesChooseSectionFragment.TAG);
              return;
            case R.id.tab_analytics:
              replaceFragment(
                  AnalyticsFragment.newInstance(),
                  R.id.container,
                  true,
                  false,
                  AnalyticsFragment.TAG);
              return;
            case R.id.tab_news:
              replaceFragment(
                  NewsFragment.newInstance(), R.id.container, true, false, NewsFragment.TAG);
              return;
            case R.id.tab_profile:
              replaceFragment(
                  ProfileFragment.newInstance(), R.id.container, true, false, ProfileFragment.TAG);
              return;
          }
        });
  }

  @Override
  public void setToolbarVisibility(int visibility) {
    mToolbar.setVisibility(visibility);
  }

  @Override
  public void setOptionsMenuVisibility(boolean isVisible) {
    mToolbar.getMenu().findItem(R.id.action_filters).setVisible(isVisible);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_filters, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.action_filters:
        FiltersActivity.start(this);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
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
    mBorromBar.getTabAtPosition(position).setSelected(isSelected);
  }

  @Override
  public void setToolBarTitle(String title) {
    mTitle.setText(title);
  }
}
