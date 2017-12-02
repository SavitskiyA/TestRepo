package com.ryj.adapters.viewholders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 7/7/17.
 */

public class TutorialHolder {
  @BindView(R.id.text)
  TextView mText;
  @BindView(R.id.image)
  ImageView mImage;

  public TutorialHolder(View view) {
    ButterKnife.bind(this, view);
  }

  public void setText(String text) {
    mText.setText(text);
  }

  public void setImage(Drawable image) {
    mImage.setImageDrawable(image);
  }
}
