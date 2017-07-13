package com.savitskiy.testsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFinishActivity extends AppCompatActivity {
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title)
  TextView mTitle;
  @BindView(R.id.ok)
  Button mOk;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up_finish);
    ButterKnife.bind(this);
  }
}
