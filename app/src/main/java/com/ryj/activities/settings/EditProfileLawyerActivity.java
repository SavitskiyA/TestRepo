package com.ryj.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 11/1/17.
 */

public class EditProfileLawyerActivity extends BaseActivity {
  @Inject
  Prefs mPrefs;

  @BindView(R.id.toolbar)
  Toolbar mToolBar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.email)
  TextView mEmail;

  @BindView(R.id.phone)
  EditText mPhone;

  @BindView(R.id.specializations)
  EditText mSpecializations;

  public static void start(Context context) {
    Intent i = new Intent(context, EditProfileLawyerActivity.class);
    context.startActivity(i);
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolBar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile_lawyer);
    getComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(mToolBar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    setSoftInputMode();
  }
}
