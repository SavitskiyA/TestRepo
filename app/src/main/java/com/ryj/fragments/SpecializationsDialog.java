package com.ryj.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.ryj.App;
import com.ryj.eventbus.CategoryEvent;
import com.ryj.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by andrey on 7/14/17.
 */

public class SpecializationsDialog extends AlertDialog {
  @Inject
  EventBus mBus;
  private Builder mBuilder;
  private String[] mArray;
  private boolean[] mCheckedItems;
  private ArrayList<Integer> mUserItems;

  public SpecializationsDialog(@NonNull Context context, String title, String[] array) {
    super(context);
    mBuilder = new Builder(context).setTitle(title);
    mArray = array;
    mCheckedItems = new boolean[mArray.length];
    mUserItems = new ArrayList<>();
    App.get().getComponent().inject(this);
  }

  public void setCancelable(boolean flag) {
    mBuilder.setCancelable(flag);
  }

  public void enableMultiChoiceItemsListener() {
    mBuilder.setMultiChoiceItems(mArray, mCheckedItems, new OnMultiChoiceClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
          if (!mUserItems.contains(which)) {
            mUserItems.add(which);
          }
        } else if (mUserItems.contains(which)) {
          mUserItems.remove(mUserItems.indexOf(which));
        }
      }
    });
  }

  public void enablePositiveButton(String title) {
    mBuilder.setPositiveButton(title, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mUserItems.size(); i++) {
          stringBuilder.append(mArray[mUserItems.get(i)]);
          if (i != mUserItems.size() - 1) {
            stringBuilder.append(StringUtils.COMA + StringUtils.SPACE);
          }
        }
        mBus.post(new CategoryEvent(stringBuilder.toString()));
      }
    });
  }

  public void enableNegativeButton(String title) {
    mBuilder.setNegativeButton(title, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
  }

  public void enableNeutralButton(String title) {
    mBuilder.setNeutralButton(title, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        for (int i = 0; i < mCheckedItems.length; i++) {
          mCheckedItems[i] = false;
          mUserItems.clear();
          mBus.post(new CategoryEvent(StringUtils.EMPTY_STRING));
        }
      }
    });
  }

  public void show() {
    AlertDialog dialog = mBuilder.create();
    dialog.show();
  }
}
