package com.ryj.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.CategoryHolder;
import com.ryj.interfaces.OnHolderClickListener;

import java.util.List;

/** Created by andrey on 11/30/17. */
public class CategoryAdapter<String> extends BaseRecyclerAdapter<String> {
  private OnHolderClickListener mOnHolderListener;
  private boolean[] mItemsSelected;

  public CategoryAdapter(
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
    return new CategoryHolder(
        mInflater.inflate(R.layout.item_category, parent, false), mOnHolderListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    CategoryHolder holder = (CategoryHolder) viewHolder;
    java.lang.String item = (java.lang.String) mItems.get(position);
    holder.setText(item);
    holder.setTag(position);
    holder.setChecked(mItemsSelected[position]);
  }
}
