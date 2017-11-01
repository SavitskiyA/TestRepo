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
import com.ryj.adapters.ItemListRecyclerAdapter;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.interfaces.OnHolderListener;
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
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/17/17.
 */
public class CourtFragment extends BaseFragment implements OnHolderListener {
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

  private ItemListRecyclerAdapter mAdapter;
  private List<Judge> mJudgeList = new ArrayList<>();
  private SpinnerDialog mSpinnerDialog;

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
    mAdapter = new ItemListRecyclerAdapter<Judge>(inflater.getContext(), this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    mSpinnerDialog = getSpinnerDialog();
    mSpinnerDialog.setCancelable(false);
    execute(getArguments().getInt(EXTRA_COURT_ID));
    return view;
  }

  private void execute(int courtId) {
    mApi.getCourt(courtId).compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .compose(
                    RxUtils.applyBeforeAndAfter(
                            (disposable) ->
                                    mSpinnerDialog.show(getActivity().getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                            () -> mSpinnerDialog.dismiss()))
            .subscribe(
                    response -> {
                      mJudgeList.clear();
                      mJudgeList.addAll(response.getJudges());
                      mAdapter.addItems(response.getJudges());
                      setView(response);
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable, this.getContext());
                    });
  }

  private void setView(Court court) {
    if (court.getName() != null) {
      mCourt.setText(court.getName());
    } else {
      mCourt.setText(StringUtils.EMPTY_STRING);
    }
    if (court.getRatingCount() != null) {
      mCourtMarksCount.setText(court.getRatingCount());
    } else {
      mCourtMarksCount.setText(StringUtils.EMPTY_STRING);
    }
    if (court.getAvgRating() != null) {
      mCourtRating.setRating(court.getAvgRating());
    } else {
      mCourtRating.setRating(0f);
    }
    mFoundCount.setText(
            String.valueOf(court.getJudges().size())
                    + StringUtils.SPACE
                    + StringUtils.JUDGES_WERE_FOUND);
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {
  }
}
