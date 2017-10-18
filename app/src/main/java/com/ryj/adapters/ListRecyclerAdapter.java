package com.ryj.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.listeners.Loadable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 10/17/17.
 */

public abstract class ListRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  protected static final int VIEWTYPE_ITEM = 0;
  protected static final int VIEWTYPE_LOADER = 1;
  protected List<T> mItems = new ArrayList<T>();

  @Override
  public int getItemViewType(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return VIEWTYPE_LOADER;
    }
    return VIEWTYPE_ITEM;
  }

  @Override
  public int getItemCount() {
    if (mItems == null || mItems.size() == 0) {
      return 0;
    }
    return mItems.size() + 1;
  }
}
