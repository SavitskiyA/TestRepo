package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.ryj.R;
import com.ryj.listeners.OnHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/29/17. */
public class CourtTypeHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.option)
  RadioButton mCourt;

  private OnHolderListener mHolderCheckedListener;

  public CourtTypeHolder(View itemView, OnHolderListener holderCheckedListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mHolderCheckedListener = holderCheckedListener;
  }

  public void setCourtType(String area) {
    mCourt.setText(area);
  }

  public void setTag(int tag) {
    mCourt.setTag(tag);
  }

  @OnClick(R.id.option)
  void onClick() {
    mHolderCheckedListener.onHolderCheckedChange(mCourt.isChecked(), (Integer) mCourt.getTag());
  }

  public void setChecked(boolean isChecked) {
    mCourt.setChecked(isChecked);
  }
}
