package com.ryj.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.models.enums.RequestType;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/** Created by andrey on 7/10/17. */
public class PasswordRecoveryActivity extends BaseActivity {
  private static final String TAG = "PasswordRecoveryActivity";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.email)
  EditText mEmail;

  @BindView(R.id.send)
  ImageButton mSend;

  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;

  @BindString(R.string.text_email_format_wrong)
  String mEmailValidationError;

  private SpinnerDialog mSpinnerDialog;

  public static void start(Context context) {
    Intent i = new Intent(context, PasswordRecoveryActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password_recovery);
    getComponent().inject(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    mSpinnerDialog = getSpinnerDialog();
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
    if (editable.length() > 0) {
      mSend.setEnabled(true);
      mEmailLayout.setErrorEnabled(false);
    } else {
      mSend.setEnabled(false);
    }
  }

  private void validateEmail() {
    if (FieldValidation.isValid(mEmail.getText().toString().trim(), StringUtils.EMAIL_PATTERN)) {
      executeRestorePasswordRequest(mEmail.getText().toString().trim());
    } else {
      mEmailLayout.setError(mEmailValidationError);
    }
  }

  @OnClick(R.id.send)
  public void onClick() {
    validateEmail();
  }

  private void executeRestorePasswordRequest(String email) {
    doRequest(
        mApi.restorePassword(email)
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .compose(RxUtils.applySchedulers())
            .compose(
                RxUtils.applyBeforeAndAfter(
                    (disposable) ->
                        mSpinnerDialog.show(getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                    () -> mSpinnerDialog.dismiss()))
            .subscribe(
                response -> {
                  PasswordRecoveryFinishActivity.start(this);
                },
                throwable -> {
                  mErrorHandler.handleErrorByRequestType(
                      throwable, this, RequestType.PASSWORD_RECOVERY);
                }));
  }
}
