package com.ryj.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 7/7/17.
 */

public class TutorialViewHolder {
  @BindView(R.id.text_tutorial)
  TextView mText;
  @BindView(R.id.image_tutorial)
  ImageView mImage;

  public TutorialViewHolder(View view) {
    ButterKnife.bind(this, view);
  }

  public void setText(String text) {
    mText.setText(text);
  }
}
