package com.ryj.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.storage.prefs.Prefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 11/1/17. */
public class EditProfileCitizenActivity extends BaseActivity {
  @Inject Prefs mPrefs;

  @BindView(R.id.email)
  TextView mEmail;

  @BindView(R.id.phone)
  EditText mPhone;

  public static void start(Context context) {
    Intent i = new Intent(context, EditProfileCitizenActivity.class);
    context.startActivity(i);
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

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_edit_citizen);
    getComponent().inject(this);
    setSoftInputMode();
  }

  @OnClick({R.id.cancel, R.id.check})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.check:
        onBackPressed();
        return;

      case R.id.back:
        onBackPressed();
        return;
    }
  }
}
