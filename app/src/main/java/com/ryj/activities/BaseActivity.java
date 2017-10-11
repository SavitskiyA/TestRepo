package com.ryj.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ryj.App;
import com.ryj.R;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.di.ApplicationComponent;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.models.events.SignOutEvent;
import com.ryj.models.events.UnauthorizedEvent;
import com.ryj.rx.RxBus;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.ToastUtil;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindString;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends RxAppCompatActivity {
  @Inject Api mApi;
  @Inject Prefs mPrefs;
  @Inject ErrorHandler mErrorHandler;

  @BindString(R.string.text_not_authorized)
  String mUnauthorized;

  @BindString(R.string.text_ok)
  String mOk;

  @BindString(R.string.text_cancel)
  String mCancel;

  public ApplicationComponent getComponent() {
    return App.get().getComponent();
  }

  public AlertDialog getDialog(CharSequence neutralButtonTitle) {
    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setButton(
        AlertDialog.BUTTON_NEUTRAL,
        neutralButtonTitle,
        (dialog, which) -> {
          dialog.dismiss();
        });
    return alertDialog;
  }

  public SpinnerDialog getSpinnerDialog() {
    return new SpinnerDialog();
  }

  public AlertDialog getDialog(CharSequence negativeButtonTitle, CharSequence positiveButtonTitle) {
    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setButton(
        AlertDialog.BUTTON_NEUTRAL,
        negativeButtonTitle,
        (dialog, which) -> {
          alertDialog.dismiss();
        });
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE,
        positiveButtonTitle,
        (dialog, which) -> {
          logout();
        });
    return alertDialog;
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

  public void setSoftInputMode() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
  }

  public void hideStatusBar() {
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    subscribeOnEvent();
  }

  protected void subscribeOnEvent() {
    RxBus.receiveEvent()
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .filter(event -> event instanceof UnauthorizedEvent)
        .subscribe(
            event -> {
              AlertDialog dialog = getDialog(mOk);
              dialog.setMessage(((UnauthorizedEvent) event).getMessage());
              dialog.show();
            },
            throwable -> {
              mErrorHandler.handleError(throwable, this);
              ToastUtil.show(throwable.getMessage(), false);
            });
    RxBus.receiveEvent()
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .filter(event -> event instanceof SignOutEvent)
        .subscribe(
            event -> {
              logout();
            },
            throwable -> {
              mErrorHandler.handleError(throwable, this);
              ToastUtil.show(throwable.getMessage(), false);
            });
  }

  protected void logout() {
    mPrefs.clear();
    SignInActivity.startWithEmptyStack(this);
  }

  public void replaceFragment(
      Fragment fragment, int resContainer, boolean stack, boolean animate, String tag) {

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    if (animate) {
      transaction.setCustomAnimations(
          R.anim.slide_in_right,
          R.anim.slide_out_left,
          R.anim.slide_in_left,
          R.anim.slide_out_right);
    }

    transaction.replace(resContainer, fragment, tag);
    if (stack) {
      transaction.addToBackStack(fragment.getClass().getSimpleName());
    }
    transaction.commitAllowingStateLoss();
  }

  public int getFragmentsBackStackSize() {
    return getSupportFragmentManager().getBackStackEntryCount();
  }
}
