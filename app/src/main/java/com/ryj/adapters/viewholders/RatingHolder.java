package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 11/2/17.
 */

public class RatingHolder extends BaseViewHolder {
  @BindView(R.id.mark_name)
  TextView mTitle;

  @BindView(R.id.mark_value)
  TextView mValue;

  @BindView(R.id.mark_denominator)
  TextView mDenominator;

  public RatingHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void setTitle(String title) {
    mTitle.setText(title);
  }

  public void setValue(String value) {
    mValue.setText(value);
  }

  public void setDenominator(String denominator) {
    mDenominator.setText(denominator);
  }
}
