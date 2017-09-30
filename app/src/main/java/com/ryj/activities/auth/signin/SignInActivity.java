package com.ryj.activities.auth.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.analytics.AnalyticsActivity;
import com.ryj.activities.auth.PasswordRecoveryActivity;
import com.ryj.activities.auth.signup.SignUpActivity;
import com.ryj.activities.judges.JudgesActivity;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/** Created by andrey on 7/7/17. */
public class SignInActivity extends BaseActivity {
  private static final String TAG = "SignInActivity";
  @Inject Prefs mPrefs;
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;

  @BindView(R.id.image)
  ImageView mImage;

  @BindView(R.id.email)
  EditText mEmail;

  @BindView(R.id.password)
  EditText mPassword;

  @BindView(R.id.sign_in)
  ImageButton mSignIn;

  @BindView(R.id.forgot_password)
  TextView mForgotPassword;

  @BindView(R.id.sign_up)
  Button mSignUp;

  @BindView(R.id.img_judges)
  ImageView mJudgesImage;

  @BindView(R.id.txt_judges)
  TextView mJudgesText;

  @BindView(R.id.img_analytics)
  ImageView mAnalyticsImage;

  @BindView(R.id.txt_analytics)
  TextView mAnalyticsText;

  @BindView(R.id.img_news)
  ImageView mNewsImage;

  @BindView(R.id.txt_news)
  TextView mNewsText;

  @BindString(R.string.text_email_format_wrong)
  String mEmailValidationError;

  @BindString(R.string.text_password_char_error)
  String mPasswordValidationError;

  @BindString(R.string.text_sign_in_error)
  String mSignInError;

  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;

  @BindView(R.id.input_layout_password)
  TextInputLayout mPasswordLayout;

  @BindString(R.string.text_email_must_not_be_empty)
  String mEmailEmpty;

  @BindString(R.string.text_password_must_not_be_empty)
  String mPasswordEmpty;

  @BindString(R.string.text_ok)
  String mOk;

  private AlertDialog mDialog;
  private SpinnerDialog mSpinnerDialog;

  public static void start(Context context) {
    Intent i = new Intent(context, SignInActivity.class);
    context.startActivity(i);
  }

  public static void startWithEmptyStack(Context context) {
    Intent i = new Intent(context, SignInActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signin);
    getComponent().inject(this);
    ButterKnife.bind(this);
    mPrefs.setIsFirstTutorialLaunch(false);
    mForgotPassword.setPaintFlags(mForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    mDialog = getDialog(mOk);
    mSpinnerDialog = getSpinnerDialog();
  }

  @OnClick({
    R.id.sign_in,
    R.id.forgot_password,
    R.id.sign_up,
    R.id.img_judges,
    R.id.img_analytics,
    R.id.img_news,
    R.id.txt_news,
    R.id.txt_analytics,
    R.id.txt_judges
  })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.sign_in:
        createSignInRequest();
        break;
      case R.id.forgot_password:
        PasswordRecoveryActivity.start(this);
        break;
      case R.id.sign_up:
        SignUpActivity.start(this);
        break;
      case R.id.img_judges:
        JudgesActivity.start(this);
        break;
      case R.id.img_analytics:
        AnalyticsActivity.start(this);
        break;
      case R.id.img_news:

        break;
      case R.id.txt_judges:
        JudgesActivity.start(this);
        break;
      case R.id.txt_analytics:
        AnalyticsActivity.start(this);
        break;
      case R.id.txt_news:

        break;
      default:
        showToast(TAG);
    }
  }

  @OnFocusChange({R.id.email, R.id.password})
  public void onFocusChanged(View v, boolean isFocused) {
    switch (v.getId()) {
      case R.id.email:
        if (!isFocused && mEmail.length() > 0 && !isEmailValid()) {
          mEmailLayout.setError(mEmailValidationError);
        }
        break;
      case R.id.password:
        if (!isFocused && mPassword.length() > 0 && !isPasswordValid()) {
          mPasswordLayout.setError(mPasswordValidationError);
        }
        break;
    }
  }

  @OnTextChanged(R.id.email)
  protected void handleEmailChange() {
    mEmailLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.password)
  protected void handlePasswordChange() {
    mPasswordLayout.setErrorEnabled(false);
  }

  private boolean isAllFieldsValid() {
    return isEmailValid() && isPasswordValid();
  }

  private boolean isEmailValid() {
    return FieldValidation.isValid(mEmail.getText().toString(), StringUtils.EMAIL_PATTERN);
  }

  private boolean isPasswordValid() {
    return mPassword.length() > Constants.MIN_PASSWORD_LENGTH;
  }

  private void validateFields() {
    if (mEmail.length() > 0 && !isEmailValid()) {
      mEmailLayout.setError(mEmailValidationError);
    } else if (mEmail.length() == 0) {
      mDialog.setMessage(mEmailEmpty);
      mDialog.show();
      return;
    }
    if (mPassword.length() > 0 && !isPasswordValid()) {
      mPasswordLayout.setError(mPasswordValidationError);
    } else if (mPassword.length() == 0) {
      mDialog.setMessage(mPasswordEmpty);
      mDialog.show();
      return;
    }
  }

  private void createSignInRequest() {
    validateFields();
    if (isAllFieldsValid()) {
      executeSignInRequest(
          mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
    }
  }

  private void executeSignInRequest(String email, String password) {
    mSpinnerDialog.show(getSupportFragmentManager(), StringUtils.EMPTY_STRING);
    mApi.signIn(email, password, Constants.PLATFORM, null)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            response -> {
              mSpinnerDialog.dismiss();
              BottomBarContainerActivity.start(this);
            },
            throwable -> {
              mSpinnerDialog.dismiss();
              mErrorHandler.handleError(throwable, this);
            });
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
}
