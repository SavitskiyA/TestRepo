package com.savitskiy.testsample;

import android.view.View;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 7/13/17.
 */

public class CustomSpinnerViewHolder {
  @BindView(R.id.text)
  TextView mText;
  @BindColor(android.R.color.darker_gray)
  int mGray;
  @BindColor(android.R.color.black)
  int mBlack;

  public CustomSpinnerViewHolder(View view) {
    ButterKnife.bind(this, view);
  }

  public void setText(String text) {
    mText.setText(text);
  }

  public void setGray() {
    mText.setTextColor(mGray);
  }

  public void setBlack() {
    mText.setTextColor(mBlack);
  }
}
