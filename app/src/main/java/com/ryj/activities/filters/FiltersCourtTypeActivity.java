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
import com.ryj.adapters.CourtTypeAdapter;
import com.ryj.listeners.OnCourtTypeAdapterListener;
import com.ryj.models.filters.Filters;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ryj.models.enums.Courts.APPELLATE;
import static com.ryj.models.enums.Courts.APPELLATE_ADMIN;
import static com.ryj.models.enums.Courts.APPELLATE_ECONOMIC;
import static com.ryj.models.enums.Courts.HIGHEST_ADMINISTRATIVE;
import static com.ryj.models.enums.Courts.HIGHEST_CIVIL_CRIMINAL;
import static com.ryj.models.enums.Courts.HIGHEST_ECONOMIC;
import static com.ryj.models.enums.Courts.LOCAL;
import static com.ryj.models.enums.Courts.LOCAL_ADMINISTRATIVE;
import static com.ryj.models.enums.Courts.LOCAL_ECONOMIC;
import static com.ryj.models.enums.Courts.SUPREME;

/** Created by andrey on 9/29/17. */
public class FiltersCourtTypeActivity extends BaseActivity implements OnCourtTypeAdapterListener {
  @Inject Filters mFilters;

  @BindView(R.id.recycler_view_list)
  RecyclerView mCourtTypes;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindArray(R.array.text_courts_client)
  String[] mCourtTypesClient;

  private String[] mCourtTypesServer = {
    SUPREME.toString(),
    HIGHEST_CIVIL_CRIMINAL.toString(),
    HIGHEST_ECONOMIC.toString(),
    HIGHEST_ADMINISTRATIVE.toString(),
    APPELLATE.toString(),
    APPELLATE_ECONOMIC.toString(),
    APPELLATE_ADMIN.toString(),
    LOCAL.toString(),
    LOCAL_ECONOMIC.toString(),
    LOCAL_ADMINISTRATIVE.toString()
  };

  private CourtTypeAdapter mAdapter;

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersCourtTypeActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters_types_of_courts);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mAdapter = new CourtTypeAdapter(this, this, getLastPosition(), mCourtTypesClient);
    mCourtTypes.setLayoutManager(new LinearLayoutManager(this));
    mCourtTypes.setAdapter(mAdapter);
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
  public void onHolderCheckedChange(int position) {
    if (position != -1) {
      mFilters.setCourtType(mCourtTypesServer[position]);
      mFilters.setCourtTypeClient(mCourtTypesClient[position]);
    } else {
      mFilters.setCourtType(null);
      mFilters.setCourtTypeClient(null);
    }
  }

  private int getLastPosition() {
    for (int i = 0; i < mCourtTypesServer.length; i++) {
      if (mCourtTypesServer[i].equals(mFilters.getCourtType())) {
        return i;
      }
    }
    return -1;
  }
}
