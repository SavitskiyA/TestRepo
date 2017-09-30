package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.CourtHolder;
import com.ryj.listeners.Loadable;
import com.ryj.models.response.Court;
import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 9/5/17.
 */
public class CourtAdapter extends RecyclerView.Adapter<CourtHolder> {
  private LayoutInflater mInflater;
  private List<Court> mCourts = new ArrayList<>();
  private boolean mIsLoad;
  private Loadable mLoadable;

  public CourtAdapter(Context context, Loadable loadable) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadable;
  }

  @Override
  public CourtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_courts_court, parent, false);
    return new CourtHolder(view);
  }

  @Override
  public void onBindViewHolder(CourtHolder holder, int position) {

    if (mCourts.get(position).getName() != null) {
      holder.setCourt(String.valueOf(mCourts.get(position).getName()));
    } else {
      holder.setCourt(StringUtils.ZERO);
    }

    holder.setRating(0f);

    if (position == mCourts.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
  }

  @Override
  public int getItemCount() {
    return mCourts.size();
  }

  public void addItems(List<Court> judges) {
    mCourts.addAll(judges);
    notifyDataSetChanged();
    mLoadable.setItemCount(getItemCount());
  }

  public void reloadItems(List<Court> judges) {
    mCourts.clear();
    mCourts.addAll(judges);
    notifyDataSetChanged();
    mLoadable.setItemCount(getItemCount());
  }

  public void setIsLoadable(boolean isLoad) {
    this.mIsLoad = isLoad;
  }
}
