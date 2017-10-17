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
import com.ryj.models.response.Region;
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
public class FiltersRegionActivity extends BaseActivity implements Loadable, OnAreaAdapterListener {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Filters mFilters;

  @BindView(R.id.recycler_view_list)
  RecyclerView mRegions;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  private AreaAdapter mAdapter;
  private int mPage = 1;
  private List<Area> mAreas = new ArrayList<>();

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersRegionActivity.class);
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
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters_regions);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mAdapter = new AreaAdapter(this, this, this);
    mRegions.setLayoutManager(new LinearLayoutManager(this));
    mRegions.setAdapter(mAdapter);
    reset();
  }

  @Override
  public void load(int page) {
    mApi.getRegions(page)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .compose(RxUtils.applySchedulers())
        .subscribe(
            response -> {
              if (page == 1) {
                mAdapter.reloadItems(getAreas(response.getRegions()));
              } else {
                mAdapter.addItems(getAreas(response.getRegions()));
              }
              mAreas.addAll(getAreas(response.getRegions()));
              mAdapter.setCurrentCheckedItem(getLastRegionIdPosition());
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
    mFilters.setRegionId(itemId);
    mFilters.setRegion(mAreas.get(itemIdPosition).getName());
    mFilters.setCityId(null);
    mFilters.setCity(null);
  }

  private List<Area> getAreas(List<Region> regions) {
    List<Area> areas = new ArrayList<>();
    for (Region region : regions) {
      areas.add(new Area(region.getId(), region.getName()));
    }
    return areas;
  }

  private int getLastRegionIdPosition() {
    for (int i = 0; i < mAreas.size(); i++) {
      if (mFilters.getRegionId() != null && mFilters.getRegionId() == mAreas.get(i).getId()) {
        return i;
      }
    }
    return -1;
  }
}
