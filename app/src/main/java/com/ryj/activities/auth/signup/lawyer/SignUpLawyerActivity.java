package com.ryj.activities.auth.signup.lawyer;

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
import com.ryj.activities.auth.signup.SignUpListActivity;
import com.ryj.models.Account;
import com.ryj.models.Session;
import com.ryj.models.User;
import com.ryj.models.enums.Specializations;
import com.ryj.models.enums.UserType;
import com.ryj.models.filters.Items;
import com.ryj.models.request.SignUpQuery;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;
import com.ryj.utils.URLSpanNoUnderline;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by andrey on 8/10/17.
 */
public class SignUpLawyerActivity extends BaseActivity {
  @Inject
  SignUpQuery mQuery;
  @Inject
  Items mItems;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.terms_link)
  TextView mTerms;

  @BindString(R.string.text_terms_cond_link)
  String mLink;

  @BindArray(R.array.text_specializations_client)
  String[] mSpecializationsClient;

  @BindView(R.id.certificate_number)
  EditText mCertificateNumber;

  @BindView(R.id.fullname)
  EditText mName;

  @BindView(R.id.surname)
  EditText mSurname;

  @BindView(R.id.specializations)
  EditText mSpecialization;

  @BindView(R.id.company)
  EditText mCompany;

  @BindView(R.id.phone)
  EditText mPhone;

  @BindView(R.id.email)
  EditText mEmail;

  @BindView(R.id.input_layout_certificate_number)
  TextInputLayout mCertificateNumberLayout;

  @BindView(R.id.input_layout_fullname)
  TextInputLayout mNameLayout;

  @BindView(R.id.input_layout_surname)
  TextInputLayout mSurnameLayout;

  @BindView(R.id.input_layout_phone)
  TextInputLayout mPhoneLayout;

  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;

  @BindView(R.id.input_layout_company)
  TextInputLayout mCompanyLayout;

  @BindView(R.id.input_layout_specializations)
  TextInputLayout mSpecializationLayout;

  @BindString(R.string.text_specialization)
  String mSpecializationsListTitle;

  @BindString(R.string.text_name_must_contain_only_letters)
  String mNameValidationError;

  @BindString(R.string.text_surname_must_contain_only_letters)
  String mSurnameValidationError;

  @BindString(R.string.text_phone_must_start_380)
  String mPhoneValidationError;

  @BindString(R.string.text_email_format_wrong)
  String mEmailValidationError;

  @BindString(R.string.text_certificate_number_must_contain)
  String mCertificationNumberValidationError;

  @BindString(R.string.text_company_must_contain_only_letters)
  String mCompanyValidationError;

  @BindString(R.string.text_specialization_must_be_selected)
  String mSpecializationValidationError;

  @BindString(R.string.text_ok)
  String mOk;

  @BindString(R.string.text_certificate_number_must_not_be_empty)
  String mCertificateNumberEmpty;

  @BindString(R.string.text_name_must_not_be_empty)
  String mNameEmpty;

  @BindString(R.string.text_surname_must_not_be_empty)
  String mSurnameEmpty;

  @BindString(R.string.text_specialization_must_not_be_empty)
  String mSpecializationEmpty;

  @BindString(R.string.text_phone_must_not_be_empty)
  String mPhoneEmpty;

  @BindString(R.string.text_email_must_not_be_empty)
  String mEmailEmpty;

  @BindString(R.string.text_error)
  String mError;

  @BindView(R.id.next)
  Button mNext;

  private AlertDialog mDialog;
  private Specializations[] mSpecializationsServer = {
          Specializations.ADMINISTRATIVE,
          Specializations.CIVIL,
          Specializations.CRIMINAL,
          Specializations.ECONOMIC,
          Specializations.LEGAL
  };

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpLawyerActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_lawyer);
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
    mItems.init(Arrays.asList(mSpecializationsClient), Arrays.asList(mSpecializationsServer));
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mItems.getResultClient() != null) {
      mSpecialization.setText(mItems.getResultClient());
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

  @OnClick({R.id.specializations, R.id.next})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.specializations:
        SignUpListActivity.start(this, mSpecializationsListTitle);
        break;
      case R.id.next:
        validateSignUpRequest();
    }
  }

  @OnTextChanged(R.id.certificate_number)
  protected void handleCertificationNumberChanged() {
    mCertificateNumberLayout.setErrorEnabled(false);
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

  @OnTextChanged(R.id.company)
  protected void handleCompanyChanged() {
    mCompanyLayout.setErrorEnabled(false);
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

  private boolean isCertificateNumberValid() {
    return FieldValidation.isValid(
            mCertificateNumber.getText().toString(), StringUtils.ONLY_DIGITS_PATTERN);
  }

  private boolean isCompanyValid() {
    return FieldValidation.isValid(
            mCompany.getText().toString(), StringUtils.LETTERS_DIGITS_PATTERN);
  }

  private boolean isSpecializationValid() {
    return mSpecialization.length() > 0;
  }

  private boolean isAllFieldsValid() {
    return isNameValid()
            && isSurnameValid()
            && isPhoneValid()
            && isEmailValid()
            && isCertificateNumberValid()
            && isCompanyValid();
  }

  @OnFocusChange({
          R.id.certificate_number,
          R.id.fullname,
          R.id.surname,
          R.id.email,
          R.id.phone,
          R.id.company,
          R.id.specializations
  })
  public void onFocusChanged(View v, boolean isFocused) {
    switch (v.getId()) {
      case R.id.certificate_number:
        if (!isFocused && mCertificateNumber.length() > 0 && !isCertificateNumberValid()) {
          mCertificateNumberLayout.setError(mCertificationNumberValidationError);
        }
        break;
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
      case R.id.phone:
        if (!isFocused && mPhone.length() > 0 && !isPhoneValid()) {
          mPhoneLayout.setError(mPhoneValidationError);
        }
        break;
      case R.id.company:
        if (!isFocused && mCompany.length() > 0 && !isCompanyValid()) {
          mCompanyLayout.setError(mCompanyValidationError);
        }
        break;
      case R.id.specializations:
        if (!isFocused && mSpecialization.length() > 0 && !isSpecializationValid()) {
          mSpecializationLayout.setError(mSpecializationValidationError);
        }
        break;
    }
  }

  private void validateFields() {
    if (mCertificateNumber.length() > 0 && !isCertificateNumberValid()) {
      mCertificateNumberLayout.setError(mCertificationNumberValidationError);
    } else if (mCertificateNumber.length() == 0) {
      mDialog.setMessage(mCertificateNumberEmpty);
      mDialog.show();
      return;
    }
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

    if (mPhone.length() > 0 && !isPhoneValid()) {
      mPhoneLayout.setError(mPhoneValidationError);
    } else if (mPhone.length() == 0) {
      mDialog.setMessage(mPhoneEmpty);
      mDialog.show();
      return;
    }

    if (!isCompanyValid()) {
      mCompanyLayout.setError(mCompanyValidationError);
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
      user.setPhone(mPhone.getText());
      user.setType(UserType.LAWYER);
      user.setSpecializations(mItems.getResultServer());
      user.setCertNumber(mCertificateNumber.getText());
      if (mCompany.length() > 0) {
        user.setCompany(mCompany.getText());
      }
      Account account = new Account(mEmail.getText());
      Session session = new Session();
      mQuery.setUser(user);
      mQuery.setAccount(account);
      mQuery.setSession(session);
      SignUpLawyerAvatarActivity.start(this);
    }
  }
}
