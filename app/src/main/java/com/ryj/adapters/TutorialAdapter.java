package com.ryj.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.TutorialHolder;


/**
 * Created by andrey on 7/6/17.
 */

public class TutorialAdapter extends PagerAdapter {
  private String[] mStrings;
  private LayoutInflater mInflater;
  private TypedArray mImages;

  public TutorialAdapter(String[] strings, TypedArray images, Context context) {
    mStrings = strings;
    mImages = images;
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
    TutorialHolder viewHolder = new TutorialHolder(view);
    viewHolder.setText(mStrings[position]);
    viewHolder.setImage(mImages.getDrawable(position));
    container.addView(view);
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}
