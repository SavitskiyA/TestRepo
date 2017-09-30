package com.ryj.activities.filters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.models.JudgesQuery;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/6/17. */
public class FiltersActivity extends BaseActivity {
  @Inject JudgesQuery mJudgesQuery;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.frame_court)
  View mFrameCourt;

  @BindView(R.id.frame_region)
  View mFrameArea;

  @BindView(R.id.frame_city)
  View mFrameCity;

  @BindView(R.id.frame_cases)
  FrameLayout mFrameCases;

  @BindView(R.id.frame_cases_selected)
  FrameLayout mFrameCasesSelected;

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.option)
  TextView mRegion;

  @BindView(R.id.city)
  TextView mCity;

  @BindView(R.id.cases_selected)
  TextView mCasesSelected;

  @BindView(R.id.cases_not_selected)
  TextView mCasesNotSelected;

  @BindString(R.string.text_not_selected)
  String mNotSelected;

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersActivity.class);
    context.startActivity(i);
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

  @Override
  protected void onResume() {
    super.onResume();
    setFilterValues();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mJudgesQuery.clear();
        onBackPressed();
        return true;
      case R.id.action_check:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_check, menu);
    return true;
  }

  @OnClick({R.id.frame_court, R.id.frame_region, R.id.frame_city, R.id.frame_cases})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.frame_court:
        FiltersCourtTypeActivity.start(this);
        return;
      case R.id.frame_region:
        FiltersRegionActivity.start(this);
        return;
      case R.id.frame_city:
        FiltersCityActivity.start(this);
        return;
      case R.id.frame_cases:
        FiltersCategoryActivity.start(this);
        return;
    }
  }

  private void setFilterValues() {
    if (mJudgesQuery.getCourtType() != null) {
      mCourt.setText(mJudgesQuery.getCourtTypeClient());
    } else {
      mCourt.setText(mNotSelected);
    }

    if (mJudgesQuery.getRegionId() != null) {
      mRegion.setText(mJudgesQuery.getRegion());
    } else {
      mRegion.setText(mNotSelected);
    }

    if (mJudgesQuery.getCityId() != null) {
      mCity.setText(mJudgesQuery.getCity());
    } else {
      mCity.setText(mNotSelected);
    }

    if (mJudgesQuery.getAffairs() != null && mJudgesQuery.getAffairs().size() > 0) {
      mCasesNotSelected.setVisibility(View.INVISIBLE);
      mFrameCasesSelected.setVisibility(View.VISIBLE);
      mCasesSelected.setText(String.valueOf(mJudgesQuery.getAffairs().size()));
    } else {
      mCasesNotSelected.setVisibility(View.VISIBLE);
      mFrameCasesSelected.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public void switchTab(int position, boolean isSelected) {}

  @Override
  public void setToolBarTitle(String title) {}

  @Override
  public void setToolbarVisibility(int visible) {}

  @Override
  public void setOptionsMenuVisibility(boolean isVisible) {}
}
