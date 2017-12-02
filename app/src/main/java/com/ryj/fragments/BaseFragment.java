package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ryj.App;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.SwitchActivity;
import com.ryj.di.ApplicationComponent;
import com.ryj.dialogs.SpinnerDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends RxFragment {
  protected CompositeDisposable mDisposable = new CompositeDisposable();
  private Unbinder mButterKnifeUnbinder;

  protected static SpinnerDialog getSpinnerDialog() {
    return BaseActivity.getSpinnerDialog();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mButterKnifeUnbinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mButterKnifeUnbinder.unbind();
  }

  protected View bindView(View view) {
    ButterKnife.bind(this, view);
    return view;
  }

  private SwitchActivity getSwitchActivity() {
    if (this.getActivity() != null && this.getActivity() instanceof SwitchActivity) {
      return ((SwitchActivity) this.getActivity());
    } else {
      throw new RuntimeException("Activity instance is null or it is not instance of Switchable");
    }
  }

  private BaseActivity getBaseActivity() {
    if (this.getActivity() != null && this.getActivity() instanceof BaseActivity) {
      return ((BaseActivity) this.getActivity());
    } else {
      throw new RuntimeException("Activity instance is null or it is not instance of BaseActivity");
    }
  }

  protected void switchActivityTab(int tagId, boolean isChecked) {
    getSwitchActivity().switchTab(tagId, isChecked);
  }

  protected void replaceFragment(
      Fragment fragment, int resContainer, boolean stack, boolean animate, String tag) {
    getBaseActivity().replaceFragment(fragment, resContainer, stack, animate, tag);
  }

  protected ApplicationComponent getComponent() {
    return App.get().getComponent();
  }

  protected void setCurrentFragmentTag(String tag) {
    getSwitchActivity().setCurrentFragmentTag(tag);
  }

  protected void setToolbarBackArrowEnabled(boolean enabled) {
    getSwitchActivity().setToolbarBackArrowEnabled(enabled);
  }

  protected void setToolbar(Toolbar toolbar) {
    getSwitchActivity().setSupportActionBar(toolbar);
  }

  protected void onBackPressed() {
    getBaseActivity().onBackPressed();
  }

  protected void doRequest(Disposable disposable) {
    if (mDisposable != null) {
      mDisposable.add(disposable);
    } else {
      mDisposable = new CompositeDisposable();
      mDisposable.add(disposable);
    }
  }

  @Override
  public void onPause() {
    mDisposable.clear();
    super.onPause();
  }
}
