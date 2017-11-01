package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.interfaces.LoadListener;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.enums.UserType;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/25/17.
 */

public class JudgeFragment extends BaseFragment implements LoadListener, OnHolderListener {
  public static final String TAG = "JudgeFragment";
  private static final String EXTRA_JUDGE_ID = "judge_id";
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
  @Inject
  Prefs mPrefs;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.abbr)
  TextView mAbbr;

  @BindView(R.id.back)
  ImageView mBack;

  @BindView(R.id.settings)
  ImageView mSettings;

  @BindView(R.id.edit)
  ImageView mEdit;

  @BindView(R.id.favourites)
  ImageView mFavourites;

  @BindView(R.id.comments_count)
  TextView mCommentsCount;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  @BindView(R.id.comment)
  Button mComment;

  @BindView(R.id.estimate)
  Button mEstimate;

  @BindView(R.id.frame_buttons)
  LinearLayout mButtons;

  @BindView(R.id.marks_recycler_view)
  RecyclerView mMarks;

  @BindView(R.id.comments_recycler_view)
  RecyclerView mComments;

  public static JudgeFragment newInstance(int judgeId) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_JUDGE_ID, judgeId);
    JudgeFragment instance = new JudgeFragment();
    instance.setArguments(bundle);
    return instance;
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_judge, container, false);
    ButterKnife.bind(this, view);
    configProfileIcons(getArguments().getInt(EXTRA_JUDGE_ID), mPrefs.getCurrentUserId(), mPrefs.getUserType());
    return view;
  }

  private void configProfileIcons(int judgeId, int currentId, UserType type) {
    if (judgeId == currentId) {
      mBack.setVisibility(View.GONE);
      mSettings.setVisibility(View.VISIBLE);
      mEdit.setVisibility(View.VISIBLE);
      mFavourites.setVisibility(View.GONE);
      mButtons.setVisibility(View.GONE);
      return;
    }
    if (type == UserType.LAWYER) {
      mBack.setVisibility(View.VISIBLE);
      mSettings.setVisibility(View.GONE);
      mEdit.setVisibility(View.GONE);
      mFavourites.setVisibility(View.VISIBLE);
      mButtons.setVisibility(View.VISIBLE);
      return;
    }
    mButtons.setVisibility(View.GONE);
    mBack.setVisibility(View.VISIBLE);
    mSettings.setVisibility(View.GONE);
    mEdit.setVisibility(View.GONE);
    mFavourites.setVisibility(View.GONE);
  }

  @Override
  public void load(int page) {

  }

  @Override
  public int increment() {
    return 0;
  }

  @Override
  public void reset() {

  }

  @Override
  public void onHolderClicked(boolean enable, int position) {

  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setActivityToolbarVisibility(View.GONE);
  }

  @Override
  public void onPause() {
    super.onPause();
    setActivityToolbarVisibility(View.VISIBLE);
  }

}
