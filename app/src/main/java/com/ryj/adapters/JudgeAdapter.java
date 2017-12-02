package com.ryj.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.BaseViewHolder;
import com.ryj.adapters.viewholders.JudgeHolder;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.response.Judge;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.StringUtils;

/** Created by andrey on 11/29/17. */
public class JudgeAdapter<T> extends BaseRecyclerAdapter<T> {
  private OnHolderClickListener mOnHolderClickListener;

  public JudgeAdapter(Context context, OnHolderClickListener onHolderClickListener) {
    super(context);
    mOnHolderClickListener = onHolderClickListener;
  }

  @Override
  public BaseViewHolder createHolder(ViewGroup parent) {
    return new JudgeHolder(
        mInflater.inflate(R.layout.item_judges_judge, parent, false), mOnHolderClickListener);
  }

  @Override
  public void bindItemHolder(BaseViewHolder viewHolder, int position) {
    JudgeHolder holder = (JudgeHolder) viewHolder;
    Judge judge = (Judge) mItems.get(position);
    holder.setTag(position);
    holder.setName(StringUtils.getFullName(judge));
    holder.setCourt(String.valueOf(judge.getCourt().getName()));
    holder.setCommentsCount(String.valueOf(judge.getCommentsCount()));
    holder.setRating(judge.getRating() / 2);
    holder.setMarksCount(String.valueOf(judge.getRatingCount()));
    if (judge.getAvatar().getOrigin() != null) {
      holder.setPhoto(Uri.parse(judge.getAvatar().getOrigin()));
      holder.hideTextStub();
    } else {
      holder.setPlaceHolder(
          DrawUtils.RESOURCES[StringUtils.getFullNameLength(judge) % DrawUtils.RESOURCES.length]);
      holder.showTextStub(StringUtils.getAbbrFromFullName(judge));
    }
  }
}
