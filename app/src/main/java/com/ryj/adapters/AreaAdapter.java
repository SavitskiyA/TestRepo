package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.AreaHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.listeners.Loadable;
import com.ryj.listeners.OnAreaAdapterListener;
import com.ryj.listeners.OnHolderListener;
import com.ryj.models.response.Area;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 9/18/17. */
public class AreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements OnHolderListener {
  private static final int VIEWTYPE_ITEM = 1;
  private static final int VIEWTYPE_LOADER = 2;
  private LayoutInflater mInflater;
  private List<Area> mAreas = new ArrayList<>();
  private Loadable mLoadable;
  private OnAreaAdapterListener mListener;
  private boolean mIsLoad;
  private int mLastChecked = -1;

  public AreaAdapter(Context context, Loadable loadable, OnAreaAdapterListener listener) {
    this.mInflater = LayoutInflater.from(context);
    this.mLoadable = loadable;
    this.mListener = listener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEWTYPE_LOADER) {
      return new LoaderViewHolder(mInflater.inflate(R.layout.item_load, parent, false));
    } else {
      return new AreaHolder(mInflater.inflate(R.layout.item_option, parent, false), this);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (position == mAreas.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
    if (holder instanceof LoaderViewHolder) {
      bindLoaderViewHolder(holder);
    } else {
      bindStandartViewHolder(holder, position);
    }
  }

  @Override
  public int getItemCount() {
    if (mAreas == null || mAreas.size() == 0) {
      return 0;
    }
    return mAreas.size() + 1;
  }

  @Override
  public long getItemId(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return -1;
    }
    return mAreas.get(position).getId();
  }

  @Override
  public void onHolderCheckedChange(boolean enable, int position) {
    if (enable && position != mLastChecked) {
      mLastChecked = position;
      notifyDataSetChanged();
      mListener.onHolderCheckedChange(mAreas.get(position).getId(), position);
    } else {
      mLastChecked = -1;
      notifyDataSetChanged();
      mListener.onHolderCheckedChange(null, position);
    }
  }

  public void addItems(List<Area> areas) {
    mAreas.addAll(areas);
    notifyDataSetChanged();
  }

  public void reloadItems(List<Area> areas) {
    mAreas.clear();
    mAreas.addAll(areas);
    notifyDataSetChanged();
  }

  public void setIsLoadable(boolean load) {
    mIsLoad = load;
  }

  public void setLastCheckedItem(int position) {
    mLastChecked = position;
    notifyDataSetChanged();
  }

  private void bindLoaderViewHolder(RecyclerView.ViewHolder viewHolder) {
    LoaderViewHolder holder = (LoaderViewHolder) viewHolder;
    if (mIsLoad) {
      holder.setLoadSpinnerVisible(View.VISIBLE);
      holder.startAnimation();
    } else {
      holder.setLoadSpinnerVisible(View.GONE);
      holder.stopAnimation();
    }
  }

  private void bindStandartViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    AreaHolder holder = (AreaHolder) viewHolder;
    if (position == 0) {
      holder.setUnderlineVisible();
    }
    holder.setTag(position);
    holder.setArea(mAreas.get(position).getName());
    holder.setChecked(position == mLastChecked);
  }

  @Override
  public int getItemViewType(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return VIEWTYPE_LOADER;
    }
    return VIEWTYPE_ITEM;
  }
}
