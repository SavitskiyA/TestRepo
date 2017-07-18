package com.ryj.activities.auth.signup.citizen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 7/11/17.
 */

public class CitizenSignUpActivity extends BaseActivity {
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;

  public static void start(Context context) {
    Intent i = new Intent(context, CitizenSignUpActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_citizen);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
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
}
