package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.CourtHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.listeners.Loadable;
import com.ryj.listeners.OnHolderClickedListener;
import com.ryj.listeners.OnHolderListener;
import com.ryj.models.response.Court;
import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 9/5/17. */
public class CourtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnHolderClickedListener {
  private static final int VIEWTYPE_ITEM = 1;
  private static final int VIEWTYPE_LOADER = 2;
  private LayoutInflater mInflater;
  private List<Court> mCourts = new ArrayList<>();
  private boolean mIsLoad;
  private Loadable mLoadable;

  public CourtAdapter(Context context, Loadable loadable) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadable;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEWTYPE_LOADER) {
      return new LoaderViewHolder(mInflater.inflate(R.layout.item_load, parent, false));
    } else {
      return new CourtHolder(mInflater.inflate(R.layout.item_courts_court, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (position == mCourts.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
    if (holder instanceof LoaderViewHolder) {
      bindLoaderViewHolder(holder);
    } else {
      bindStandartViewHolder(holder, position);
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return VIEWTYPE_LOADER;
    }
    return VIEWTYPE_ITEM;
  }

  @Override
  public int getItemCount() {
    if (mCourts == null || mCourts.size() == 0) {
      return 0;
    }
    return mCourts.size() + 1;
  }

  @Override
  public long getItemId(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return -1;
    }
    return mCourts.get(position).getId();
  }

  public void addItems(List<Court> judges) {
    mCourts.addAll(judges);
    notifyDataSetChanged();
  }

  public void reloadItems(List<Court> judges) {
    mCourts.clear();
    mCourts.addAll(judges);
    notifyDataSetChanged();
  }

  public void setIsLoadable(boolean isLoad) {
    this.mIsLoad = isLoad;
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
    CourtHolder holder = (CourtHolder) viewHolder;
    if (mCourts.get(position).getName() != null) {
      holder.setCourt(String.valueOf(mCourts.get(position).getName()));
    } else {
      holder.setCourt(StringUtils.ZERO);
    }
    holder.setRating(0f);
  }

  @Override
  public void onClick(int position) {

  }
}
