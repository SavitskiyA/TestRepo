package com.ryj.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 9/27/17.
 */

public class SpinnerDialog extends DialogFragment {
  @BindView(R.id.load_spinner)
  ImageView mSpinner;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_spinner, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    mSpinner.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotation));
  }

  @Override
  public void onPause() {
    super.onPause();
    mSpinner.clearAnimation();
  }
}
