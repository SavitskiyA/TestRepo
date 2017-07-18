package com.ryj.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.TutorialViewHolder;


/**
 * Created by andrey on 7/6/17.
 */

public class TutorialPagerAdapter extends PagerAdapter {
  private String[] mStrings;
  private LayoutInflater mInflater;

  public TutorialPagerAdapter(String[] strings, Context context) {
    this.mStrings = strings;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return mStrings.length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View view = mInflater.inflate(R.layout.page_tutorial, null);
    TutorialViewHolder viewHolder = new TutorialViewHolder(view);
    viewHolder.setText(mStrings[position]);
    container.addView(view);
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}
