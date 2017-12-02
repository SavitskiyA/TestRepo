package com.ryj.activities.auth.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signup.citizen.SignUpCitizenActivity;
import com.ryj.activities.auth.signup.judge.SignUpJudgeAutocompleteActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 7/10/17. */
public class SignUpActivity extends BaseActivity {
  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.judge)
  FrameLayout mJudge;

  @BindView(R.id.lawyer)
  FrameLayout mAdvocate;

  @BindView(R.id.citizen)
  FrameLayout mCitizen;

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
  }

  @OnClick({R.id.judge, R.id.lawyer, R.id.citizen})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.judge:
        SignUpJudgeAutocompleteActivity.start(this);
        break;
      case R.id.lawyer:
        SignUpLawyerActivity.start(this);
        break;
      case R.id.citizen:
        SignUpCitizenActivity.start(this);
        break;
    }
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
