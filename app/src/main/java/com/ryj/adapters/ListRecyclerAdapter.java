package com.ryj.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 10/17/17.
 */

public abstract class ListRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  protected static final int VIEWTYPE_ITEM = 0;
  protected static final int VIEWTYPE_LOADER = 1;
  protected static final int VIEWTYPE_LOAD_MORE = 2;
  protected List<T> mItems = new ArrayList<T>();
  private boolean isAllCommentsButtonShown;

  private boolean isLastItem(int position) {
    return position != 0 && position == getItemCount() - 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (isAllCommentsButtonShown() && isLastItem(position)) {
      return VIEWTYPE_LOAD_MORE;
    } else if (isLastItem(position)) {
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

  public boolean isAllCommentsButtonShown() {
    return isAllCommentsButtonShown;
  }

  public void setAllCommentsButtonShown(boolean allCommentsButtonShown) {
    isAllCommentsButtonShown = allCommentsButtonShown;
  }

}
