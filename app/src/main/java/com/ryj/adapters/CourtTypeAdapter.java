package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.CourtTypeHolder;
import com.ryj.listeners.OnCourtTypeAdapterListener;
import com.ryj.listeners.OnHolderListener;

/** Created by andrey on 9/29/17. */
public class CourtTypeAdapter extends RecyclerView.Adapter<CourtTypeHolder>
    implements OnHolderListener {
  private LayoutInflater mInflater;
  private String[] mCourts;
  private OnCourtTypeAdapterListener mListener;
  private int mCurrentChecked = -1;

  public CourtTypeAdapter(
      Context context, OnCourtTypeAdapterListener listener, int lastChecked, String... courts) {
    this.mInflater = LayoutInflater.from(context);
    this.mListener = listener;
    this.mCourts = courts;
    this.mCurrentChecked = lastChecked;
  }

  @Override
  public CourtTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_option, parent, false);
    return new CourtTypeHolder(view, this);
  }

  @Override
  public void onBindViewHolder(CourtTypeHolder holder, int position) {
    holder.setTag(position);
    holder.setCourtType(mCourts[position]);
    holder.setChecked(position == mCurrentChecked);
  }

  @Override
  public int getItemCount() {
    return mCourts.length;
  }

  @Override
  public void onHolderCheckedChange(boolean enable, int position) {
    mListener.onHolderCheckedChange(position);
    mCurrentChecked = position;
    notifyDataSetChanged();
  }

  public void setCurrentCheckedItem(int position) {
    mCurrentChecked = position;
    notifyDataSetChanged();
  }
}
