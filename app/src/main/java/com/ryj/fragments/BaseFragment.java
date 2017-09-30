package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ryj.App;
import com.ryj.activities.BaseActivity;
import com.ryj.di.ApplicationComponent;
import com.ryj.listeners.Switchable;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {
  private Unbinder mButterKnifeUnbinder;

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

  private Switchable getSwitchable() {
    if (this.getActivity() != null && this.getActivity() instanceof Switchable) {
      return ((Switchable) this.getActivity());
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

  protected void setActivityToolBarTitle(String title) {
    getSwitchable().setToolBarTitle(title);
  }

  protected void switchActivityTab(int tagId, boolean isChecked) {
    getSwitchable().switchTab(tagId, isChecked);
  }

  protected void setActivityToolbarVisibility(int visible) {
    getSwitchable().setToolbarVisibility(visible);
  }

  protected void setActivityOptionsMenuVisibility(boolean isVisible) {
    getSwitchable().setOptionsMenuVisibility(isVisible);
  }

  protected void replaceFragment(
      Fragment fragment, int resContainer, boolean stack, boolean animate, String tag) {
    getBaseActivity().replaceFragment(fragment, resContainer, stack, animate, tag);
  }

  protected ApplicationComponent getComponent() {
    return App.get().getComponent();
  }
}
