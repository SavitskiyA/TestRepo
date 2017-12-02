package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 9/5/17.
 */
public class CourtHolder extends BaseViewHolder {

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.ratingbar)
  RatingView mRatingBar;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  private OnHolderClickListener mOnHolderClickListener;

  public CourtHolder(View itemView, OnHolderClickListener onHolderClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mOnHolderClickListener = onHolderClickListener;
  }

  public void setCourt(String court) {
    mCourt.setText(court);
  }

  public void setRating(Float rating) {
    mRatingBar.setRating(rating);
  }

  public void setMarksCount(String count) {
    mMarksCount.setText(count);
  }

  @OnClick(R.id.frame_court)
  void onClick() {
    mOnHolderClickListener.onHolderClick(false,getTag());
  }
}
