package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ryj.App;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.SwitchActivity;
import com.ryj.di.ApplicationComponent;
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

  protected void setActivityToolBarTitle(String title) {
    getSwitchActivity().setToolBarTitle(title);
  }

  protected void switchActivityTab(int tagId, boolean isChecked) {
    getSwitchActivity().switchTab(tagId, isChecked);
  }

  protected void setActivityToolbarVisibility(int visible) {
    getSwitchActivity().setToolbarVisibility(visible);
  }

  protected void setActivityOptionsMenuVisibility(boolean isVisible) {
    getSwitchActivity().setOptionsMenuVisibility(isVisible);
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
}
