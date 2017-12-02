package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.FavouriteHolder;
import com.ryj.interfaces.OnDeleteClickListener;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.response.Judge;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.StringUtils;

/** Created by andrey on 11/15/17. */
public class FavouriteAdapter<T> extends BaseRecyclerAdapter<T> {
  private LayoutInflater mInflater;
  private OnHolderClickListener mOnHolderListener;
  private OnDeleteClickListener mOnDeleteOnHolderListener;
  private boolean mIsLoad;

  public FavouriteAdapter(
      Context context,
      OnHolderClickListener onHolderListener,
      OnDeleteClickListener onDeleteListener) {
    super(context);
    mInflater = LayoutInflater.from(context);
    mOnHolderListener = onHolderListener;
    mOnDeleteOnHolderListener = onDeleteListener;
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new FavouriteHolder(
        mInflater.inflate(R.layout.item_favourite_judge, parent, false),
        mOnHolderListener,
        mOnDeleteOnHolderListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    FavouriteHolder holder = (FavouriteHolder) viewHolder;
    Judge judge = (Judge) mItems.get(position);
    holder.setTag(position);
    holder.setName(StringUtils.getFullName(judge));
    holder.setCourt(String.valueOf(judge.getCourt().getName()));
    holder.setCommentsCount(String.valueOf(judge.getCommentsCount()));
    holder.setRating(judge.getRating() / 2);
    holder.setMarksCount(String.valueOf(judge.getRatingCount()));
    if (judge.getAvatar().getOrigin() != null) {
      holder.setPhoto(Uri.parse(judge.getAvatar().getOrigin()));
      holder.hideTextStub();
    } else {
      holder.setPlaceHolder(
          DrawUtils.RESOURCES[StringUtils.getFullNameLength(judge) % DrawUtils.RESOURCES.length]);
      holder.showTextStub(StringUtils.getAbbrFromFullName(judge));
    }
  }
}
