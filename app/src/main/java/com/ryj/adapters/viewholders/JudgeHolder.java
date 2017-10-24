package com.ryj.adapters.viewholders;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by andrey on 9/5/17. */
public class JudgeHolder extends RecyclerView.ViewHolder {

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

  @BindView(R.id.text_stub)
  TextView mTextStub;

  public JudgeHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
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
}
