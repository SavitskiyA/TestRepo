package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/18/17. */
public class RegionHolder extends BaseViewHolder {
  @BindView(R.id.region)
  RadioButton mArea;

  @BindView(R.id.underline)
  ImageView mUnderline;

  private OnHolderClickListener mOnHolderClickListener;

  public RegionHolder(View itemView, OnHolderClickListener onHolderListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mOnHolderClickListener = onHolderListener;
  }

  public void setArea(String area) {
    mArea.setText(area);
  }

  public void setUnderlineVisible() {
    mUnderline.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.region)
  void onClick() {
    mArea.setChecked(true);
    mOnHolderClickListener.onHolderClick(true, getTag());
  }

  public void setChecked(boolean isChecked) {
    mArea.setChecked(isChecked);
  }
}
