package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.auth.signup.SignUpListActivity;
import com.ryj.activities.filters.FiltersCategoryActivity;
import com.ryj.activities.filters.FiltersCourtTypeActivity;
import com.ryj.adapters.viewholders.CategoryHolder;
import com.ryj.adapters.viewholders.CourtTypeHolder;
import com.ryj.adapters.viewholders.SignUpItemHolder;
import com.ryj.interfaces.OnHolderListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 10/19/17.
 */

public class ItemListRecyclerAdapter<T> extends ListRecyclerAdapter<T> implements OnHolderListener {
  private LayoutInflater mInflater;
  private boolean[] mItemsSelected;
  private OnHolderListener mListener;

  public ItemListRecyclerAdapter(
          Context context,
          OnHolderListener listener,
          List<T> items,
          boolean[] itemsSelected
  ) {
    this.mItems.addAll(items);
    this.mInflater = LayoutInflater.from(context);
    this.mListener = listener;
    this.mItemsSelected = itemsSelected;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mListener instanceof SignUpListActivity) {
      return new SignUpItemHolder(mInflater.inflate(R.layout.item_signup_list, parent, false), this);
    } else if (mListener instanceof FiltersCategoryActivity) {
      return new CategoryHolder(mInflater.inflate(R.layout.item_signup_list, parent, false), this);
    } else if (mListener instanceof FiltersCourtTypeActivity) {
      return new CourtTypeHolder(mInflater.inflate(R.layout.item_option, parent, false), this);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof SignUpItemHolder) {
      bindSignUpItemHolder((SignUpItemHolder) holder, position);
      mListener.onHolderClicked(mItemsSelected[position], 0);
    } else if (holder instanceof CategoryHolder) {
      bindCategoryHolder((CategoryHolder) holder, position);
    } else if (holder instanceof CourtTypeHolder) {
      bindCourtTypeHolder((CourtTypeHolder) holder, position);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  private void bindCourtTypeHolder(CourtTypeHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setTag(position);
    holder.setCourtType(item);
    holder.setChecked(mItemsSelected[position]);
  }

  private void bindCategoryHolder(CategoryHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setText(item);
    holder.setTag(position);
    holder.setChecked(mItemsSelected[position]);
  }

  private void bindSignUpItemHolder(SignUpItemHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setText(item);
    holder.setTag(position);
    holder.setChecked(mItemsSelected[position]);
  }


  @Override
  public void onHolderClicked(boolean enable, int position) {
    if (mListener instanceof FiltersCourtTypeActivity) {
      mListener.onHolderClicked(false, position);
      Arrays.fill(mItemsSelected, false);
      mItemsSelected[position] = true;
      notifyDataSetChanged();
    } else {
      mListener.onHolderClicked(enable, position);
    }
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }
}
