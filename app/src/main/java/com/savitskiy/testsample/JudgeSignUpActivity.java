package com.savitskiy.testsample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class JudgeSignUpActivity extends AppCompatActivity {
  @BindView(R.id.terms)
  TextView mTerms;
  @BindString(R.string.html)
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
  private boolean mCategorySelected;

  private boolean[] mCheckedItems;
  private ArrayList<Integer> mUserItems = new ArrayList<>();


  public static void start(Context context) {
    Intent i = new Intent(context, JudgeSignUpActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_judge_sign_up);
    ButterKnife.bind(this);

    mTerms.setText(Html.fromHtml(mLink));
    mTerms.setMovementMethod(LinkMovementMethod.getInstance());
    URLSpanNoUnderline.removeUnderlines((Spannable) mTerms.getText());
    CustomSpinnerAdapter categoryAdapter = new CustomSpinnerAdapter(mCategoriesArray, this);
    mCategory.setAdapter(categoryAdapter);
    mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
          mCategorySelected = false;
          mNext.setEnabled(false);
        } else {
          mCategorySelected = true;
          if (isAllEditTextFilledRight()) {
            mNext.setEnabled(true);
          } else {
            mNext.setEnabled(false);
          }
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        mCategorySelected = false;
        mNext.setEnabled(false);
      }
    });

    mCheckedItems = new boolean[mSpecializationsArray.length];
    mSpecialization.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(JudgeSignUpActivity.this);
        builder.setTitle("Choose specialization");
        builder.setMultiChoiceItems(mSpecializationsArray, mCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            if (isChecked) {
              if (!mUserItems.contains(which)) {
                mUserItems.add(which);
              }
            } else if (mUserItems.contains(which)) {
              mUserItems.remove(mUserItems.indexOf(which));
            }
          }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mUserItems.size(); i++) {
              stringBuilder.append(mSpecializationsArray[mUserItems.get(i)]);
              if (i != mUserItems.size() - 1) {
                stringBuilder.append(", ");
              }
            }
            mSpecialization.setText(stringBuilder.toString());
          }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });
        builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            for (int i = 0; i < mCheckedItems.length; i++) {
              mCheckedItems[i] = false;
              mUserItems.clear();
              mSpecialization.setText("");
            }
          }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });
  }

  @OnTextChanged({R.id.name, R.id.surname, R.id.phone, R.id.email, R.id.specialization})
  protected void handleTextChange(Editable editable) {
    if (isAllEditTextFilledRight() && isCategorySelected()) {
      mNext.setEnabled(true);
    } else {
      mNext.setEnabled(false);
    }
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
    return mSpecialization.length() > 0 ? true : false;
  }

  private boolean isCategorySelected() {
    return mCategorySelected;
  }

  private boolean isAllEditTextFilledRight() {
    return isNameValid() && isSurnameValid() && isPhoneValid() && isEmailValid() && isSpecializationValid();
  }

}
