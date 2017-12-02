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
import com.ryj.adapters.CategoryAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.enums.Affairs;
import com.ryj.models.filters.Filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 9/20/17. */
public class FiltersCategoryActivity extends BaseActivity implements OnHolderClickListener {
  @Inject Filters mFilters;

  @BindView(R.id.categories)
  RecyclerView mCategories;

  @BindArray(R.array.text_categories_client)
  String[] mAffairsClient;

  private Affairs[] mAffairsServer = {
    Affairs.ADMIN_VIOLATION,
    Affairs.ADMINISTRATIVE,
    Affairs.CIVIL,
    Affairs.CRIMINAL,
    Affairs.ECONOMIC,
    Affairs.URGENT_LAWSUITS
  };

  private CategoryAdapter<String> mAdapter;

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersCategoryActivity.class);
    context.startActivity(i);
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
    setContentView(R.layout.activity_filters_categoty);
    getComponent().inject(this);
    setSoftInputMode();
    if (mFilters.getAffairsBooleans() == null) {
      mFilters.setAffairsBooleans(new boolean[mAffairsClient.length]);
    }
    mAdapter =
        new CategoryAdapter<String>(
            this, this, Arrays.asList(mAffairsClient), mFilters.getAffairsBooleans());
    mCategories.setLayoutManager(new LinearLayoutManager(this));
    mCategories.setAdapter(mAdapter);
  }

  private List<String> getChoosenCategories(boolean[] choosenCategoriesBoolean) {
    List<String> choosenCategories = new ArrayList<>();
    for (int i = 0; i < mAffairsServer.length; i++) {
      if (choosenCategoriesBoolean[i]) {
        choosenCategories.add(mAffairsServer[i].toString());
      }
    }
    return choosenCategories;
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    mFilters.getAffairsBooleans()[position] = enable;
  }

  @OnClick({R.id.back, R.id.check})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        mFilters.clearAffairs();
        onBackPressed();
        break;
      case R.id.check:
        mFilters.setAffairs(getChoosenCategories(mFilters.getAffairsBooleans()));
        onBackPressed();
        break;
    }
  }
}
