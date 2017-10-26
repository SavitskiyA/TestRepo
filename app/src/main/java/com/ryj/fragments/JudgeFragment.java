package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.LoadableListRecyclerAdapter;
import com.ryj.interfaces.LoadListener;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/25/17.
 */

public class JudgeFragment extends BaseFragment implements LoadListener, OnHolderListener {
  public static final String TAG = "JudgeFragment";
  private static final String EXTRA_PRIVATE_SETTINGS = "private_settings";
  private static final String EXTRA_JUDGE_ID = "judge_id";
  private static final String EXTRA_JUDGE_FIRST_NAME = "judge_first_name";
  private static final String EXTRA_JUDGE_LAST_NAME = "judge_last_name";
  private static final String EXTRA_COURT = "court";
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;

  @BindView(R.id.settings)
  ImageView mSettings;

  @BindView(R.id.edit)
  ImageView mEdit;

  public static CourtFragment newInstance(
          int judgeId, String courtName, int courtMarksCount, float courtRating) {
    Bundle bundle = new Bundle();

    CourtFragment instance = new CourtFragment();
    instance.setArguments(bundle);
    return instance;
  }


  private LoadableListRecyclerAdapter mAdapter;
  private int mJudgeId;
//  private List<Mark> mMarkList = new ArrayList<>();
  private List<Comment> mCommentList = new ArrayList<>();
  private int mPage = 1;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    getComponent().inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_judge, container, false);
    ButterKnife.bind(this, view);
    mAdapter = new LoadableListRecyclerAdapter(inflater.getContext(), this, this);
    setView(getArguments());
    reset();
    return view;
  }

  private void setView(Bundle arguments) {
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
