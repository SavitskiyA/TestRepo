package com.ryj.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by andrey on 8/17/17. */
public class AutoCompleteHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.fullname)
  TextView mName;

  @BindView(R.id.court)
  TextView mCourt;

  public AutoCompleteHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public String getName() {
    return mName.getText().toString();
  }

  public void setName(String text) {
    mName.setText(text);
  }

  public String getCourt() {
    return mCourt.getText().toString();
  }

  public void setCourt(String court) {
    mCourt.setText(court);
  }
}
