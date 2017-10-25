package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryj.interfaces.OnHolderListener;

/**
 * Created by andrey on 10/25/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
  protected OnHolderListener mListener;

  public BaseViewHolder(View itemView, OnHolderListener listener) {
    super(itemView);
    mListener = listener;
  }

  public abstract void setTag(int position);

}
