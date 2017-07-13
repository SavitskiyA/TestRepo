package com.savitskiy.testsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PasswordRecovery extends AppCompatActivity {
  @BindView(R.id.email)
  EditText mEmail;
  @BindView(R.id.send)
  Button mSend;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password_recovery);
    ButterKnife.bind(this);
  }

  @OnTextChanged(R.id.email)
  protected void handleTextChange(Editable editable) {
    String email = mEmail.getText().toString();
    if (!FieldValidation.isValid(email, StringUtils.EMAIL_PATTERN)) {
      mSend.setEnabled(false);
    } else {
      mSend.setEnabled(true);
    }
  }
}
