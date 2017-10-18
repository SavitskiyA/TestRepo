package com.ryj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryj.R;
import com.ryj.adapters.viewholders.CategoryHolder;
import com.ryj.listeners.OnHolderListener;

/** Created by andrey on 9/20/17. */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>
    implements OnHolderListener {
  private String[] mCategories;
  private LayoutInflater mInflater;
  private boolean[] mSelectedCategoriesBoolean;

  public CategoryAdapter(
      Context context, String[] categories, boolean[] selectedCategoriesBoolean) {
    mCategories = categories;
    this.mInflater = LayoutInflater.from(context);
    if (selectedCategoriesBoolean != null) {
      this.mSelectedCategoriesBoolean = selectedCategoriesBoolean;
    } else {
      this.mSelectedCategoriesBoolean = new boolean[mCategories.length];
    }
  }

  @Override
  public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_signup_list, parent, false);
    return new CategoryHolder(view, this);
  }

  @Override
  public void onBindViewHolder(CategoryHolder holder, int position) {
    holder.setTag(position);
    holder.setText(mCategories[position]);
    holder.setChecked(mSelectedCategoriesBoolean[position]);
  }

  @Override
  public int getItemCount() {
    return mCategories.length;
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
    mSelectedCategoriesBoolean[position] = enable;
  }

  public boolean[] getSelectedCategoriesBoolean() {
    return mSelectedCategoriesBoolean;
  }
}
