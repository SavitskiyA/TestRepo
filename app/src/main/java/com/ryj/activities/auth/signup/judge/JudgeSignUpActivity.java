package com.ryj.activities.auth.signup.judge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.models.Account;
import com.ryj.models.Session;
import com.ryj.models.User;
import com.ryj.models.enums.Affairs;
import com.ryj.models.enums.UserType;
import com.ryj.models.request.SignUpQuery;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.StringUtils;
import com.ryj.utils.URLSpanNoUnderline;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andrey on 7/11/17.
 */

public class JudgeSignUpActivity extends BaseActivity {
  private static final String TAG = "SignUpJudgeActivity";
  private static String EXTRA_LIST_STRING = "list_string";
  private static String EXTRA_LIST_BOOLEANS = "list_booleans";
  private static int REQUEST_CODE = 1;
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.terms_link)
  TextView mTerms;
  @BindString(R.string.text_terms_cond_link)
  String mLink;
  @BindArray(R.array.categories)
  String[] mCategories;
  @BindView(R.id.category)
  EditText mCategory;
  @BindView(R.id.name)
  EditText mName;
  @BindView(R.id.surname)
  EditText mSurname;
  @BindView(R.id.phone)
  EditText mPhone;
  @BindView(R.id.email)
  EditText mEmail;
  @BindView(R.id.next)
  Button mNext;
  @BindString(R.string.text_ok)
  String mOk;
  @BindString(R.string.text_category_of_cases)
  String mCategoriesListTitle;
  @BindView(R.id.input_layout_name)
  TextInputLayout mNameLayout;
  @BindView(R.id.input_layout_surname)
  TextInputLayout mSurnameLayout;
  @BindView(R.id.input_layout_category)
  TextInputLayout mCategoryLayout;
  @BindView(R.id.input_layout_phone)
  TextInputLayout mPhoneLayout;
  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;
  @BindString(R.string.text_name_must_contain_only_letters)
  String mNameValidationError;
  @BindString(R.string.text_surname_must_contain_only_letters)
  String mSurnameValidationError;
  @BindString(R.string.text_category_must_be_selected)
  String mCategoryValidationError;
  @BindString(R.string.text_phone_must_start_380)
  String mPhoneValidationError;
  @BindString(R.string.text_email_of_judge_must_end)
  String mEmailValidationError;

  private boolean[] mChoosenCategoriesBooleans;
  private Affairs[] mAffairs = {Affairs.ADMIN, Affairs.ADMINISTRATIVE, Affairs.CIVIL, Affairs.CRIMINAL, Affairs.ECONOMIC, Affairs.URGENT};

  public static void start(Context context) {
    Intent i = new Intent(context, JudgeSignUpActivity.class);
    context.startActivity(i);
  }

  public static void sendActivityResult(Activity activity, String choosenCategories, boolean[] choosenCategoriesBooleans) {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_LIST_STRING, choosenCategories);
    intent.putExtra(EXTRA_LIST_BOOLEANS, choosenCategoriesBooleans);
    activity.setResult(RESULT_OK, intent);
    activity.onBackPressed();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_judge);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
    mTerms.setText(Html.fromHtml(mLink));
    mTerms.setMovementMethod(LinkMovementMethod.getInstance());
    URLSpanNoUnderline.removeUnderlines((Spannable) mTerms.getText());
    mChoosenCategoriesBooleans = new boolean[mCategories.length];
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

  @OnClick({R.id.category, R.id.next})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.category:
        SignUpListActivity.startForResult(this, mCategoriesListTitle, mCategories, mChoosenCategoriesBooleans, REQUEST_CODE);
        break;
      case R.id.next:
        validateSignUpRequest();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE && data != null) {
      mCategory.setText(data.getStringExtra(EXTRA_LIST_STRING));
      mChoosenCategoriesBooleans = data.getBooleanArrayExtra(EXTRA_LIST_BOOLEANS);
    }
  }

  @OnTextChanged({R.id.name, R.id.surname, R.id.phone, R.id.email, R.id.category})
  protected void handleTextChange() {
    mNext.setEnabled(isAllFieldsNotEmpty());
  }

  @OnTextChanged(R.id.name)
  protected void handleNameChanged() {
    mNameLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.surname)
  protected void handleSurnameChanged() {
    mSurnameLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.category)
  protected void handleCategoryChanged() {
    mCategoryLayout.setErrorEnabled(false);
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
    return FieldValidation.isValid(mSurname.getText().toString(), StringUtils.LETTERS_SPACE_PATTERN);
  }

  private boolean isPhoneValid() {
    return FieldValidation.isValid(mPhone.getText().toString(), StringUtils.PHONE_PATTERN_UA);
  }

  private boolean isEmailValid() {
    return FieldValidation.isValid(mEmail.getText().toString(), StringUtils.EMAIL_PATTERN_JUDGE);
  }

  private boolean isCategoryValid() {
    return mCategory.length() > 0;
  }

  private boolean isAllFieldsValid() {
    return isNameValid() && isSurnameValid() && isEmailValid() && isCategoryValid() && isPhoneValid();
  }

  private boolean isAllFieldsNotEmpty() {
    return mName.length() > 0 && mSurname.length() > 0 && mCategory.length() > 0 && mEmail.length() > 0;
  }

  public void validateSignUpRequest() {
    if (!isNameValid()) {
      mNameLayout.setError(mNameValidationError);
    }
    if (!isSurnameValid()) {
      mSurnameLayout.setError(mSurnameValidationError);
    }
    if (!isCategoryValid()) {
      mCategoryLayout.setError(mCategoryValidationError);
    }
    if (!isPhoneValid()) {
      mPhoneLayout.setError(mPhoneValidationError);
    }
    if (!isEmailValid()) {
      mEmailLayout.setError(mEmailValidationError);
    }
    if (isAllFieldsValid()) {
      User user = new User(mName.getText(), mSurname.getText());
      if (mPhone.length() > 0) {
        user.setPhone(mPhone.getText());
      }
      user.setType(UserType.JUDGE);
      user.setAffairs(getChoosenCategoriesServer());
      Account account = new Account(mEmail.getText());
      Session session = new Session();
      executeSignUpRequest(user, account, session);
    }
  }

  private void executeSignUpRequest(User user, Account account, Session session) {
    mApi.signUp(new SignUpQuery(user, account, session))
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    response -> {
                      ThankYouActivity.start(this);
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable);
                    });
  }

  private List<Affairs> getChoosenCategoriesServer() {
    List<Affairs> choosenCategoriesServerList = new ArrayList<>();
    for (int i = 0; i < mChoosenCategoriesBooleans.length; i++) {
      if (mChoosenCategoriesBooleans[i]) {
        choosenCategoriesServerList.add(mAffairs[i]);
      }
    }
    return choosenCategoriesServerList;
  }
}
