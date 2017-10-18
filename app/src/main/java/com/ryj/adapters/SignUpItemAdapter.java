package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.SignUpItemHolder;
import com.ryj.listeners.OnHolderListener;

import java.util.ArrayList;

/**
 * Created by andrey on 7/22/17.
 */

public class SignUpItemAdapter extends RecyclerView.Adapter<SignUpItemHolder> implements OnHolderListener {
  private String[] mCategories;
  private ArrayList<String> mChoosenCategoriesList = new ArrayList<>();
  private boolean[] mChoosenCategoriesBooleans;
  private LayoutInflater mInflater;
  private OnHolderListener mOnButtonEnableListener;


  public SignUpItemAdapter(String[] categories, boolean[] choosenCategories, OnHolderListener onButtonEnableListener, Context context) {
    this.mCategories = categories;
    this.mChoosenCategoriesBooleans = choosenCategories;
    this.mOnButtonEnableListener = onButtonEnableListener;
    this.mInflater = LayoutInflater.from(context);
    this.fillCategoriesList();
    if (mChoosenCategoriesList.size() > 0) {
      mOnButtonEnableListener.onHolderClicked(true, 0);
    }
  }

  @Override
  public SignUpItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_signup_list, parent, false);
    return new SignUpItemHolder(view, this);
  }

  @Override
  public void onBindViewHolder(SignUpItemHolder holder, int position) {
    holder.setText(mCategories[position]);
    holder.setTag(position);
    holder.setChecked(mChoosenCategoriesBooleans[position]);
  }

  @Override
  public int getItemCount() {
    return mCategories.length;
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
    if (enable) {
      if (mChoosenCategoriesBooleans[position]) {
        mOnButtonEnableListener.onHolderClicked(enable, 0);
      } else {
        mChoosenCategoriesBooleans[position] = enable;
        mChoosenCategoriesList.add(mCategories[position]);
        mOnButtonEnableListener.onHolderClicked(enable, 0);
      }
    } else {
      if (mChoosenCategoriesList.contains(mCategories[position])) {
        mChoosenCategoriesList.remove(mCategories[position]);
        mChoosenCategoriesBooleans[position] = enable;
      }
      if (mChoosenCategoriesList.size() == 0) {
        mOnButtonEnableListener.onHolderClicked(enable, 0);
      }
    }
  }

  private void fillCategoriesList() {
    for (int i = 0; i < mChoosenCategoriesBooleans.length; i++) {
      if (mChoosenCategoriesBooleans[i]) {
        mChoosenCategoriesList.add(mCategories[i]);
      }
    }
  }

  public ArrayList<String> getChoosenCategoriesList() {
    return mChoosenCategoriesList;
  }

  public boolean[] getChoosenCategoriesBooleans() {
    return mChoosenCategoriesBooleans;
  }
}
