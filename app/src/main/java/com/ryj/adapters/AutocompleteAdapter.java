package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.ryj.R;
import com.ryj.adapters.viewholders.AutoCompleteHolder;
import com.ryj.adapters.viewholders.LoaderViewHolder;
import com.ryj.interfaces.LoadListener;
import com.ryj.interfaces.Loadable;
import com.ryj.models.response.Judge;
import com.ryj.utils.StringUtils;

import java.util.List;

/**
 * Created by andrey on 10/18/17.
 */

public class AutocompleteAdapter extends ListBaseAdapter<Judge> implements Loadable<Judge>, Filterable {
  private LayoutInflater mInflater;
  private LoadListener mLoadable;

  public AutocompleteAdapter(Context context, LoadListener loadable) {
    mInflater = LayoutInflater.from(context);
    mLoadable = loadable;
  }

  @Override
  public View createView(int position, View convertView, ViewGroup parent) {
    loadMore(position);
    if (convertView == null) {
      if (isLoaderView(position)) {
        convertView = mInflater.inflate(R.layout.item_load, parent, false);
        convertView.setTag(VIEWTYPE_LOADER);
        bindLoaderHolder(new LoaderViewHolder(convertView));
        return convertView;
      } else {
        convertView = mInflater.inflate(R.layout.item_signup_judge_autocomplete, parent, false);
        convertView.setTag(VIEWTYPE_ITEM);
        bindItemHolder(new AutoCompleteHolder(convertView), position);
        return convertView;
      }
    } else if ((Integer) convertView.getTag() == VIEWTYPE_LOADER) {
      if (isLoaderView(position)) {
        bindLoaderHolder(new LoaderViewHolder(convertView));
        return convertView;
      } else {
        convertView = mInflater.inflate(R.layout.item_signup_judge_autocomplete, parent, false);
        convertView.setTag(VIEWTYPE_ITEM);
        bindItemHolder(new AutoCompleteHolder(convertView), position);
        return convertView;
      }
    } else {
      if (isLoaderView(position)) {
        convertView = mInflater.inflate(R.layout.item_load, parent, false);
        convertView.setTag(VIEWTYPE_LOADER);
        bindLoaderHolder(new LoaderViewHolder(convertView));
        return convertView;
      } else {
        bindItemHolder(new AutoCompleteHolder(convertView), position);
        return convertView;
      }
    }
  }

  @Override
  public void addItems(List<Judge> judges) {
    mItems.addAll(judges);
    notifyDataSetChanged();
  }

  @Override
  public void reloadItems(List<Judge> judges) {
    mItems.clear();
    mItems.addAll(judges);
    notifyDataSetChanged();
  }

  @Override
  public void loadMore(int position) {
    if (position == mItems.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
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

  }

  @Override
  public void bindItemHolder(RecyclerView.ViewHolder viewHolder, int position) {
    AutoCompleteHolder holder = (AutoCompleteHolder) viewHolder;
    holder.setName(StringUtils.getFullName(mItems.get(position)));
    holder.setCourt(mItems.get(position).getCourt().getName());
  }

  @Override
  public void setIsLoadable(boolean load) {
    mIsLoad = load;
  }

  @Override
  public Filter getFilter() {
    Filter filter =
            new Filter() {
              @Override
              protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = mItems;
                filterResults.count = mItems.size();
                return null;
              }

              @Override
              protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                  notifyDataSetChanged();
                } else {
                  notifyDataSetInvalidated();
                }
              }
            };
    return filter;
  }
}
