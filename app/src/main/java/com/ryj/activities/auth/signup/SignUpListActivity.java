package com.ryj.activities.auth.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signup.judge.SignUpJudgeActivity;
import com.ryj.adapters.SignUpItemAdapter;
import com.ryj.listeners.OnButtonEnableListener;
import com.ryj.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 7/22/17. */
public class SignUpListActivity extends BaseActivity implements OnButtonEnableListener {
  private static String EXTRA_LIST_TITLE = "list_title";
  private static String EXTRA_LIST_ARRAY = "list_array";
  private static String EXTRA_LIST_BOOLEANS = "list_booleans";
  private static boolean mIsButtonResponsive;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;

  @BindView(R.id.ok)
  Button mOk;

  private SignUpItemAdapter mAdapter;

  public static void startForResult(
      Activity activity,
      String title,
      String[] categories,
      boolean[] choosenCategoriesBoolean,
      int requestCode) {
    Intent intent = new Intent(activity, SignUpListActivity.class);
    intent.putExtra(EXTRA_LIST_TITLE, title);
    intent.putExtra(EXTRA_LIST_ARRAY, categories);
    intent.putExtra(EXTRA_LIST_BOOLEANS, choosenCategoriesBoolean);
    activity.startActivityForResult(intent, requestCode);
    mIsButtonResponsive = activity instanceof SignUpJudgeActivity ? true : false;
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolbar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_list);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    Bundle extras = getIntent().getExtras();
    mTitle.setText(extras.getString(EXTRA_LIST_TITLE));
    String[] categories = extras.getStringArray(EXTRA_LIST_ARRAY);
    boolean[] choosenCategories = extras.getBooleanArray(EXTRA_LIST_BOOLEANS);
    mAdapter = new SignUpItemAdapter(categories, choosenCategories, this, this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(mAdapter);
    mOk.setEnabled(!mIsButtonResponsive);
  }

  @OnClick(R.id.ok)
  public void onClick() {
    String choosenCategories =
        StringUtils.getStringFromList(
            mAdapter.getChoosenCategoriesList(), StringUtils.DOT_COMMA_SPACE);
    boolean[] choosenCategoriesBooleans = mAdapter.getChoosenCategoriesBooleans();
    SignUpJudgeActivity.sendActivityResult(this, choosenCategories, choosenCategoriesBooleans);
  }

  @Override
  public void onButtonEnable(boolean enable) {
    if (mIsButtonResponsive) {
      mOk.setEnabled(enable);
    }
  }
}
