package com.ryj.activities.filters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.adapters.AreaAdapter;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.listeners.Loadable;
import com.ryj.listeners.OnAreaAdapterListener;
import com.ryj.models.JudgesQuery;
import com.ryj.models.response.Area;
import com.ryj.models.response.City;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/** Created by andrey on 9/19/17. */
public class FiltersCityActivity extends BaseActivity
    implements Loadable, OnAreaAdapterListener {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject JudgesQuery mJudgeQuery;

  @BindView(R.id.cities)
  RecyclerView mCities;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  private AreaAdapter mAdapter;
  private int mPage = 1;
  private List<Area> mAreas = new ArrayList<>();
  private SpinnerDialog mSpinnerDialog;

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersCityActivity.class);
    context.startActivity(i);
  }

  private List<Area> getAreas(List<City> cities) {
    List<Area> areas = new ArrayList<>();
    for (City city : cities) {
      areas.add(new Area(city.getId(), city.getName()));
    }
    return areas;
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
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters_city);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mAdapter = new AreaAdapter(this, this, this);
    mCities.setLayoutManager(new LinearLayoutManager(this));
    mCities.setAdapter(mAdapter);
    mSpinnerDialog = getSpinnerDialog();
    mSpinnerDialog.setCancelable(false);
    reset();
  }

  @Override
  public void setItemCount(int count) {}

  @Override
  public void load(int page) {
    mSpinnerDialog.show(getSupportFragmentManager(), StringUtils.EMPTY_STRING);
    mApi.getCities(
            null,
            getRegionId(),
            mJudgeQuery.getSort().toString(),
            mJudgeQuery.getDirection().toString(),
            page)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            response -> {
              mSpinnerDialog.dismiss();
              if (page == 1) {
                mAdapter.reloadItems(getAreas(response.getCities()));
              } else {
                mAdapter.addItems(getAreas(response.getCities()));
              }
              mAreas.addAll(getAreas(response.getCities()));
              mAdapter.setLastCheckedItem(getLastCityIdPosition());
              if (response.getNextPage() == null) {
                mAdapter.setIsLoadable(false);
              }
            },
            throwable -> {
              mSpinnerDialog.dismiss();
              mErrorHandler.handleError(throwable, this);
            });
  }

  @Override
  public int increment() {
    return mPage++;
  }

  @Override
  public void reset() {
    mPage = 1;
    mAdapter.setIsLoadable(true);
    load(mPage);
  }

  @Override
  public void onHolderCheckedChange(Integer itemId, Integer itemIdPosition) {
    mJudgeQuery.setCityId(itemId);
    mJudgeQuery.setCity(mAreas.get(itemIdPosition).getName());
  }

  private Integer getRegionId() {
    return mJudgeQuery.getRegionId() == null ? null : mJudgeQuery.getRegionId();
  }

  private int getLastCityIdPosition() {
    for (int i = 0; i < mAreas.size(); i++) {
      if (mJudgeQuery.getCityId() != null && mJudgeQuery.getCityId() == mAreas.get(i).getId()) {
        return i;
      }
    }
    return -1;
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
