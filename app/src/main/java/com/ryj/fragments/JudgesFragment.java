package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.filters.FiltersActivity;
import com.ryj.adapters.JudgeAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;
import com.ryj.models.filters.Filters;
import com.ryj.models.response.Judge;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.EndlessRecyclerOnScrollListener;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;

/** Created by andrey on 8/24/17. */
public class JudgesFragment extends BaseFragment implements OnHolderClickListener {
  public static final String TAG = "JudgesFragment";
  public static final String EXTRA_PARENT_ACTIVITY = "parent_activity";
  public static final int PARENT_JUDGES = 0;
  public static final int PARENT_MOST_DISCUSSED = 1;
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Filters mFilters;
  @Inject Prefs mPrefs;

  @BindString(R.string.text_judges)
  String mTitleJudges;

  @BindString(R.string.text_most_discussed_1line)
  String mTitleMostDiscussed;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;

  @BindView(R.id.search)
  EditText mSearch;

  @BindView(R.id.sort)
  ImageView mSort;

  @BindView(R.id.search_cancel)
  ImageView mSearchCancel;

  @BindView(R.id.frame_found_count)
  FrameLayout mFrameFoundCount;

  @BindView(R.id.found_count)
  TextView mFoundCount;

  @BindView(R.id.found)
  TextView mFound;

  @BindView(R.id.rated)
  TextView mRatedText;

  @BindString(R.string.text_lowest_rated)
  String mLowestRated;

  @BindString(R.string.text_best_rated)
  String mBestRated;

  private JudgeAdapter<Judge> mAdapter;
  private boolean mIsSort;
  private int mPage = 1;
  private List<Judge> mJudgeList = new ArrayList<>();
  private int mMode;
  private EndlessRecyclerOnScrollListener mOnScrollListener;

  public static JudgesFragment newInstance(int parent) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_PARENT_ACTIVITY, parent);
    JudgesFragment instance = new JudgesFragment();
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
    View view = bindView(inflater.inflate(R.layout.fragment_judges, container, false));
    onTextChanged();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
    mAdapter = new JudgeAdapter<Judge>(inflater.getContext(), this);
    mAdapter.setPaginationEnable(true);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mRecyclerView.setAdapter(mAdapter);
    mOnScrollListener =
        new EndlessRecyclerOnScrollListener(linearLayoutManager) {
          @Override
          public void onLoadMore(int current_page) {
            if (mAdapter.isLoading()) load(current_page);
          }
        };
    mRecyclerView.addOnScrollListener(mOnScrollListener);
    mFilters.setSorting(Sort.AVG_RATING);
    setMode(getArguments().getInt(EXTRA_PARENT_ACTIVITY, 0));
    setRated();
    setHasOptionsMenu(true);
    return view;
  }

  private void setRated() {
    if (mFilters.getDirection() == Direction.ASC) {
      mRatedText.setText(mLowestRated);
    } else {
      mRatedText.setText(mBestRated);
    }
  }

  private void setMode(int mode) {
    mMode = mode;
    switch (mode) {
      case PARENT_JUDGES:
        setElementsVisible(View.VISIBLE);
        mTitle.setText(mTitleJudges);
        break;
      case PARENT_MOST_DISCUSSED:
        setElementsVisible(View.GONE);
        mFilters.setSorting(Sort.COMMENTS_COUNT);
        mTitle.setText(mTitleMostDiscussed);
        break;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setCurrentFragmentTag(TAG);
    load(Constants.VALUE_ONE);
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mFilters.clear();
  }

  @OnTextChanged(R.id.search)
  public void onEditTextChanged() {
    if (mSearch.length() > 0) {
      mSearchCancel.setVisibility(View.VISIBLE);
    } else {
      mSearchCancel.setVisibility(View.INVISIBLE);
      mFrameFoundCount.setVisibility(View.GONE);
      mFound.setVisibility(View.GONE);
    }
  }

  private void onTextChanged() {
    RxTextView.textChanges(mSearch)
        .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .compose(bindUntilEvent(FragmentEvent.STOP))
        .subscribe(query -> load(Constants.VALUE_ONE));
  }

  @OnClick({R.id.sort, R.id.search_cancel, R.id.back, R.id.filters})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sort:
        if (mIsSort) {
          mFilters.setDirection(Direction.ASC);
          mRatedText.setText(mLowestRated);
          load(Constants.VALUE_ONE);
          mIsSort = false;
        } else {
          mFilters.setDirection(Direction.DESC);
          mRatedText.setText(mBestRated);
          load(Constants.VALUE_ONE);
          mIsSort = true;
        }
        break;
      case R.id.search_cancel:
        mSearch.setText(StringUtils.EMPTY_STRING);
        load(Constants.VALUE_ONE);
        break;
      case R.id.back:
        getActivity().onBackPressed();
        break;
      case R.id.filters:
        FiltersActivity.start(this.getContext(), FiltersActivity.PARENT_JUDGES);
        break;
    }
  }

  public void load(int page) {
    doRequest(
        mApi.getJudges(
                mSearch.getText().toString(),
                mFilters.getCourtId(),
                mFilters.getAffairs(),
                mFilters.getCourtType(),
                mFilters.getCityId(),
                mFilters.getRegionId(),
                mFilters.getSorting().toString(),
                mFilters.getDirection().toString(),
                page)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  if (response.getTotalEntries() != null) {
                    mFoundCount.setText(String.valueOf(response.getTotalEntries()));
                  } else {
                    mFoundCount.setText(StringUtils.ZERO);
                  }
                  if (page == 1) {
                    mJudgeList.clear();
                    mJudgeList.addAll(response.getObjects());
                    mAdapter.reloadItems(response.getObjects());
                  } else {
                    mJudgeList.addAll(response.getObjects());
                    mAdapter.addItems(response.getObjects());
                  }
                  if (mSearch.length() > 0) {
                    mFrameFoundCount.setVisibility(View.VISIBLE);
                    mFound.setVisibility(View.VISIBLE);
                  }
                  if (response.getNextPage() == null) {
                    mAdapter.setIsLoading(false);
                  } else {
                    mAdapter.setIsLoading(true);
                  }
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  public void setElementsVisible(int visible) {
    mRatedText.setVisibility(visible);
    mSort.setVisibility(visible);
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    Judge judge = mJudgeList.get(position);
    replaceFragment(
        JudgeFragment.newInstance(judge.getId()), R.id.container, true, false, JudgeFragment.TAG);
  }
}
