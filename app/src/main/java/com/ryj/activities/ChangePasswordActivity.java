package com.ryj.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 11/21/17. */
public class ChangePasswordActivity extends BaseActivity {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;

  @BindView(R.id.current_password)
  EditText mCurrentPassword;

  @BindView(R.id.new_password)
  EditText mNewPassword;

  @BindView(R.id.repeat_password)
  EditText mRepeatPassword;

  @BindView(R.id.input_layout_current_password)
  TextInputLayout mCurrentPasswordLayout;

  @BindView(R.id.input_layout_new_password)
  TextInputLayout mNewPasswordLayout;

  @BindView(R.id.input_layout_repeat_password)
  TextInputLayout mRepeatPasswordLayout;

  @BindString(R.string.text_password_char_error)
  String mPasswordMustContainError;

  @BindString(R.string.text_password_must_not_be_empty)
  String mPasswordMustNotBeEmpty;

  @BindView(R.id.save)
  Button mSave;

  @BindString(R.string.text_ok)
  String mOk;

  private AlertDialog mDialog;

  public static void start(Context context) {
    Intent i = new Intent(context, ChangePasswordActivity.class);
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
    setContentView(R.layout.activity_change_password);
    getComponent().inject(this);
    setSoftInputMode();
    mDialog = getDialog(mOk);
  }

  @OnClick({R.id.save, R.id.back})
  void onClick(View v) {
    switch (v.getId()) {
      case R.id.save:
        if (!StringUtils.isFieldEmpty(
            new EditText[] {mCurrentPassword, mNewPassword, mRepeatPassword})) {
          mDialog.setMessage(mPasswordMustNotBeEmpty);
          mDialog.show();
        } else {
          changePassword();
        }
        break;
      case R.id.back:
        onBackPressed();
        break;
    }
  }

  private void changePassword() {
    doRequest(
        mApi.changePassword(
                mCurrentPassword.getText(), mNewPassword.getText(), mRepeatPassword.getText())
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .compose(RxUtils.applySchedulersForCompletable())
            .compose(
                RxUtils.applyBeforeAndAfterForCompletable(
                    (disposable) ->
                        getSpinnerDialog()
                            .show(getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                    () -> getSpinnerDialog().dismiss()))
            .subscribe(
                () -> {
                  SignInActivity.start(this);
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this);
                }));
  }
}
