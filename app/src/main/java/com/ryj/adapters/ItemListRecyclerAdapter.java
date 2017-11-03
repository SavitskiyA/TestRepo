package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.activities.auth.signup.SignUpListActivity;
import com.ryj.activities.filters.FiltersCategoryActivity;
import com.ryj.activities.filters.FiltersCourtTypeActivity;
import com.ryj.adapters.viewholders.CategoryHolder;
import com.ryj.adapters.viewholders.CourtTypeHolder;
import com.ryj.adapters.viewholders.DetailRatingHolder;
import com.ryj.adapters.viewholders.JudgeHolder;
import com.ryj.adapters.viewholders.SignUpItemHolder;
import com.ryj.fragments.CourtFragment;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.response.Judge;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 10/19/17.
 */

public class ItemListRecyclerAdapter<T> extends ListRecyclerAdapter<T> implements OnHolderListener {
  private LayoutInflater mInflater;
  private boolean[] mItemsSelected;
  private OnHolderListener mListener;

  public ItemListRecyclerAdapter(
          Context context,
          OnHolderListener listener,
          List<T> items,
          boolean[] itemsSelected
  ) {
    this.mItems.addAll(items);
    this.mInflater = LayoutInflater.from(context);
    this.mListener = listener;
    this.mItemsSelected = itemsSelected;
  }

  public ItemListRecyclerAdapter(
          Context context,
          OnHolderListener listener
  ) {
    this.mInflater = LayoutInflater.from(context);
    this.mListener = listener;
  }

  public ItemListRecyclerAdapter(
          Context context) {
    this.mInflater = LayoutInflater.from(context);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mListener instanceof SignUpListActivity) {
      return new SignUpItemHolder(mInflater.inflate(R.layout.item_signup_list, parent, false), this);
    } else if (mListener instanceof FiltersCategoryActivity) {
      return new CategoryHolder(mInflater.inflate(R.layout.item_signup_list, parent, false), this);
    } else if (mListener instanceof FiltersCourtTypeActivity) {
      return new CourtTypeHolder(mInflater.inflate(R.layout.item_option, parent, false), this);
    } else if (mListener instanceof CourtFragment) {
      return new JudgeHolder(mInflater.inflate(R.layout.item_judges_judge, parent, false), this);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof SignUpItemHolder) {
      bindSignUpItemHolder((SignUpItemHolder) holder, position);
      mListener.onHolderClicked(mItemsSelected[position], position);
    } else if (holder instanceof CategoryHolder) {
      bindCategoryHolder((CategoryHolder) holder, position);
    } else if (holder instanceof CourtTypeHolder) {
      bindCourtTypeHolder((CourtTypeHolder) holder, position);
    } else if (holder instanceof JudgeHolder) {
      bindJudgeHolder(holder, position);
    } else {
      throw new RuntimeException("this is not an instance of necessary parent");
    }
  }

  private void bindCourtTypeHolder(CourtTypeHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setTag(position);
    holder.setCourtType(item);
    holder.setChecked(mItemsSelected[position]);
  }

  private void bindCategoryHolder(CategoryHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setText(item);
    holder.setTag(position);
    holder.setChecked(mItemsSelected[position]);
  }

  private void bindSignUpItemHolder(SignUpItemHolder holder, int position) {
    String item = (String) mItems.get(position);
    holder.setText(item);
    holder.setTag(position);
    holder.setChecked(mItemsSelected[position]);
  }

  private void bindJudgeHolder(RecyclerView.ViewHolder viewHolder, int position) {
    JudgeHolder holder = (JudgeHolder) viewHolder;
    Judge judge = (Judge) mItems.get(position);
    holder.setTag(position);
    holder.setName(StringUtils.getFullName(judge));
    if (judge.getCourt() != null && judge.getCourt().getName() != null) {
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

  private void bindDetailRatingHolder(RecyclerView.ViewHolder viewHolder, int position) {
    DetailRatingHolder holder = (DetailRatingHolder) viewHolder;
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
    if (mListener instanceof FiltersCourtTypeActivity) {
      mListener.onHolderClicked(false, position);
      Arrays.fill(mItemsSelected, false);
      mItemsSelected[position] = true;
      notifyDataSetChanged();
    } else {
      mListener.onHolderClicked(enable, position);
    }
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public void addItems(List<T> items) {
    mItems.addAll(items);
    notifyDataSetChanged();
  }
}
