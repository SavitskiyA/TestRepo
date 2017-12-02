package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.FrameLayout;

import com.ryj.R;
import com.ryj.interfaces.OnLoadMoreClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 10/25/17. */
public class LoadMoreViewHolder extends BaseViewHolder {
  @BindView(R.id.frame)
  FrameLayout mFrame;

  public LoadMoreViewHolder(View itemView, OnLoadMoreClickListener mOnLoadMoreClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    setOnLoadMoreClickListener(mOnLoadMoreClickListener);
  }

  @OnClick(R.id.frame)
  void onClick() {
    mOnLoadMoreClickListener.onLoadMoreClick(false, (Integer) getTag());
  }
}
