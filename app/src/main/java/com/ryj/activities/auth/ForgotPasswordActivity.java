package com.ryj.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by andrey on 7/10/17.
 */

public class ForgotPasswordActivity extends BaseActivity {
  private static final String TAG = "ForgotPasswordActivity";
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.email)
  EditText mEmail;
  @BindView(R.id.send)
  Button mSend;

  public static void start(Context context) {
    Intent i = new Intent(context, ForgotPasswordActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    ButterKnife.bind(this);
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

  @OnTextChanged(R.id.email)
  public void handleTextChange(Editable editable) {
    if (!FieldValidation.isValid(editable.toString(), StringUtils.EMAIL_PATTERN)) {
      mSend.setEnabled(false);
    } else {
      mSend.setEnabled(true);
    }
  }

  @OnClick(R.id.send)
  public void onClick() {
    SignInActivity.start(this);
    finish();
  }

}
