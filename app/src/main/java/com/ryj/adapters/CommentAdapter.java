package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.CommentsHolder;
import com.ryj.fragments.LawyerFragment;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.interfaces.OnLoadMoreClickListener;
import com.ryj.interfaces.OnVoteClickListener;
import com.ryj.models.response.Comment;
import com.ryj.utils.DateUtils;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.StringUtils;

import java.text.ParseException;

/** Created by andrey on 11/15/17. */
public class CommentAdapter<T> extends BaseRecyclerAdapter<T> {
  private OnHolderClickListener mOnHolderListener;
  private OnVoteClickListener mOnVoteHolderListener;
  private boolean mIsSignedUp;

  public CommentAdapter(
      Context context,
      OnHolderClickListener onHolderListener,
      OnVoteClickListener onHolderLikeListener,
      OnLoadMoreClickListener onLoadMoreHolderListener,
      boolean isSignedUp) {
    super(context, onLoadMoreHolderListener);
    mOnHolderListener = onHolderListener;
    mOnVoteHolderListener = onHolderLikeListener;
    mIsSignedUp = isSignedUp;
  }

  public CommentAdapter(
      Context context,
      OnHolderClickListener onHolderListener,
      OnVoteClickListener onHolderLikeListener,
      boolean isSignedUp) {
    super(context);
    mOnHolderListener = onHolderListener;
    mOnVoteHolderListener = onHolderLikeListener;
    mIsSignedUp = isSignedUp;
  }

  private void setJudgePhotoAbbr(CommentsHolder viewHolder, Comment comment) {
    if (comment.getJudge().getAvatar() != null
        && comment.getJudge().getAvatar().getSmall() != null) {
      viewHolder.setPhoto(Uri.parse(comment.getJudge().getAvatar().getSmall()));
      viewHolder.hideAbbr();
    } else {
      viewHolder.setPlaceHolder(
          DrawUtils.RESOURCES[
              StringUtils.getFullNameLength(comment.getJudge()) % DrawUtils.RESOURCES.length]);
      viewHolder.showAbbr(StringUtils.getAbbrFromFullName(comment.getJudge()));
    }
    viewHolder.setJudgeFullName(StringUtils.getFullName(comment.getJudge()));
  }

  private void setLawyerPhotoAbbr(CommentsHolder viewHolder, Comment comment) {
    if (comment.getLawyer().getAvatar().getSmall() != null) {
      viewHolder.setPhoto(Uri.parse(comment.getLawyer().getAvatar().getSmall()));
      viewHolder.hideAbbr();
    } else {
      viewHolder.setPlaceHolder(
          DrawUtils.RESOURCES[
              StringUtils.getFullNameLength(comment.getLawyer()) % DrawUtils.RESOURCES.length]);
      viewHolder.showAbbr(StringUtils.getAbbrFromFullName(comment.getLawyer()));
    }
    viewHolder.setLawyerFullName(StringUtils.getFullName(comment.getLawyer()));
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new CommentsHolder(
            mInflater.inflate(R.layout.item_comments, parent, false),
            mOnHolderListener,
            mOnVoteHolderListener,
            mIsSignedUp);
  }
  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    CommentsHolder holder = (CommentsHolder) viewHolder;
    Comment comment = (Comment) mItems.get(position);
    holder.setTag(position);

    if (mOnHolderListener instanceof LawyerFragment) {
      setJudgePhotoAbbr(holder, comment);
      holder.setMode(CommentsHolder.LAWYER_PROFILE_MODE);
    } else {
      setLawyerPhotoAbbr(holder, comment);
      holder.setMode(CommentsHolder.JUDGE_PROFILE_MODE);
    }

    holder.setBody(comment.getBody());

    try {
      holder.setDate(DateUtils.getFormatedDate(comment.getCreatedAt()));
    } catch (ParseException e) {
      e.printStackTrace();
      holder.setDate(StringUtils.EMPTY_STRING);
    }

    if (mIsSignedUp) {
      holder.showLikeFrame();
    } else {
      holder.hideLikeFrame();
    }

    holder.setLikeCountText(comment.getLikesCount());
    holder.setDisLikeCountText(comment.getDislikesCount());

    if (comment.getVoteKind() != null) {
      if (comment.getVoteKind() == 0) {
        holder.setLikeSelected(true);
      } else if (comment.getVoteKind() == 1) {
        holder.sedDisLikeSelected(true);
      }
    } else {
      holder.setLikeSelected(false);
      holder.sedDisLikeSelected(false);
    }
  }
}
