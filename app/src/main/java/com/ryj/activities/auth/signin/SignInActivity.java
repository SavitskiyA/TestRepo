package com.ryj.activities.auth.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.analytics.AnalyticsActivity;
import com.ryj.activities.auth.ForgotPasswordActivity;
import com.ryj.activities.auth.signup.SignUpActivity;
import com.ryj.activities.judges.JudgesActivity;
import com.ryj.activities.news.NewsActivity;
import com.ryj.activities.profiles.advocate.AdvocateActivity;
import com.ryj.activities.profiles.citizen.CitizenActivity;
import com.ryj.activities.profiles.judge.JudgeActivity;
import com.ryj.models.enums.UserType;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindColor;
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
  @BindView(R.id.image)
  ImageView mImage;
  @BindView(R.id.email)
  EditText mEmail;
  @BindView(R.id.password)
  EditText mPassword;
  @BindView(R.id.signin)
  Button mSignIn;
  @BindView(R.id.forgotPassword)
  TextView mForgotPassword;
  @BindView(R.id.signup)
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
  @BindString(R.string.text_sign_in)
  String mSignInText;
  @BindString(R.string.text_forgot_password)
  String mForgotPasText;
  @BindString(R.string.text_sign_up)
  String mSignUpText;
  @BindString(R.string.text_email_password_wrong)
  String mEmailPasswordError;
  @BindString(R.string.text_error)
  String mError;
  @BindView(R.id.layout)
  FrameLayout mLayout;
  @BindColor(R.color.colorDarkGradientStart)
  int mColorStart;
  @BindColor(R.color.colorDarkGradientMidOne)
  int mColorMidOne;
  @BindColor(R.color.colorDarkGradientMidTwo)
  int mColorMidTwo;
  @BindColor(R.color.colorDarkGradientEnd)
  int mColorEnd;
  @BindArray(R.array.gradientColors)
  int[] mColors;
  private float[] mPoints = {0, 0.25f, 0.5f, 0.75f, 1};


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
    mPrefs.setIsFirstTutorialLaunch(false);
    mLayout.setBackground(getBackgroundGradient(mColors, mPoints));
  }

  @OnClick({R.id.signin, R.id.forgotPassword, R.id.signup, R.id.img_judges, R.id.img_analytics, R.id.img_news, R.id.txt_news, R.id.txt_analytics, R.id.txt_judges})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.signin:
        sendSignInRequest(mEmail.getText().toString(), mPassword.getText().toString());
        break;
      case R.id.forgotPassword:
        ForgotPasswordActivity.start(this);
        break;
      case R.id.signup:
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
  protected void handleTextChange(Editable editable) {
    if (!FieldValidation.isValid(mEmail.getText().toString(), StringUtils.EMAIL_PATTERN) || mPassword.length() < Constants.MIN_PASSWORD_LENGTH) {
      mSignIn.setEnabled(false);
    } else {
      mSignIn.setEnabled(true);
    }
  }

  private void sendSignInRequest(String email, String password) {
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
                        showToast(mError);
                      }
                    },
                    throwable -> {
                      showToast(mEmailPasswordError);
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
