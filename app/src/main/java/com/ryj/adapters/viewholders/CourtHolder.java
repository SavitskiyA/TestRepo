package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;
import com.ryj.listeners.OnHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 9/5/17.
 */
public class CourtHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.ratingbar)
  RatingView mRatingBar;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  private OnHolderListener mClickedListener;

  public CourtHolder(View itemView, OnHolderListener listener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mClickedListener = listener;
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

  public void setTag(int tag) {
    mCourt.setTag(tag);
  }

  @OnClick(R.id.frame_court)
  void onClick() {
    mClickedListener.onHolderClicked(false, (Integer) mCourt.getTag());
  }
}
