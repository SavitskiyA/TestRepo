package com.ryj.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.CourtTypeHolder;
import com.ryj.interfaces.OnHolderClickListener;

import java.util.List;

/** Created by andrey on 11/30/17. */
public class CourtTypeAdapter<String> extends BaseRecyclerAdapter<String> {
  private OnHolderClickListener mOnHolderListener;
  private boolean[] mItemsSelected;

  public CourtTypeAdapter(
      Context context,
      OnHolderClickListener onHolderListener,
      List<String> items,
      boolean[] itemSelected) {
    super(context);
    mItems.clear();
    mItems.addAll(items);
    mOnHolderListener = onHolderListener;
    mItemsSelected = itemSelected;
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new CourtTypeHolder(
        mInflater.inflate(R.layout.item_option, parent, false), mOnHolderListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    CourtTypeHolder holder = (CourtTypeHolder) viewHolder;
    String item = mItems.get(position);
    holder.setTag(position);
    holder.setCourtType((java.lang.String) item);
    holder.setChecked(mItemsSelected[position]);
  }
}
