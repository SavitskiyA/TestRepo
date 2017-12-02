package com.ryj.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.activities.EstimationActivity;


import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by andrey on 9/27/17. */
public class EstimationDialog extends DialogFragment {
  @BindView(R.id.case_number)
  EditText mCaseNumber;

  @BindView(R.id.send)
  Button mSend;

  @BindString(R.string.text_ok)
  String mOk;

  @BindString(R.string.text_number_of_case_must_not_be_blank)
  String mCaseNumberMustNotBeBlank;

  private AlertDialog mDialog;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    View view = inflater.inflate(R.layout.dialog_estimation, container, false);
    ButterKnife.bind(this, view);
    mDialog = ((BaseActivity) getActivity()).getDialog(mOk);
    return view;
  }

  @OnClick({R.id.cancel, R.id.send})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.cancel:
        dismiss();
        return;
      case R.id.send:
        if (mCaseNumber.length() != 0) {
          dismiss();
          EstimationActivity.start(this.getContext());
          return;
        } else {
          mDialog.setMessage(mCaseNumberMustNotBeBlank);
          mDialog.show();
          return;
        }
    }
  }
}
