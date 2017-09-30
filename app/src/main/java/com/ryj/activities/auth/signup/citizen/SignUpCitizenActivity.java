package com.ryj.activities.auth.signup.citizen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.models.Account;
import com.ryj.models.Session;
import com.ryj.models.User;
import com.ryj.models.enums.RequestType;
import com.ryj.models.enums.UserType;
import com.ryj.models.request.SignUpQuery;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;
import com.ryj.utils.URLSpanNoUnderline;
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

/** Created by andrey on 7/11/17. */
public class SignUpCitizenActivity extends BaseActivity {
  private static final String TAG = "SignUpCitizenActivity";

  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.terms_link)
  TextView mTerms;

  @BindString(R.string.text_terms_cond_link)
  String mLink;

  @BindView(R.id.fullname)
  EditText mName;

  @BindView(R.id.surname)
  EditText mSurname;

  @BindView(R.id.phone)
  EditText mPhone;

  @BindView(R.id.email)
  EditText mEmail;

  @BindView(R.id.next)
  Button mNext;

  @BindView(R.id.input_layout_fullname)
  TextInputLayout mNameLayout;

  @BindView(R.id.input_layout_surname)
  TextInputLayout mSurnameLayout;

  @BindView(R.id.input_layout_phone)
  TextInputLayout mPhoneLayout;

  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;

  @BindString(R.string.text_name_must_contain_only_letters)
  String mNameValidationError;

  @BindString(R.string.text_surname_must_contain_only_letters)
  String mSurnameValidationError;

  @BindString(R.string.text_email_format_wrong)
  String mEmailValidationError;

  @BindString(R.string.text_name_must_not_be_empty)
  String mNameEmpty;

  @BindString(R.string.text_surname_must_not_be_empty)
  String mSurnameEmpty;

  @BindString(R.string.text_email_must_not_be_empty)
  String mEmailEmpty;

  @BindString(R.string.text_ok)
  String mOk;

  String mError;
  private AlertDialog mDialog;
  private SpinnerDialog mSpinnerDialog;
  private boolean isFirstNameEdited;
  private boolean isLastNameEdited;

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpCitizenActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_citizen);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mTerms.setText(Html.fromHtml(mLink));
    mTerms.setMovementMethod(LinkMovementMethod.getInstance());
    URLSpanNoUnderline.removeUnderlines((Spannable) mTerms.getText());
    mDialog = getDialog(mOk);
    mSpinnerDialog = getSpinnerDialog();
    mSpinnerDialog.setCancelable(false);
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

  @OnClick(R.id.next)
  public void onClick(View view) {
    validateSignUpRequest();
  }

  @OnTextChanged(R.id.fullname)
  protected void handleNameChanged() {
    mNameLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.surname)
  protected void handleSurnameChanged() {
    mSurnameLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.phone)
  protected void handlePhoneChanged() {
    mPhoneLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.email)
  protected void handleEmailChanged() {
    mEmailLayout.setErrorEnabled(false);
  }

  private boolean isNameValid() {
    return FieldValidation.isValid(mName.getText().toString(), StringUtils.ONLY_LETTERS_PATTERN);
  }

  private boolean isSurnameValid() {
    return FieldValidation.isValid(
        mSurname.getText().toString(), StringUtils.LETTERS_SPACE_PATTERN);
  }

  private boolean isPhoneValid() {
    return FieldValidation.isValid(mPhone.getText().toString(), StringUtils.PHONE_PATTERN_UA);
  }

  private boolean isEmailValid() {
    return FieldValidation.isValid(mEmail.getText().toString(), StringUtils.EMAIL_PATTERN);
  }

  private boolean isAllFieldsValid() {
    return isNameValid() && isSurnameValid() && isEmailValid();
  }

  @OnFocusChange({R.id.fullname, R.id.surname, R.id.email})
  public void onFocusChanged(View v, boolean isFocused) {
    switch (v.getId()) {
      case R.id.fullname:
        if (!isFocused && mName.length() > 0 && !isNameValid()) {
          mNameLayout.setError(mNameValidationError);
        }
        break;
      case R.id.surname:
        if (!isFocused && mSurname.length() > 0 && !isSurnameValid()) {
          mSurnameLayout.setError(mSurnameValidationError);
        }
        break;
      case R.id.email:
        if (!isFocused && mEmail.length() > 0 && !isEmailValid()) {
          mEmailLayout.setError(mEmailValidationError);
        }
        break;
    }
  }

  private void validateFields() {
    if (mName.length() > 0 && !isNameValid()) {
      mNameLayout.setError(mNameValidationError);
    } else if (mName.length() == 0) {
      mDialog.setMessage(mNameEmpty);
      mDialog.show();
      return;
    }

    if (mSurname.length() > 0 && !isSurnameValid()) {
      mSurnameLayout.setError(mSurnameValidationError);
    } else if (mSurname.length() == 0) {
      mDialog.setMessage(mSurnameEmpty);
      mDialog.show();
      return;
    }

    if (mEmail.length() > 0 && !isEmailValid()) {
      mEmailLayout.setError(mEmailValidationError);
    } else if (mEmail.length() == 0) {
      mDialog.setMessage(mEmailEmpty);
      mDialog.show();
      return;
    }
  }

  public void validateSignUpRequest() {
    validateFields();
    if (isAllFieldsValid()) {
      User user = new User(mName.getText(), mSurname.getText());
      if (isPhoneValid()) {
        user.setPhone(mPhone.getText());
      }
      user.setType(UserType.CITIZEN);
      Account account = new Account(mEmail.getText());
      Session session = new Session();
      executeSignUpRequest(user, account, session);
    }
  }

  private void executeSignUpRequest(User user, Account account, Session session) {
    mSpinnerDialog.show(getSupportFragmentManager(), StringUtils.EMPTY_STRING);
    mApi.signUp(new SignUpQuery(user, account, session))
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            response -> {
              mSpinnerDialog.dismiss();
              ThankYouActivity.start(this);
              finish();
            },
            throwable -> {
              mSpinnerDialog.dismiss();
              mErrorHandler.handleErrorByRequestType(throwable, this, RequestType.SIGN_UP_TEMP);
            });
  }
}
