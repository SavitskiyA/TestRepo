package com.ryj.activities.auth.signup;

import android.content.Context;
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
import com.ryj.adapters.ItemListRecyclerAdapter;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.filters.Items;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 7/22/17. */
public class SignUpListActivity extends BaseActivity implements OnHolderListener {
  private static String EXTRA_LIST_TITLE = "list_title";
  private static boolean mIsButtonResponsive;
  @Inject Items mItems;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;

  @BindView(R.id.ok)
  Button mOk;

  private ItemListRecyclerAdapter<String> mAdapter;

  public static void start(Context context, String title) {
    Intent i = new Intent(context, SignUpListActivity.class);
    i.putExtra(EXTRA_LIST_TITLE, title);
    mIsButtonResponsive = context instanceof SignUpJudgeActivity ? true : false;
    context.startActivity(i);
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
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    Bundle extras = getIntent().getExtras();
    mTitle.setText(extras.getString(EXTRA_LIST_TITLE));
    mAdapter =
        new ItemListRecyclerAdapter<String>(
            this, this, mItems.getItemsClient(), mItems.getBooleans());
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(mAdapter);
    mOk.setEnabled(!mIsButtonResponsive);
  }

  @OnClick(R.id.ok)
  public void onClick() {
    onBackPressed();
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
    if (mIsButtonResponsive) {
      mOk.setEnabled(enable);
    }
    mItems.setBoolean(enable, position);
  }
}
