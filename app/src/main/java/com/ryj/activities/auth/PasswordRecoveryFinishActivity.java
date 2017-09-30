package com.ryj.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signin.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 8/3/17. */
public class PasswordRecoveryFinishActivity extends BaseActivity {
  private static final String TAG = "PasswordRecoveryFinishActivity";

  @BindView(R.id.ok)
  Button mOk;

  public static void start(Context context) {
    Intent i = new Intent(context, PasswordRecoveryFinishActivity.class);
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
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password_recovery_finish);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.ok)
  public void onClick() {
    SignInActivity.startWithEmptyStack(this);
    finish();
  }

  @Override
  public void switchTab(int position, boolean isSelected) {}

  @Override
  public void setToolBarTitle(String title) {}

  @Override
  public void setToolbarVisibility(int visible) {}

  @Override
  public void setOptionsMenuVisibility(boolean isVisible) {}
}
