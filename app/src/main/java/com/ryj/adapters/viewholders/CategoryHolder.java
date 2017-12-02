package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.CheckBox;

import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/** Created by andrey on 9/20/17. */
public class CategoryHolder extends BaseViewHolder {
  @BindView(R.id.item)
  CheckBox mItem;

  private OnHolderClickListener mOnHolderClickListener;

  public CategoryHolder(View itemView, OnHolderClickListener onHolderClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.mOnHolderClickListener = onHolderClickListener;
  }

  public String getText() {
    return mItem.getText().toString();
  }

  public void setText(String text) {
    mItem.setText(text);
  }

  @OnCheckedChanged(R.id.item)
  void onCheckedChange() {
    mOnHolderClickListener.onHolderClick(mItem.isChecked(), getTag());
  }

  public void setChecked(boolean checked) {
    mItem.setChecked(checked);
  }
}
