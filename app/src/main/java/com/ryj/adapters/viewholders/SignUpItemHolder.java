package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.ryj.R;
import com.ryj.listeners.OnHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by andrey on 7/22/17.
 */

public class SignUpItemHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.item)
  CheckBox mItem;
  private OnHolderListener mHolderCheckedListener;

  public SignUpItemHolder(View itemView, OnHolderListener holderCheckedListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.mHolderCheckedListener = holderCheckedListener;
  }

  public String getText() {
    return mItem.getText().toString();
  }

  public void setText(String text) {
    mItem.setText(text);
  }

  @OnCheckedChanged(R.id.item)
  void onCheckedChange() {
    mHolderCheckedListener.onHolderClicked(mItem.isChecked(), (Integer) mItem.getTag());
  }

  public void setTag(int tag) {
    mItem.setTag(tag);
  }

  public void setChecked(boolean checked) {
    mItem.setChecked(checked);
  }

  public CheckBox getItem() {
    return mItem;
  }
}
