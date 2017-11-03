package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 11/2/17.
 */

public class DetailRatingHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.mark_name)
  TextView mMarkName;

  @BindView(R.id.mark_value)
  TextView mMarkValue;

  @BindView(R.id.mark_denominator)
  TextView mMarkDenominator;

  public DetailRatingHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void setMarkName(String name) {
    mMarkName.setText(name);
  }

  public void setMarkValue(String value) {
    mMarkValue.setText(value);
  }

  public void setMarkDenominator(String denominator) {
    mMarkDenominator.setText(denominator);
  }
}
