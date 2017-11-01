package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.filters.FiltersCityActivity;
import com.ryj.activities.filters.FiltersRegionActivity;
import com.ryj.adapters.viewholders.AreaHolder;
import com.ryj.adapters.viewholders.CourtHolder;
import com.ryj.adapters.viewholders.JudgeHolder;
import com.ryj.adapters.viewholders.LoadMoreViewHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.fragments.CourtFragment;
import com.ryj.fragments.CourtsFragment;
import com.ryj.fragments.JudgesFragment;
import com.ryj.interfaces.LoadListener;
import com.ryj.interfaces.Loadable;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.response.Area;
import com.ryj.models.response.Court;
import com.ryj.models.response.Judge;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.StringUtils;

import java.util.List;

/**
 * Created by andrey on 10/18/17.
 */
public class LoadableListRecyclerAdapter<T> extends ListRecyclerAdapter<T>
        implements Loadable<T>, OnHolderListener {
  private boolean mIsLoad;
  private int mCurrentChecked = -1;
  private LoadListener mLoadable;
  private LayoutInflater mInflater;
  private OnHolderListener mListener;

  public LoadableListRecyclerAdapter(
          Context context, LoadListener loadListener, OnHolderListener holderListener) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadListener;
    mListener = holderListener;
//    if (mListener instanceof JudgesFragment) {
//      setAllCommentsButtonShown(true);
//    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEWTYPE_LOADER) {
      return new LoaderViewHolder(mInflater.inflate(R.layout.item_load, parent, false));
    } else if (viewType == VIEWTYPE_LOAD_MORE) {
      return new LoadMoreViewHolder(mInflater.inflate(R.layout.item_load_more_all_comments, parent, false), this);
    } else {
      return createHolder(parent);
    }
  }

  private RecyclerView.ViewHolder createHolder(ViewGroup parent) {
    if (mListener instanceof JudgesFragment || mListener instanceof CourtFragment) {
      return new JudgeHolder(mInflater.inflate(R.layout.item_judges_judge, parent, false), this);
    } else if (mListener instanceof CourtsFragment) {
      return new CourtHolder(mInflater.inflate(R.layout.item_courts_court, parent, false), this);
    } else if (mListener instanceof FiltersCityActivity
            || mListener instanceof FiltersRegionActivity) {
      return new AreaHolder(mInflater.inflate(R.layout.item_option, parent, false), this);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!isAllCommentsButtonShown()) {
      loadMore(position);
    }
    if (holder instanceof LoaderViewHolder) {
      bindLoaderHolder(holder);
    } else if (holder instanceof LoadMoreViewHolder) {
      bindLoadMoreHolder(holder, position);
    } else {
      bindItemHolder(holder, position);
    }
  }

  @Override
  public void loadMore(int position) {
    if (position == mItems.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
  }

  @Override
  public void bindItemHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof JudgeHolder) {
      bindJudgeHolder(viewHolder, position);
    } else if (viewHolder instanceof CourtHolder) {
      bindCourtHolder(viewHolder, position);
    } else if (viewHolder instanceof AreaHolder) {
      bindAreaHolder(viewHolder, position);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  private void bindAreaHolder(RecyclerView.ViewHolder viewHolder, int position) {
    AreaHolder holder = (AreaHolder) viewHolder;
    Area area = (Area) mItems.get(position);
    if (position == 0) {
      holder.setUnderlineVisible();
    }
    holder.setTag(position);
    holder.setArea(area.getName());
    holder.setChecked(position == mCurrentChecked);
  }

  private void bindCourtHolder(RecyclerView.ViewHolder viewHolder, int position) {
    CourtHolder holder = (CourtHolder) viewHolder;
    Court court = (Court) mItems.get(position);
    if (court.getName() != null) {
      holder.setCourt(String.valueOf(court.getName()));
    } else {
      holder.setCourt(StringUtils.ZERO);
    }
    holder.setTag(position);
    holder.setRating(0f);
  }

  private void bindJudgeHolder(RecyclerView.ViewHolder viewHolder, int position) {
    JudgeHolder holder = (JudgeHolder) viewHolder;
    Judge judge = (Judge) mItems.get(position);
    holder.setTag(position);
    holder.setName(StringUtils.getFullName(judge));
    if (judge.getCourt().getName() != null) {
      holder.setCourt(String.valueOf(judge.getCourt().getName()));
    } else {
      holder.setCourt(StringUtils.EMPTY_STRING);
    }
    if (judge.getCommentsCount() != null) {
      holder.setCommentsCount(String.valueOf(judge.getCommentsCount()));
    } else {
      holder.setCommentsCount(StringUtils.ZERO);
    }
    if (judge.getRating() != null) {
      holder.setRating(judge.getRating() / 2);
    } else {
      holder.setRating(0f);
    }
    if (judge.getRatingCount() != null) {
      holder.setMarksCount(String.valueOf(judge.getRatingCount()));
    } else {
      holder.setMarksCount(StringUtils.ZERO);
    }

    if (judge.getAvatar().getOrigin() != null) {
      holder.setPhoto(Uri.parse(judge.getAvatar().getOrigin()));
      holder.hideTextStub();
    } else {
      holder.setPlaceHolder(
              DrawUtils.RESOURCES[StringUtils.getFullNameLength(judge) % DrawUtils.RESOURCES.length]);
      holder.showTextStub(StringUtils.getAbbrFromFullName(judge));
    }
  }

  @Override
  public void bindLoaderHolder(RecyclerView.ViewHolder viewHolder) {
    LoaderViewHolder holder = (LoaderViewHolder) viewHolder;
    if (mIsLoad) {
      holder.setLoadSpinnerVisible(View.VISIBLE);
      holder.startAnimation();
    } else {
      holder.setLoadSpinnerVisible(View.GONE);
      holder.stopAnimation();
    }
  }

  @Override
  public void bindLoadMoreHolder(RecyclerView.ViewHolder viewHolder, int position) {
    LoadMoreViewHolder holder = (LoadMoreViewHolder) viewHolder;
    holder.setTag(position);
  }

  @Override
  public void setIsLoadable(boolean load) {
    mIsLoad = load;
  }

  public void setCurrentCheckedItem(int position) {
    mCurrentChecked = position;
    notifyDataSetChanged();
  }

  @Override
  public void addItems(List<T> judges) {
    mItems.addAll(judges);
    notifyDataSetChanged();
  }

  @Override
  public void reloadItems(List<T> judges) {
    mItems.clear();
    mItems.addAll(judges);
    notifyDataSetChanged();
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
    if (mListener instanceof JudgesFragment || mListener instanceof CourtFragment) {
      onJudgeHolderClicked(enable, position);
    } else if (mListener instanceof CourtsFragment) {
      onCourtHolderClicked(enable, position);
    } else if (mListener instanceof FiltersCityActivity
            || mListener instanceof FiltersRegionActivity) {
      onAreaHolderClicked(enable, position);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  private void onAreaHolderClicked(boolean enable, int position) {
    mListener.onHolderClicked(false, position);
    mCurrentChecked = position;
    notifyDataSetChanged();
  }

  private void onCourtHolderClicked(boolean enable, int position) {
    mListener.onHolderClicked(enable, position);
  }

  private void onJudgeHolderClicked(boolean enable, int position) {
    mListener.onHolderClicked(enable, position);
  }

  private void onLoadMoreHolderClicked(boolean enable, int position) {
    setAllCommentsButtonShown(false);
    mLoadable.load(mLoadable.increment());
  }
}
