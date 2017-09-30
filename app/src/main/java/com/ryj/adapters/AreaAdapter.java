package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.AreaHolder;
import com.ryj.listeners.Loadable;
import com.ryj.listeners.OnAreaAdapterListener;
import com.ryj.listeners.OnHolderListener;
import com.ryj.models.response.Area;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 9/18/17. */
public class AreaAdapter extends RecyclerView.Adapter<AreaHolder>
    implements OnHolderListener {
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
  public AreaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_option, parent, false);
    return new AreaHolder(view, this);
  }

  @Override
  public void onBindViewHolder(AreaHolder holder, int position) {
    holder.setTag(position);
    holder.setArea(mAreas.get(position).getName());
    holder.setChecked(position == mLastChecked);
    if (position == mAreas.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
    if (position == 0) {
      holder.setUnderlineVisible();
    }
  }

  @Override
  public int getItemCount() {
    return mAreas.size();
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
    mLoadable.setItemCount(getItemCount());
  }

  public void reloadItems(List<Area> areas) {
    mAreas.clear();
    mAreas.addAll(areas);
    notifyDataSetChanged();
    mLoadable.setItemCount(getItemCount());
  }

  public void setIsLoadable(boolean load) {
    mIsLoad = load;
  }

  public void setLastCheckedItem(int position) {
    mLastChecked = position;
    notifyDataSetChanged();
  }
}
