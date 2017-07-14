package com.savitskiy.testsample.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by andrey on 7/14/17.
 */

public class SpecializationsDialog extends AlertDialog {
  private AlertDialog.Builder mBuilder;
  private String[] mArray;
  private boolean[] mCheckedItems;
  private ArrayList<Integer> mUserItems;

  protected SpecializationsDialog(@NonNull Context context, String title, String[] array) {
    super(context);
    mBuilder = new AlertDialog.Builder(context).setTitle(title);
    mArray = array;
    mCheckedItems = new boolean[mArray.length];
    mUserItems = new ArrayList<>();
  }

  public void

}
