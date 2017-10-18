package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.LoadableAdapter;
import com.ryj.listeners.LoadListener;
import com.ryj.listeners.OnHolderListener;
import com.ryj.models.response.Judge;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/17/17.
 */

public class CourtFragment extends BaseFragment implements LoadListener, OnHolderListener {
  public static final String TAG = "CourtFragment";
  public static final String EXTRA_COURT_ID = "court_id";
  public static final String EXTRA_COURT_NAME = "court_name";
  public static final String EXTRA_COURT_MARKS_COUNT = "court_marks_count";
  public static final String EXTRA_COURT_RATING = "court_rating";

  @BindString(R.string.text_court)
  public String mTitle;
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;

  @BindView(R.id.courts_found_count)
  TextView mFoundCount;

  @BindView(R.id.court_name)
  TextView mCourt;

  @BindView(R.id.ratingbar)
  RatingView mCourtRating;

  @BindView(R.id.marks_count)
  TextView mCourtMarksCount;

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  private LoadableAdapter mAdapter;
  private int mCourtId;
  private List<Judge> mJudgeList = new ArrayList<>();
  private int mPage = 1;


  public static CourtFragment newInstance(int courtId, String courtName, int courtMarksCount, float courtRating) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_COURT_ID, courtId);
    bundle.putString(EXTRA_COURT_NAME, courtName);
    bundle.putInt(EXTRA_COURT_MARKS_COUNT, courtMarksCount);
    bundle.putFloat(EXTRA_COURT_RATING, courtRating);
    CourtFragment instance = new CourtFragment();
    instance.setArguments(bundle);
    return instance;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    setActivityToolBarTitle(mTitle);
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setActivityOptionsMenuVisibility(false);
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
    setActivityOptionsMenuVisibility(true);
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_court, container, false);
    ButterKnife.bind(this, view);
    mAdapter = new LoadableAdapter(inflater.getContext(), this, this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    setView(getArguments());
    reset();
    return view;
  }

  private void setView(Bundle bundle) {
    mCourt.setText(bundle.getString(EXTRA_COURT_NAME));
    mCourtMarksCount.setText(bundle.getString(EXTRA_COURT_MARKS_COUNT));
    mCourtRating.setRating(bundle.getFloat(EXTRA_COURT_RATING));
    mCourtId = bundle.getInt(EXTRA_COURT_ID);
  }

  @Override
  public void load(int page) {
    mApi.getJudges(mCourtId,
            page)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                    response -> {
                      if (response.getTotalEntries() != null) {
                        mFoundCount.setText(String.valueOf(response.getTotalEntries()) + StringUtils.SPACE + StringUtils.JUDGES_WERE_FOUND);
                      } else {
                        mFoundCount.setText(StringUtils.ZERO + StringUtils.SPACE + StringUtils.JUDGES_WERE_FOUND);
                      }
                      if (page == 1) {
                        mJudgeList.clear();
                        mJudgeList.addAll(response.getObjects());
                        mAdapter.reloadItems(mJudgeList);
                      } else {
                        mJudgeList.addAll(response.getObjects());
                        mAdapter.addItems(mJudgeList);
                      }
                      if (response.getNextPage() == null) {
                        mAdapter.setIsLoadable(false);
                      }
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable, this.getContext());
                    });
  }

  @Override
  public int increment() {
    return mPage++;
  }

  @Override
  public void reset() {
    mPage = 1;
    mAdapter.setIsLoadable(true);
    load(mPage);
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
  }
}
