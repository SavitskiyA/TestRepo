package com.ryj.activities.auth.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.ryj.activities.analytics.AnalyticsActivity;
import com.ryj.activities.auth.PasswordRecoveryActivity;
import com.ryj.activities.auth.signup.SignUpActivity;
import com.ryj.activities.judges.JudgesActivity;
import com.ryj.activities.news.NewsActivity;
import com.ryj.activities.profiles.advocate.AdvocateActivity;
import com.ryj.activities.profiles.citizen.CitizenActivity;
import com.ryj.activities.profiles.judge.JudgeActivity;
import com.ryj.models.enums.UserType;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andrey on 7/7/17.
 */

public class SignInActivity extends BaseActivity {
  private static final String TAG = "SignInActivity";
  @Inject
  Prefs mPrefs;
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
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
  String mEmailFormatError;
  @BindString(R.string.text_password_char_error)
  String mPasswordFormatError;
  @BindString(R.string.text_sign_in_error)
  String mSignInError;
  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;
  @BindView(R.id.input_layout_password)
  TextInputLayout mPasswordLayout;

  public static void start(Context context) {
    Intent i = new Intent(context, SignInActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signin);
    getComponent().inject(this);
    ButterKnife.bind(this);
    mSignIn.setEnabled(false);
    mPrefs.setIsFirstTutorialLaunch(false);
    mForgotPassword.setPaintFlags(mForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
  }

  @OnClick({R.id.sign_in, R.id.forgot_password, R.id.sign_up, R.id.img_judges, R.id.img_analytics, R.id.img_news, R.id.txt_news, R.id.txt_analytics, R.id.txt_judges})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.sign_in:
        validSignInRequest(mEmail.getText().toString(), mPassword.getText().toString());
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
        NewsActivity.start(this);
        break;
      case R.id.txt_judges:
        JudgesActivity.start(this);
        break;
      case R.id.txt_analytics:
        AnalyticsActivity.start(this);
        break;
      case R.id.txt_news:
        NewsActivity.start(this);
        break;
      default:
        showToast(TAG);
    }
  }

  @OnTextChanged({R.id.email, R.id.password})
  protected void handleTextChange() {
    mSignIn.setEnabled(mEmail.length() > 0 && mPassword.length() > 0);
    mEmailLayout.setErrorEnabled(false);
    mPasswordLayout.setErrorEnabled(false);
  }

  private boolean isAllFieldsValid(String email, String password) {
    return FieldValidation.isEmailFieldValid(email) && FieldValidation.isPasswordFieldValid(password);
  }


  private void validSignInRequest(String email, String password) {
    if (!FieldValidation.isEmailFieldValid(email)) {
      mEmailLayout.setError(mEmailFormatError);
    }
    if (!FieldValidation.isPasswordFieldValid(password)) {
      mPasswordLayout.setError(mPasswordFormatError);
    }
    if (isAllFieldsValid(email, password)) {
      executeSignInRequest(email, password);
    }
  }

  private void executeSignInRequest(String email, String password) {
    mApi.signIn(email, password, Constants.PLATFORM, null)
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    response -> {
                      if (UserType.CITIZEN == response.getType()) {
                        CitizenActivity.start(this);
                      } else if (UserType.LAWYER == response.getType()) {
                        AdvocateActivity.start(this);
                      } else if (UserType.JUDGE == response.getType()) {
                        JudgeActivity.start(this);
                      } else {
                        showToast(TAG);
                      }
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable);
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
