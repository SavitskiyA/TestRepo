package com.ryj.activities.filters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.adapters.CategoryAdapter;
import com.ryj.models.filters.Filters;
import com.ryj.models.enums.Affairs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by andrey on 9/20/17. */
public class FiltersCategoryActivity extends BaseActivity {
  @Inject Filters mFilters;

  @BindView(R.id.categories)
  RecyclerView mCategories;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindArray(R.array.text_categories_client)
  String[] mCategoriesClient;

  private Affairs[] mCategoriesBackEnd = {
    Affairs.ADMIN_VIOLATION,
    Affairs.ADMINISTRATIVE,
    Affairs.CIVIL,
    Affairs.CRIMINAL,
    Affairs.ECONOMIC,
    Affairs.URGENT_LAWSUITS
  };

  private CategoryAdapter mAdapter;

  public static void start(Context context) {
    Intent i = new Intent(context, FiltersCategoryActivity.class);
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
    setContentView(R.layout.activity_filters_categoty);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mAdapter = new CategoryAdapter(this, mCategoriesClient, mFilters.getAffairsBooleans());
    mCategories.setLayoutManager(new LinearLayoutManager(this));
    mCategories.setAdapter(mAdapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mFilters.setAffairs(getChoosenCategories(mAdapter.getSelectedCategoriesBoolean()));
        mFilters.setAffairsBooleans(mAdapter.getSelectedCategoriesBoolean());
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private List<String> getChoosenCategories(boolean[] choosenCategoriesBoolean) {
    List<String> choosenCategories = new ArrayList<>();
    for (int i = 0; i < mCategoriesBackEnd.length; i++) {
      if (choosenCategoriesBoolean[i]) {
        choosenCategories.add(mCategoriesBackEnd[i].toString());
      }
    }
    return choosenCategories;
  }
}
