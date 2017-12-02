package com.ryj.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  private static final String TAG = "MainActivity";

  @BindView(R.id.helloTitle)
  TextView mHelloTitle;

  public static void start(Context context) {
    Intent i = new Intent(context, MainActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getComponent().inject(this);
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
