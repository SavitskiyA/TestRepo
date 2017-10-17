package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/5/17. */
public class CourtHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.frame_court)
  FrameLayout mFrameCourt;

  @BindView(R.id.ratingbar)
  RatingView mRatingBar;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  public CourtHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
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

  }
}
