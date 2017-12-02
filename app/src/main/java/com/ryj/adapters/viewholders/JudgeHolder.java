package com.ryj.adapters.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 9/5/17.
 */
public class JudgeHolder extends BaseViewHolder {

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.name)
  TextView mName;

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.ratingbar)
  RatingView mRatingBar;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  @BindView(R.id.comments_count)
  TextView mCommentsCount;

  @BindView(R.id.abbr)
  TextView mTextStub;

  private OnHolderClickListener mListener;

  public JudgeHolder(View itemView, OnHolderClickListener listener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mListener = listener;
  }

  public void setPlaceHolder(int resource) {
    mPhoto.setImageResource(resource);
  }

  public void setName(String name) {
    mName.setText(name);
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

  public void setCommentsCount(String count) {
    mCommentsCount.setText(count);
  }

  public void setPhoto(Uri uri) {
    mPhoto.setImageURI(uri);
  }

  public void showTextStub(String textStub) {
    mTextStub.setVisibility(View.VISIBLE);
    mTextStub.setText(textStub);
  }

  public void hideTextStub() {
    mTextStub.setVisibility(View.GONE);
  }

  public void setTag(int tag) {
    mName.setTag(tag);
  }

  @OnClick(R.id.frame_judge)
  void onClick() {
    mListener.onHolderClick(false, (Integer) mName.getTag());
  }
}
