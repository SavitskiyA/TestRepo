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
import com.ryj.listeners.Loadable;
import com.ryj.listeners.OnAreaAdapterListener;
import com.ryj.models.filters.Filters;
import com.ryj.models.response.Area;
import com.ryj.models.response.City;
import com.ryj.utils.RxUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by andrey on 9/19/17. */
public class FiltersCityActivity extends BaseActivity implements Loadable, OnAreaAdapterListener {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Filters mFilters;

  @BindView(R.id.cities)
  RecyclerView mCities;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  private AreaAdapter mAdapter;
  private int mPage = 1;
  private List<Area> mAreas = new ArrayList<>();

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
    reset();
  }

  @Override
  public void load(int page) {
    mApi.getCities(getRegionId(), page)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .compose(RxUtils.applySchedulers())
        .subscribe(
            response -> {
              if (page == 1) {
                mAdapter.reloadItems(getAreas(response.getCities()));
              } else {
                mAdapter.addItems(getAreas(response.getCities()));
              }
              mAreas.addAll(getAreas(response.getCities()));
              mAdapter.setCurrentCheckedItem(getLastCityIdPosition());
              if (response.getNextPage() == null) {
                mAdapter.setIsLoadable(false);
              }
            },
            throwable -> {
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
    mFilters.setCityId(itemId);
    mFilters.setCity(mAreas.get(itemIdPosition).getName());
  }

  private Integer getRegionId() {
    return mFilters.getRegionId() == null ? null : mFilters.getRegionId();
  }

  private int getLastCityIdPosition() {
    for (int i = 0; i < mAreas.size(); i++) {
      if (mFilters.getCityId() != null && mFilters.getCityId() == mAreas.get(i).getId()) {
        return i;
      }
    }
    return -1;
  }
}
