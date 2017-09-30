package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.JudgeHolder;
import com.ryj.listeners.Loadable;
import com.ryj.models.response.Judge;
import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** Created by andrey on 9/5/17. */
public class JudgeAdapter extends RecyclerView.Adapter<JudgeHolder> {
  private LayoutInflater mInflater;
  private List<Judge> mJudges = new ArrayList<>();
  private boolean mIsLoad;
  private Loadable mLoadable;

  public JudgeAdapter(Context context, Loadable loadable) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadable;
  }

  @Override
  public JudgeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_judges_judge, parent, false);
    return new JudgeHolder(view);
  }

  @Override
  public void onBindViewHolder(JudgeHolder holder, int position) {

    holder.setName(StringUtils.getFullName(mJudges.get(position)));

    if (mJudges.get(position).getCourt().getTitle() != null) {
      holder.setCommentsCount(String.valueOf(mJudges.get(position).getCourt().getTitle()));
    } else {
      holder.setCommentsCount(StringUtils.ZERO);
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

    if (position == mJudges.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
  }

  @Override
  public int getItemCount() {
    return mJudges.size();
  }

  public void addItems(List<Judge> judges) {
    mJudges.addAll(judges);
    notifyDataSetChanged();
    mLoadable.setItemCount(getItemCount());
  }

  public void reloadItems(List<Judge> judges) {
    mJudges.clear();
    mJudges.addAll(judges);
    notifyDataSetChanged();
    mLoadable.setItemCount(getItemCount());
  }

  public void setIsLoadable(boolean isLoad) {
    this.mIsLoad = isLoad;
  }
}
