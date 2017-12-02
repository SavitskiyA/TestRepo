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
import com.ryj.adapters.CourtTypeAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.filters.Filters;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

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
public class FiltersCourtTypeActivity extends BaseActivity implements OnHolderClickListener {
  @Inject
  Filters mFilters;

  @BindView(R.id.types_of_courts)
  RecyclerView mCourtTypes;

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

  private CourtTypeAdapter<String> mAdapter;

  public static void start(Context context) {
    Intent i = new Intent(context, com.ryj.activities.filters.FiltersCourtTypeActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filters_types_of_courts);
    getComponent().inject(this);
    setSoftInputMode();
    if (mFilters.getCourtsBooleans() == null) {
      mFilters.setCourtsBooleans(new boolean[mCourtTypesClient.length]);
    }
    mAdapter =
            new CourtTypeAdapter<String>(
                    this, this, Arrays.asList(mCourtTypesClient), mFilters.getCourtsBooleans());
    mCourtTypes.setLayoutManager(new LinearLayoutManager(this));
    mCourtTypes.setAdapter(mAdapter);
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

  private int getLastPosition() {
    for (int i = 0; i < mCourtTypesServer.length; i++) {
      if (mCourtTypesServer[i].equals(mFilters.getCourtType())) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    if (position != -1) {
      mFilters.setCourtType(mCourtTypesServer[position]);
      mFilters.setCourtTypeClient(mCourtTypesClient[position]);
      mFilters.getCourtsBooleans()[position] = enable;
    } else {
      mFilters.setCourtType(null);
      mFilters.setCourtTypeClient(null);
    }
  }

  @OnClick({R.id.back, R.id.check})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        mFilters.clearCourtType();
        onBackPressed();
        return;
      case R.id.check:
        onBackPressed();
        return;
    }
  }
 }
