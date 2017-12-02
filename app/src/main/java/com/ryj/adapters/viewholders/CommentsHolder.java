package com.ryj.adapters.viewholders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.R;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.interfaces.OnVoteClickListener;
import com.ryj.models.enums.VoteType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 11/14/17. */
public class CommentsHolder extends BaseViewHolder {
  public static final int JUDGE_PROFILE_MODE = 0;
  public static final int LAWYER_PROFILE_MODE = 1;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.frame_likes)
  RelativeLayout mFrameLikes;

  @BindView(R.id.abbr)
  TextView mAbbr;

  @BindView(R.id.lawyer_fullname)
  TextView mLawyerFullName;

  @BindView(R.id.on_the)
  TextView mOnThe;

  @BindView(R.id.judge_fullname)
  TextView mJudgeFullName;

  @BindView(R.id.date)
  TextView mDate;

  @BindView(R.id.body)
  TextView mBody;

  @BindView(R.id.like)
  ImageView mLikeImage;

  @BindView(R.id.dislike)
  ImageView mDislikeImage;

  @BindView(R.id.like_count)
  TextView mLikeCountText;

  @BindView(R.id.dislike_count)
  TextView mDisLikeCountText;

  @BindView(R.id.frame)
  RelativeLayout mFrame;

  @BindView(R.id.complain)
  ImageView mComplain;

  private OnVoteClickListener mOnVoteClickListener;
  private OnHolderClickListener mOnHolderClickListener;

  private boolean mIsLike;
  private boolean mIsDislike;

  public CommentsHolder(
      View itemView,
      OnHolderClickListener onHolderClickListener,
      OnVoteClickListener onVoteClickListener,
      boolean isSignedUp) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mOnHolderClickListener = onHolderClickListener;
    mOnVoteClickListener = onVoteClickListener;
  }

  public void setPhoto(Uri uri) {
    mPhoto.setImageURI(uri);
  }

  public void setAbbr(String abbr) {
    mAbbr.setText(abbr);
  }

  public void setLawyerFullName(String fullName) {
    mLawyerFullName.setText(fullName);
  }

  public void setJudgeFullName(String judgeName) {
    mJudgeFullName.setText(judgeName);
  }

  public void setDate(String date) {
    mDate.setText(date);
  }

  public void setBody(String body) {
    mBody.setText(body);
  }

  public void showAbbr(String textStub) {
    mAbbr.setVisibility(View.VISIBLE);
    mAbbr.setText(textStub);
  }

  public void hideAbbr() {
    mAbbr.setVisibility(View.GONE);
  }

  public void setPlaceHolder(int resource) {
    mPhoto.setImageResource(resource);
  }

  public void hideLikeFrame() {
    mFrameLikes.setVisibility(View.GONE);
  }

  public void showLikeFrame() {
    mFrameLikes.setVisibility(View.VISIBLE);
  }

  public void setLikeSelected(boolean isLikeSelected) {
    mLikeImage.setSelected(mIsLike = isLikeSelected);
  }

  public void sedDisLikeSelected(boolean isDisLikeSelected) {
    mDislikeImage.setSelected(mIsDislike = isDisLikeSelected);
  }

  public void setLikeCountText(Integer likeCount) {
    mLikeCountText.setText(String.valueOf(likeCount));
  }

  public void setDisLikeCountText(Integer disLikeCount) {
    mDisLikeCountText.setText(String.valueOf(disLikeCount));
  }

  @OnClick({R.id.like, R.id.dislike, R.id.frame})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.like:
        doLike();
        break;
      case R.id.dislike:
        doDisLike();
        break;
      case R.id.frame:
        mOnHolderClickListener.onHolderClick(true, getTag());
        break;
    }
  }

  private void doLike() {
    if (!mIsLike) {
      incrLike();
      if (mIsDislike) {
        mDislikeImage.setSelected(mIsDislike = false);
      }
    } else {
      decrLike();
    }
  }

  private void doDisLike() {
    if (!mIsDislike) {
      incrDisLike();
      if (mIsLike) {
        mLikeImage.setSelected(mIsLike = false);
      }
    } else {
      decrDislike();
    }
  }

  private void incrLike() {
    mLikeImage.setSelected(mIsLike = true);
    mOnVoteClickListener.vote(VoteType.LIKE, getTag());
  }

  private void decrLike() {
    mLikeImage.setSelected(mIsLike = false);
    mOnVoteClickListener.deleteVote(getTag());
  }

  private void incrDisLike() {
    mDislikeImage.setSelected(mIsDislike = true);
    mOnVoteClickListener.vote(VoteType.DISLIKE, getTag());
  }

  private void decrDislike() {
    mDislikeImage.setSelected(mIsDislike = false);
    mOnVoteClickListener.deleteVote( getTag());
  }

  public void setMode(int mode) {
    switch (mode) {
      case JUDGE_PROFILE_MODE:
        setJudgeProfileMode();
        return;
      case LAWYER_PROFILE_MODE:
        setLawyerProfileMode();
        return;
    }
  }

  private void setLawyerProfileMode() {
    mLawyerFullName.setVisibility(View.INVISIBLE);
    mOnThe.setVisibility(View.VISIBLE);
    mJudgeFullName.setVisibility(View.VISIBLE);
  }

  private void setJudgeProfileMode() {
    mLawyerFullName.setVisibility(View.VISIBLE);
    mOnThe.setVisibility(View.INVISIBLE);
    mJudgeFullName.setVisibility(View.INVISIBLE);
  }
}
