package com.ryj.activities.auth.signup.judge;

import android.app.Activity;
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
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.models.Account;
import com.ryj.models.Session;
import com.ryj.models.enums.Affairs;
import com.ryj.models.enums.RequestType;
import com.ryj.models.enums.UserType;
import com.ryj.models.request.SignUpQuery;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.RxUtils;
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
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/** Created by andrey on 7/11/17. */
public class SignUpJudgeActivity extends BaseActivity {
  private static final String TAG = "SignUpJudgeActivity";
  private static String EXTRA_LIST_STRING = "list_string";
  private static String EXTRA_LIST_BOOLEANS = "list_booleans";
  private static int REQUEST_CODE = 1;
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject SignUpQuery mQuery;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.terms_link)
  TextView mTerms;

  @BindString(R.string.text_terms_cond_link)
  String mLink;

  @BindArray(R.array.text_categories_client)
  String[] mCategories;

  @BindView(R.id.category)
  EditText mCategory;

  @BindView(R.id.fullname)
  EditText mFullName;

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

  @BindView(R.id.input_layout_fullname)
  TextInputLayout mFullNameLayout;

  @BindView(R.id.input_layout_category)
  TextInputLayout mCategoryLayout;

  @BindView(R.id.input_layout_phone)
  TextInputLayout mPhoneLayout;

  @BindView(R.id.input_layout_email)
  TextInputLayout mEmailLayout;

  @BindString(R.string.text_name_must_contain_only_letters)
  String mFullNameValidationError;

  @BindString(R.string.text_category_must_be_selected)
  String mCategoryValidationError;

  @BindString(R.string.text_email_of_judge_must_end)
  String mEmailValidationError;

  @BindString(R.string.text_name_must_not_be_empty)
  String mFullNameEmpty;

  @BindString(R.string.text_category_must_not_be_empty)
  String mCategoryEmpty;

  @BindString(R.string.text_email_must_not_be_empty)
  String mEmailEmpty;

  private AlertDialog mDialog;
  private SpinnerDialog mSpinnerDialog;
  private boolean[] mChoosenCategoriesBooleans;
  private Affairs[] mAffairs = {
    Affairs.ADMIN_VIOLATION,
    Affairs.ADMINISTRATIVE,
    Affairs.CIVIL,
    Affairs.CRIMINAL,
    Affairs.ECONOMIC,
    Affairs.URGENT_LAWSUITS
  };

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpJudgeActivity.class);
    context.startActivity(i);
  }

  public static void sendActivityResult(
      Activity activity, String choosenCategories, boolean[] choosenCategoriesBooleans) {
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
    mDialog = getDialog(mOk);
    mSpinnerDialog = getSpinnerDialog();
    mSpinnerDialog.setCancelable(false);
    mFullName.setText(StringUtils.getFullName(mQuery.getUser()));
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
        SignUpListActivity.startForResult(
            this, mCategoriesListTitle, mCategories, mChoosenCategoriesBooleans, REQUEST_CODE);
        break;
      case R.id.next:
        createSignUpRequest();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE && data != null) {
      mCategory.setText(data.getStringExtra(EXTRA_LIST_STRING));
      mChoosenCategoriesBooleans = data.getBooleanArrayExtra(EXTRA_LIST_BOOLEANS);
    }
  }

  @OnTextChanged(R.id.fullname)
  protected void handleNameChanged() {
    mFullNameLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.category)
  protected void handleCategoryChanged() {
    mCategoryLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.email)
  protected void handleEmailChanged() {
    mEmailLayout.setErrorEnabled(false);
  }

  @OnTextChanged(R.id.phone)
  protected void handlePhoneChanged() {
    mPhoneLayout.setErrorEnabled(false);
  }

  private boolean isFullNameValid() {
    return mFullName.length() > 0;
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
    return isFullNameValid() && isEmailValid() && isCategoryValid();
  }

  @OnFocusChange({R.id.email, R.id.category})
  public void onFocusChanged(View v, boolean isFocused) {
    switch (v.getId()) {
      case R.id.email:
        if (!isFocused && mEmail.length() > 0 && !isEmailValid()) {
          mEmailLayout.setError(mEmailValidationError);
        }
        break;
      case R.id.category:
        if (isFocused) {
          SignUpListActivity.startForResult(
              this, mCategoriesListTitle, mCategories, mChoosenCategoriesBooleans, REQUEST_CODE);
        }
        break;
    }
  }

  private void validateFields() {
    if (mFullName.length() > 0 && !isFullNameValid()) {
      mFullNameLayout.setError(mFullNameValidationError);
    } else if (mFullName.length() == 0) {
      mDialog.setMessage(mFullNameEmpty);
      mDialog.show();
      return;
    }

    if (mCategory.length() > 0 && !isCategoryValid()) {
      mCategoryLayout.setError(mCategoryValidationError);
    } else if (mCategory.length() == 0) {
      mDialog.setMessage(mCategoryEmpty);
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

  public void createSignUpRequest() {
    validateFields();
    if (isAllFieldsValid()) {
      if (isPhoneValid()) {
        mQuery.getUser().setPhone(mPhone.getText());
      }
      mQuery.getUser().setType(UserType.JUDGE);
      mQuery.getUser().setAffairs(getChoosenCategoriesServer());
      mQuery.setAccount(new Account(mEmail.getText()));
      mQuery.setSession(new Session());
      executeSignUpRequest(mQuery);
    }
  }

  private void executeSignUpRequest(SignUpQuery query) {
    mApi.signUp(query)
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .compose(RxUtils.applySchedulers())
        .compose(
            RxUtils.applyBeforeAndAfter(
                (disposable) ->
                    mSpinnerDialog.show(getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                () -> mSpinnerDialog.dismiss()))
        .subscribe(
            response -> {
              ThankYouActivity.start(this);
              finish();
            },
            throwable -> {
              mErrorHandler.handleErrorByRequestType(throwable, this, RequestType.SIGN_UP_TEMP);
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
