package com.ryj.listeners;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by andrey on 10/18/17.
 */

public interface Loadable<T> {
  void addItems(List<T> judges);

  void reloadItems(List<T> judges);

  void loadMore(int position);

  void bindLoaderHolder(RecyclerView.ViewHolder viewHolder);

  void bindItemHolder(RecyclerView.ViewHolder viewHolder, int position);

  void setIsLoadable(boolean load);
}
