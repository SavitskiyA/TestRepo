package com.ryj.adapters.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;
import com.ryj.interfaces.OnDeleteClickListener;
import com.ryj.interfaces.OnHolderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/5/17. */
public class FavouriteHolder extends BaseViewHolder {

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

  @BindView(R.id.delete)
  FrameLayout mDelete;

  @BindView(R.id.delete_img)
  ImageView mDeleteImg;

  private OnHolderClickListener mOnHolderClickListener;
  private OnDeleteClickListener mOnDeleteClickListener;

  public FavouriteHolder(
      View itemView,
      OnHolderClickListener onHolderClickListener,
      OnDeleteClickListener onDeleteClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mOnHolderClickListener = onHolderClickListener;
    mOnDeleteClickListener = onDeleteClickListener;
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

  @OnClick({R.id.frame_judge, R.id.delete, R.id.delete_img})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.frame_judge:
        mOnHolderClickListener.onHolderClick(false, getTag());
        return;
      case R.id.delete:
        mOnDeleteClickListener.onDeleteClick(getTag());
        return;
      case R.id.delete_img:
        mOnDeleteClickListener.onDeleteClick(getTag());
        return;
    }
  }
}
