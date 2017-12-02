package com.ryj.activities.filters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.adapters.RegionAdapter;
import com.ryj.interfaces.OnHolderClickListener;
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
import butterknife.OnClick;

/** Created by andrey on 9/19/17. */
public class FiltersCityActivity extends BaseActivity implements OnHolderClickListener {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Filters mFilters;

  @BindView(R.id.cities)
  RecyclerView mCities;

  private RegionAdapter<Area> mAdapter;
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
    return null;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return null;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters_city);
    getComponent().inject(this);
    setSoftInputMode();
    mAdapter = new RegionAdapter<Area>(this, this);
    mCities.setLayoutManager(new LinearLayoutManager(this));
    mCities.setAdapter(mAdapter);
    load();
  }

  public void load() {
    mApi.getCities(mFilters.getRegionId())
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .compose(RxUtils.applySchedulers())
        .subscribe(
            response -> {
              mAreas.addAll(getAreas(response.getCities()));
              mAdapter.reloadItems(getAreas(response.getCities()));
              mAdapter.setCurrentCheckedItem(getLastCityIdPosition());
            },
            throwable -> {
              mErrorHandler.handleError(throwable, this);
            });
  }

  private int getLastCityIdPosition() {
    for (int i = 0; i < mAreas.size(); i++) {
      if (mFilters.getCityId() != null && mFilters.getCityId() == mAreas.get(i).getId()) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    mFilters.setCityId(mAreas.get(position).getId());
    mFilters.setCity(mAreas.get(position).getName());
  }

  @OnClick({R.id.back, R.id.check})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        mFilters.clearCity();
        onBackPressed();
        return;
      case R.id.check:
        onBackPressed();
        return;
    }
  }
}
