package com.ryj.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.RegionHolder;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.response.Area;

/** Created by andrey on 11/30/17. */
public class RegionAdapter<T> extends BaseRecyclerAdapter<T> {
  private OnHolderClickListener mOnHolderClickListener;
  private int mCurrentChecked = -1;

  public RegionAdapter(Context context, OnHolderClickListener onHolderListener) {
    super(context);
    mOnHolderClickListener = onHolderListener;
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new RegionHolder(
        mInflater.inflate(R.layout.item_option, parent, false), mOnHolderClickListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    RegionHolder holder = (RegionHolder) viewHolder;
    Area area = (Area) mItems.get(position);
    if (position == 0) {
      holder.setUnderlineVisible();
    }
    holder.setTag(position);
    holder.setArea(area.getName());
    holder.setChecked(position == mCurrentChecked);
  }

  public void setCurrentCheckedItem(int position) {
    mCurrentChecked = position;
    notifyDataSetChanged();
  }
}
