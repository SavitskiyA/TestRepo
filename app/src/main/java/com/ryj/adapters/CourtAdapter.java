package com.ryj.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.CourtHolder;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.response.Court;

/** Created by andrey on 11/30/17. */
public class CourtAdapter<T> extends BaseRecyclerAdapter<T> {

  private OnHolderClickListener mOnHolderListener;

  public CourtAdapter(Context context, OnHolderClickListener onHolderListener) {
    super(context);
    mOnHolderListener = onHolderListener;
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new CourtHolder(
        mInflater.inflate(R.layout.item_courts_court, parent, false), mOnHolderListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    CourtHolder holder = (CourtHolder) viewHolder;
    Court court = (Court) mItems.get(position);
    holder.setCourt(court.getName());
    holder.setTag(position);
    holder.setRating(court.getRating());
    holder.setMarksCount(String.valueOf(court.getRatingCount()));
  }
}
