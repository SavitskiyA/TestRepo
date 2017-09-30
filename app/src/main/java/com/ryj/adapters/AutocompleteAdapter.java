package com.ryj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.adapters.viewholders.AutoCompleteHolder;
import com.ryj.listeners.Loadable;
import com.ryj.models.response.Judge;
import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

/** Created by andrey on 8/17/17. */
public class AutocompleteAdapter extends BaseAdapter implements Filterable {
  @BindView(R.id.fullname)
  TextView mName;

  @BindView(R.id.court)
  TextView mCourt;

  private LayoutInflater mInflater;

  private List<Judge> mJudges = new ArrayList<Judge>();
  private Loadable mLoadable;

  private boolean mIsLoad;

  public AutocompleteAdapter(Context context, Loadable loadable) {
    this.mInflater = LayoutInflater.from(context);
    this.mLoadable = loadable;
  }

  @Override
  public int getCount() {
    return mJudges.size();
  }

  @Override
  public Object getItem(int position) {
    return mJudges.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.item_signup_judge_autocomplete, parent, false);
    }
    AutoCompleteHolder holder = new AutoCompleteHolder(convertView);
    holder.setName(StringUtils.getFullName(mJudges.get(position)));
    holder.setCourt(mJudges.get(position).getCourt().getTitle());
    if (position == mJudges.size() - 1 && mIsLoad) {
      mLoadable.load(mLoadable.increment());
    }
    return convertView;
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
  public Filter getFilter() {
    Filter filter =
        new Filter() {
          @Override
          protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            filterResults.values = mJudges;
            filterResults.count = mJudges.size();
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

  public void setIsLoadable(boolean load) {
    mIsLoad = load;
  }
}
