package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.LoadMoreViewHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.interfaces.OnLoadMoreClickListener;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 11/30/17. */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
  private static final int VIEWTYPE_ITEM = 0;
  private static final int VIEWTYPE_LOADER = 1;
  private static final int VIEWTYPE_LOAD_MORE = 2;
  protected List<T> mItems = new ArrayList<T>();
  protected LayoutInflater mInflater;
  protected OnLoadMoreClickListener mOnLoadMoreHolderListener;
  private boolean mIsPaginationEnable;
  private boolean mIsLoading;
  private boolean isAllCommentsButtonShown;

  public BaseRecyclerAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
  }

  public BaseRecyclerAdapter(Context context, OnLoadMoreClickListener onLoadMoreHolderListener) {
    mInflater = LayoutInflater.from(context);
    mOnLoadMoreHolderListener = onLoadMoreHolderListener;
  }

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
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEWTYPE_LOADER) {
      return new LoaderViewHolder(mInflater.inflate(R.layout.item_load, parent, false));
    } else if (viewType == VIEWTYPE_LOAD_MORE) {
      return new LoadMoreViewHolder(
          mInflater.inflate(R.layout.item_load_more_all_comments, parent, false),
          mOnLoadMoreHolderListener);
    } else {
      return createHolder(parent);
    }
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof LoaderViewHolder) {
      bindLoaderHolder(holder);
    } else if (holder instanceof LoadMoreViewHolder) {
      bindLoadMoreHolder(holder, position);
    } else {
      bindItemHolder(holder, position);
    }
  }

  private void bindLoaderHolder(BaseViewHolder viewHolder) {
    LoaderViewHolder holder = (LoaderViewHolder) viewHolder;
    if (mIsLoading) {
      holder.setLoadSpinnerVisible(View.VISIBLE);
      holder.startAnimation();
    } else {
      holder.setLoadSpinnerVisible(View.GONE);
      holder.stopAnimation();
    }
  }

  private void bindLoadMoreHolder(BaseViewHolder viewHolder, int position) {
    LoadMoreViewHolder holder = (LoadMoreViewHolder) viewHolder;
    holder.setTag(position);
  }

  @Override
  public int getItemCount() {
    return (mItems == null || mItems.size() == 0)
        ? 0
        : mIsPaginationEnable ? mItems.size() + 1 : mItems.size();
  }

  private boolean isAllCommentsButtonShown() {
    return isAllCommentsButtonShown;
  }

  public void setAllCommentsButtonShown(boolean allCommentsButtonShown) {
    isAllCommentsButtonShown = allCommentsButtonShown;
  }

  public void setIsLoading(boolean loading) {
    mIsLoading = loading;
  }

  public boolean isLoading() {
    return mIsLoading;
  }

  public void addItems(List<T> items) {
    mItems.addAll(items);
    notifyDataSetChanged();
  }

  public void reloadItems(List<T> items) {
    mItems.clear();
    mItems.addAll(items);
    notifyDataSetChanged();
  }

  public abstract BaseViewHolder createHolder(ViewGroup parent);

  public abstract void bindItemHolder(BaseViewHolder holder, int position);

  public void setPaginationEnable(boolean paginationEnable) {
    mIsPaginationEnable = paginationEnable;
  }
}
