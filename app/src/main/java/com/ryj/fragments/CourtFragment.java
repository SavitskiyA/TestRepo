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
import com.ryj.adapters.JudgeAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.response.Court;
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
import butterknife.OnClick;

public class CourtFragment extends BaseFragment implements OnHolderClickListener {
  public static final String TAG = "CourtFragment";
  public static final String EXTRA_COURT_ID = "court_id";
  public static final String EXTRA_COURT_NAME = "court_name";
  public static final String EXTRA_COURT_MARKS_COUNT = "court_marks_count";
  public static final String EXTRA_COURT_RATING = "court_rating";

  @BindString(R.string.text_court)
  public String mTitle;

  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;

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

  private JudgeAdapter mAdapter;
  private int mCourtId;
  private List<Judge> mJudgeList = new ArrayList<>();

  public static CourtFragment newInstance(
      int courtId, String courtName, int courtMarksCount, float courtRating) {
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
    mCourtId = getArguments().getInt(EXTRA_COURT_ID);
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
  }

  @Nullable
  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = bindView(inflater.inflate(R.layout.fragment_court, container, false));
    mAdapter = new JudgeAdapter<Judge>(inflater.getContext(), this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    getCourt();
    return view;
  }

  private void setView(Court court) {
    mCourt.setText(court.getName());
    mCourtMarksCount.setText(String.valueOf(court.getRatingCount()));
    mCourtRating.setRating(court.getRating());
  }

  public void getCourt() {
    doRequest(
        mApi.getCourt(mCourtId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  mFoundCount.setText(
                      new StringBuilder()
                          .append(response.getJudges().size())
                          .append(StringUtils.SPACE)
                          .append(StringUtils.JUDGES_WERE_FOUND));
                  setView(response);
                  mJudgeList.clear();
                  mJudgeList.addAll(response.getJudges());
                  mAdapter.reloadItems(response.getJudges());
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    Judge judge = mJudgeList.get(position);
    replaceFragment(
        JudgeFragment.newInstance(judge.getId()), R.id.container, true, false, JudgeFragment.TAG);
  }

  @OnClick(R.id.back)
  void onClick(View v) {
    switch (v.getId()) {
      case R.id.back:
        getActivity().onBackPressed();
        break;
    }
  }
}
