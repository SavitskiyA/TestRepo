package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.JudgeHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.listeners.Loadable;
import com.ryj.models.response.Judge;
import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 9/5/17. */
public class JudgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int VIEWTYPE_ITEM = 1;
  private static final int VIEWTYPE_LOADER = 2;
  private LayoutInflater mInflater;
  private List<Judge> mJudges = new ArrayList<>();
  private boolean mIsLoad;
  private Loadable mLoadable;

  public JudgeAdapter(Context context, Loadable loadable) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadable;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEWTYPE_LOADER) {
      return new LoaderViewHolder(mInflater.inflate(R.layout.item_load, parent, false));
    } else {
      return new JudgeHolder(mInflater.inflate(R.layout.item_judges_judge, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (position == mJudges.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
    if (holder instanceof LoaderViewHolder) {
      bindLoaderViewHolder(holder);
    } else {
      bindStandartViewHolder(holder, position);
    }
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
    JudgeHolder holder = (JudgeHolder) viewHolder;
    holder.setName(StringUtils.getFullName(mJudges.get(position)));
    if (mJudges.get(position).getCourt().getName() != null) {
      holder.setCourt(String.valueOf(mJudges.get(position).getCourt().getName()));
    } else {
      holder.setCourt(StringUtils.EMPTY_STRING);
    }

    if (mJudges.get(position).getCommentsCount() != null) {
      holder.setCommentsCount(String.valueOf(mJudges.get(position).getCommentsCount()));
    } else {
      holder.setCommentsCount(StringUtils.ZERO);
    }
    if (mJudges.get(position).getRating() != null) {
      holder.setRating(mJudges.get(position).getRating() / 2);
    } else {
      holder.setRating(0f);
    }
    if (mJudges.get(position).getRatingCount() != null) {
      holder.setMarksCount(String.valueOf(mJudges.get(position).getRatingCount()));
    } else {
      holder.setMarksCount(StringUtils.ZERO);
    }

    if (mJudges.get(position).getAvatar().getOrigin() != null) {
      holder.setPhoto(Uri.parse(mJudges.get(position).getAvatar().getOrigin()));
    }
  }

  public void addItems(List<Judge> judges) {
    mJudges.addAll(judges);
    notifyDataSetChanged();
  }

  public void reloadItems(List<Judge> judges) {
    mJudges.clear();
    mJudges.addAll(judges);
    notifyDataSetChanged();
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
    if (mJudges == null || mJudges.size() == 0) {
      return 0;
    }
    return mJudges.size() + 1;
  }

  @Override
  public long getItemId(int position) {
    if (position != 0 && position == getItemCount() - 1) {
      return -1;
    }
    return mJudges.get(position).getId();
  }

  public void setIsLoadable(boolean isLoad) {
    this.mIsLoad = isLoad;
  }
}
