package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ryj.R;
import com.ryj.listeners.OnHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/18/17. */
public class AreaHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.region)
  RadioButton mArea;

  @BindView(R.id.underline)
  ImageView mUnderline;

  private OnHolderListener mHolderCheckedListener;

  public AreaHolder(View itemView, OnHolderListener holderCheckedListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mHolderCheckedListener = holderCheckedListener;
  }

  public void setArea(String area) {
    mArea.setText(area);
  }

  public void setUnderlineVisible() {
    mUnderline.setVisibility(View.VISIBLE);
  }

  public void setTag(int tag) {
    mArea.setTag(tag);
  }

  @OnClick(R.id.region)
  void onClick() {
    mArea.setChecked(true);
    mHolderCheckedListener.onHolderCheckedChange(true, (Integer) mArea.getTag());
  }

  public void setChecked(boolean isChecked) {
    mArea.setChecked(isChecked);
  }
}
