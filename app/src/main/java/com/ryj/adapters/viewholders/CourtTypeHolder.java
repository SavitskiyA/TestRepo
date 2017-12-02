package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.RadioButton;

import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/29/17. */
public class CourtTypeHolder extends BaseViewHolder {
  @BindView(R.id.region)
  RadioButton mCourt;

  private OnHolderClickListener mOnHolderClickListener;

  public CourtTypeHolder(
      View itemView, OnHolderClickListener onHolderClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mOnHolderClickListener = onHolderClickListener;
  }

  public void setCourtType(String area) {
    mCourt.setText(area);
  }

  @OnClick(R.id.region)
  void onClick() {
    mOnHolderClickListener.onHolderClick(mCourt.isChecked(), getTag());
  }

  public void setChecked(boolean isChecked) {
    mCourt.setChecked(isChecked);
  }
}
