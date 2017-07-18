package com.ryj.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ryj.App;
import com.ryj.di.ApplicationComponent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

  public ApplicationComponent getComponent() {
    return App.get().getComponent();
  }

  @Nullable
  protected abstract Toolbar getToolbar();

  @Nullable
  protected abstract TextView getToolbarTitle();

  public void setToolbarBackArrowEnabled(boolean enabled) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
      getSupportActionBar().setDisplayShowHomeEnabled(enabled);
    }
  }

  public void setDefaultDisplayShowTitleEnabled(boolean enabled) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(enabled);
    }
  }

  public void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
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
}
