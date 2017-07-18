package com.ryj.activities.auth.signup.judge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.adapters.CategorySpinnerAdapter;
import com.ryj.eventbus.CategoryEvent;
import com.ryj.fragments.SpecializationsDialog;
import com.ryj.utils.FieldValidation;
import com.ryj.utils.URLSpanNoUnderline;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

/**
 * Created by andrey on 7/11/17.
 */

public class JudgeSignUpActivity extends BaseActivity {

  @Inject
  EventBus mBus;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.terms_link)
  TextView mTerms;
  @BindString(R.string.text_terms_cond_link)
  String mLink;
  @BindArray(R.array.array_categories)
  String[] mCategoriesArray;
  @BindArray(R.array.array_specializations)
  String[] mSpecializationsArray;
  @BindView(R.id.categories)
  Spinner mCategory;
  @BindView(R.id.specialization)
  EditText mSpecialization;
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
  @BindString(R.string.text_specialization_hint)
  String mSpecializationHint;
  @BindString(R.string.text_btn_cancel_title)
  String mCancel;
  @BindString(R.string.text_btn_clear_title)
  String mClear;
  @BindString(R.string.text_btn_save_title)
  String mSave;
  @BindString(R.string.text_ok)
  String mOk;
  private SpecializationsDialog mDialog;

  public static void start(Context context) {
    Intent i = new Intent(context, JudgeSignUpActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_judge);
    getComponent().inject(this);
    ButterKnife.bind(this);
    mBus.register(this);
    setSupportActionBar(mToolbar);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    mTerms.setText(Html.fromHtml(mLink));
    mTerms.setMovementMethod(LinkMovementMethod.getInstance());
    URLSpanNoUnderline.removeUnderlines((Spannable) mTerms.getText());
    CategorySpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(mCategoriesArray, this);
    mCategory.setAdapter(categoryAdapter);
    mDialog = new SpecializationsDialog(this, mSpecializationHint, mSpecializationsArray);
    mDialog.setCancelable(false);
    mDialog.enableMultiChoiceItemsListener();
    mDialog.enablePositiveButton(mSave);
    mDialog.enableNegativeButton(mCancel);
    mDialog.enableNeutralButton(mClear);
  }

  @OnItemSelected(R.id.categories)
  public void onItemSelected() {
    mNext.setEnabled(isAllFieldsValid() && isCategorySelected());
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

  @OnClick({R.id.specialization, R.id.next})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.specialization:
        mDialog.show();
        break;
      case R.id.next:
        ThankYouActivity.start(this);
    }
  }

  @OnTextChanged({R.id.name, R.id.surname, R.id.phone, R.id.email, R.id.specialization})
  protected void handleTextChange(Editable editable) {
    mNext.setEnabled(isAllFieldsValid() && isCategorySelected());
  }

  private boolean isNameValid() {
    return FieldValidation.isValid(mName.getText().toString(), Constants.ONLY_LETTERS_PATTERN);
  }

  private boolean isSurnameValid() {
    return FieldValidation.isValid(mSurname.getText().toString(), Constants.ONLY_LETTERS_PATTERN);
  }

  private boolean isPhoneValid() {
    return FieldValidation.isValid(mPhone.getText().toString(), Constants.PHONE_PATTERN_UA);
  }

  private boolean isEmailValid() {
    return FieldValidation.isValid(mEmail.getText().toString(), Constants.EMAIL_PATTERN_JUDGE);
  }

  private boolean isSpecializationValid() {
    return mSpecialization.length() > 0;
  }

  private boolean isCategorySelected() {
    return mCategory.getSelectedItemPosition() > 0;
  }

  private boolean isAllFieldsValid() {
    return isNameValid() && isSurnameValid() && isPhoneValid() && isEmailValid() && isSpecializationValid();
  }

  @Subscribe
  public void onEvent(CategoryEvent event) {
    mSpecialization.setText(event.getString());
  }
}
