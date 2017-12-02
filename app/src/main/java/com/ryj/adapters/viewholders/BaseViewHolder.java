package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryj.interfaces.OnLoadMoreClickListener;

/** Created by andrey on 11/30/17. */
public class BaseViewHolder extends RecyclerView.ViewHolder {
  private int mTag;
  protected OnLoadMoreClickListener mOnLoadMoreClickListener;

  public BaseViewHolder(View itemView) {
    super(itemView);
  }

  public int getTag() {
    return mTag;
  }

  public void setTag(int tag) {
    mTag = tag;
  }

  public void setOnLoadMoreClickListener(OnLoadMoreClickListener onLoadMoreClickListener) {
    mOnLoadMoreClickListener = onLoadMoreClickListener;
  }
}
