package com.savitskiy.testsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SpinnerAdapter;

/**
 * Created by andrey on 7/13/17.
 */

public class CustomSpinnerAdapterCheckBox extends BaseAdapter implements SpinnerAdapter {
  private LayoutInflater mInflater;
  private String[] mStrings;
  private StringBuilder mBuilder;

  public CustomSpinnerAdapterCheckBox(String[] strings, Context context) {
    mStrings = strings;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return mStrings.length;
  }

  @Override
  public Object getItem(int position) {
    return mStrings[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = mInflater.inflate(R.layout.spinner_item, null);
    CustomSpinnerCheckBoxViewHolder viewHolder = new CustomSpinnerCheckBoxViewHolder(view);
    if (mBuilder.length() == 0) {
      viewHolder.setText(mStrings[position]);
    } else {
      viewHolder.setText(mBuilder.toString());
    }
    if (position == 0) {
      viewHolder.setGray();
    } else {
      viewHolder.setBlack();
    }

    return view;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    View view = mInflater.inflate(R.layout.spinner_item_check_box, null);
    CustomSpinnerCheckBoxViewHolder viewHolder = new CustomSpinnerCheckBoxViewHolder(view);
    viewHolder.setText(mStrings[position]);
    if (position == 0) {
      viewHolder.setGray();
    } else {
      viewHolder.setBlack();
    }
    viewHolder.getCheckbox().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mBuilder.append(((CheckBox) v).getText()).append(",");
      }
    });
    return view;
  }

}
