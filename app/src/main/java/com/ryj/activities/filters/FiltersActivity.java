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
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.models.Filters;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/6/17. */
public class FiltersActivity extends BaseActivity {
  public static final String EXTRA_PARENT = "parent";
  public static final int PARENT_JUDGES = 0;
  public static final int PARENT_COURTS = 1;
  @Inject Filters mFilters;

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

  @BindView(R.id.frame_cases_title)
  FrameLayout mFrameCasesTitle;

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.region)
  TextView mRegion;

  @BindView(R.id.city)
  TextView mCity;

  @BindView(R.id.cases_selected)
  TextView mCasesSelected;

  @BindView(R.id.cases_not_selected)
  TextView mCasesNotSelected;

  @BindString(R.string.text_not_selected)
  String mNotSelected;

  @BindView(R.id.cancel_type_of_court)
  ImageView mCourtTypeCancel;

  @BindView(R.id.cancel_region)
  ImageView mRegionCancel;

  @BindView(R.id.cancel_city)
  ImageView mCityCancel;

  @BindView(R.id.cancel_affair)
  ImageView mCancelAffairs;

  @BindView(R.id.court_type_right_arrow)
  ImageView mCourtTypeArrow;

  @BindView(R.id.region_right_arrow)
  ImageView mRegionArrow;

  @BindView(R.id.city_right_arrow)
  ImageView mCityArrow;

  @BindView(R.id.affairs_right_arrow)
  ImageView mAffairsArrow;

  public static void start(Context context, int parent) {
    Intent i = new Intent(context, FiltersActivity.class);
    i.putExtra(EXTRA_PARENT, parent);
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
    setFilterView();
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
    if (getIntent().getExtras().getInt(EXTRA_PARENT) == PARENT_COURTS) {
      setCasesVisible(View.GONE);
    } else {
      setCasesVisible(View.VISIBLE);
    }
  }

  private void setCasesVisible(int visible) {
    mFrameCases.setVisibility(visible);
    mFrameCasesTitle.setVisibility(visible);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.action_reset:
        mFilters.clear();
        setFilterView();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_reset_filters, menu);
    return true;
  }

  @OnClick({
    R.id.frame_court,
    R.id.frame_region,
    R.id.frame_city,
    R.id.frame_cases,
    R.id.cancel_type_of_court,
    R.id.cancel_region,
    R.id.cancel_city,
    R.id.cancel_affair
  })
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
      case R.id.cancel_type_of_court:
        mFilters.clearCourtType();
        setCourtTypeView();
        return;
      case R.id.cancel_region:
        mFilters.clearRegion();
        setRegionView();
        return;
      case R.id.cancel_city:
        mFilters.clearCity();
        setCityView();
        return;
      case R.id.cancel_affair:
        mFilters.clearAffairs();
        setAffairsView();
        return;
    }
  }

  private void setFilterView() {
    setCourtTypeView();
    setRegionView();
    setCityView();
    setAffairsView();
  }

  private void setCourtTypeView() {
    if (mFilters.getCourtType() != null) {
      mCourt.setText(mFilters.getCourtTypeClient());
      mCourtTypeArrow.setVisibility(View.GONE);
      mCourtTypeCancel.setVisibility(View.VISIBLE);
    } else {
      mCourt.setText(mNotSelected);
      mCourtTypeArrow.setVisibility(View.VISIBLE);
      mCourtTypeCancel.setVisibility(View.GONE);
    }
  }

  private void setRegionView() {
    if (mFilters.getRegionId() != null) {
      mRegion.setText(mFilters.getRegion());
      mRegionArrow.setVisibility(View.GONE);
      mRegionCancel.setVisibility(View.VISIBLE);
    } else {
      mRegion.setText(mNotSelected);
      mRegionArrow.setVisibility(View.VISIBLE);
      mRegionCancel.setVisibility(View.GONE);
    }
  }

  private void setCityView() {
    if (mFilters.getCityId() != null) {
      mCity.setText(mFilters.getCity());
      mCityArrow.setVisibility(View.GONE);
      mCityCancel.setVisibility(View.VISIBLE);
    } else {
      mCity.setText(mNotSelected);
      mCityArrow.setVisibility(View.VISIBLE);
      mCityCancel.setVisibility(View.GONE);
    }
  }

  private void setAffairsView() {
    if (mFilters.getAffairs() != null && mFilters.getAffairs().size() > 0) {
      mCasesNotSelected.setVisibility(View.INVISIBLE);
      mFrameCasesSelected.setVisibility(View.VISIBLE);
      mCasesSelected.setText(String.valueOf(mFilters.getAffairs().size()));
      mCancelAffairs.setVisibility(View.VISIBLE);
      mAffairsArrow.setVisibility(View.GONE);
    } else {
      mAffairsArrow.setVisibility(View.VISIBLE);
      mFrameCasesSelected.setVisibility(View.INVISIBLE);
      mCasesNotSelected.setVisibility(View.VISIBLE);
      mCancelAffairs.setVisibility(View.GONE);
    }
  }
}
