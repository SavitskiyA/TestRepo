package com.ryj.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 10/18/17.
 */

public abstract class ListBaseAdapter<T> extends BaseAdapter {
  protected static final int VIEWTYPE_ITEM = 0;
  protected static final int VIEWTYPE_LOADER = 1;
  protected List<T> mItems = new ArrayList<T>();
  protected boolean mIsLoad;

  @Override
  public int getCount() {
    if (mItems == null || mItems.size() == 0) {
      return 0;
    }
    return mItems.size() + 1;
  }

  @Override
  public Object getItem(int position) {
    if (position != 0 && position == getCount() - 1) {
      return -1;
    }
    return mItems.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public abstract View createView(int position, View convertView, ViewGroup parent);

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return createView(position, convertView, parent);
  }

  public boolean isLoaderView(int position) {
    return (position != 0 && position == getCount() - 1);
  }
}
