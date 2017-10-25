package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.ryj.R;
import com.ryj.interfaces.OnHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrey on 10/25/17.
 */

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.frame)
  FrameLayout mFrame;
  private OnHolderListener mListener;

  public LoadMoreViewHolder(View itemView, OnHolderListener listener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mListener = listener;

  }

  public void setTag(int position) {
    mFrame.setTag(position);
  }

  @OnClick(R.id.frame)
  void onClick() {
    mListener.onHolderClicked(false, (Integer) mFrame.getTag());
  }
}
