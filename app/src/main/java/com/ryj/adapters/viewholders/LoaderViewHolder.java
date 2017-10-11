package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/5/17.
 */

public class LoaderViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.load_frame)
  FrameLayout mFrame;

  @BindView(R.id.load_spinner)
  ImageView mLoadSpinner;

  public LoaderViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);

  }

  public void setLoadSpinnerVisible(int visible) {
    mLoadSpinner.setVisibility(visible);
  }

  public void startAnimation() {
    mLoadSpinner.startAnimation(AnimationUtils.loadAnimation(itemView.getContext(), R.anim.rotation));
  }

  public void stopAnimation() {
    mLoadSpinner.clearAnimation();
  }
}
