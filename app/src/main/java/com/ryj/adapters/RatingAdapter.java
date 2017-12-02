package com.ryj.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.RatingHolder;
import com.ryj.models.response.Rating;

/** Created by andrey on 11/30/17. */
public class RatingAdapter<T> extends BaseRecyclerAdapter<T> {
  public RatingAdapter(Context context) {
    super(context);
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new RatingHolder(mInflater.inflate(R.layout.item_mark, parent, false));
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    RatingHolder holder = (RatingHolder) viewHolder;
    Rating rating = (Rating) mItems.get(position);
    holder.setTitle(rating.getTitle());
    holder.setValue(String.valueOf(rating.getValue()));
  }
}
