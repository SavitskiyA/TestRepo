package com.savitskiy.testsample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andrey on 7/10/17.
 */

public class SignUpActivity extends AppCompatActivity {
  @BindString(R.string.text_signup)
  String mSignUp;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.judge)
  Button mJudge;
  @BindView(R.id.advocate)
  Button mAdvocate;
  @BindView(R.id.citizen)
  Button mCitizen;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @OnClick({R.id.judge, R.id.advocate, R.id.citizen})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.judge:
        JudgeSignUpActivity.start(this);
        break;
      case R.id.advocate:
        Toast.makeText(this, "I'm advocate", Toast.LENGTH_SHORT).show();
        break;
      case R.id.citizen:
        Toast.makeText(this, "I'm citizen", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}
