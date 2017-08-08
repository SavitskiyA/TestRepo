package com.ryj.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.adapters.TutorialPagerAdapter;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends BaseActivity {
  private static final String TAG = "TutorialActivity";
  @BindView(R.id.pager_container)
  ViewPager mPager;
  @BindView(R.id.indicator)
  CircleIndicator mIndicator;
  @BindView(R.id.skip)
  TextView mSkip;
  @BindArray(R.array.texts_tutorial)
  String[] mTutorials;
  @BindArray(R.array.tutorial_images)
  TypedArray mImages;

  public static void start(Context context) {
    Intent i = new Intent(context, TutorialActivity.class);
    context.startActivity(i);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tutorial);
    getComponent().inject(this);
    ButterKnife.bind(this);
    mPager.setAdapter(new TutorialPagerAdapter(mTutorials, mImages, this));
    mIndicator.setViewPager(mPager);
  }

  @OnClick(R.id.skip)
  public void onSkipClick() {
    SignInActivity.start(this);
    finish();
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
