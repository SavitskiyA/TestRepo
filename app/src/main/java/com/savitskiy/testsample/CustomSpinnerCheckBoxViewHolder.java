package com.savitskiy.testsample;

import android.view.View;
import android.widget.CheckBox;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 7/13/17.
 */

public class CustomSpinnerCheckBoxViewHolder {
  @BindView(R.id.checkbox)
  CheckBox mCheckbox;
  @BindColor(android.R.color.darker_gray)
  int mGray;
  @BindColor(android.R.color.black)
  int mBlack;

  public CustomSpinnerCheckBoxViewHolder(View view) {
    ButterKnife.bind(this, view);
  }

  public void setText(String text) {
    mCheckbox.setText(text);
  }

  public void setGray() {
    mCheckbox.setTextColor(mGray);
  }

  public void setBlack() {
    mCheckbox.setTextColor(mBlack);
  }

  public CheckBox getCheckbox() {
    return mCheckbox;
  }
}
