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
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.LoadableListRecyclerAdapter;
import com.ryj.interfaces.LoadListener;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.enums.Direction;
import com.ryj.models.enums.Sort;
import com.ryj.models.filters.Filters;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;

/** Created by andrey on 8/24/17. */
public class JudgesFragment extends BaseFragment implements LoadListener, OnHolderListener {
  public static final String TAG = "JudgesFragment";
  public static final String EXTRA_PARENT_ACTIVITY = "parent_activity";
  public static final int PARENT_JUDGES = 0;
  public static final int PARENT_MOST_DISCUSSED = 1;
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Filters mFilters;

  @BindString(R.string.text_judges)
  String mTitleJudges;

  @BindString(R.string.text_most_discussed_1line)
  String mTitleMostDiscussed;

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

  @BindView(R.id.best_rated)
  TextView mBestRated;

  private LoadableListRecyclerAdapter mAdapter;
  private boolean mIsSort;
  private int mPage = 1;

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
    View view = inflater.inflate(R.layout.fragment_judges, container, false);
    ButterKnife.bind(this, view);
    onTextChanged();
    mAdapter = new LoadableListRecyclerAdapter(inflater.getContext(), this, this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    setMode(getArguments().getInt(EXTRA_PARENT_ACTIVITY, 0));
    return view;
  }

  private void setMode(int mode) {
    switch (mode) {
      case PARENT_JUDGES:
        setElementsVisible(View.VISIBLE);
        setActivityToolBarTitle(mTitleJudges);
        break;
      case PARENT_MOST_DISCUSSED:
        setElementsVisible(View.GONE);
        mFilters.setSorting(Sort.COMMENTS_COUNT);
        setActivityToolBarTitle(mTitleMostDiscussed);
        break;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setCurrentFragmentTag(TAG);
    reset();
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
        .subscribe(query -> reset());
  }

  @OnClick({R.id.sort, R.id.search_cancel})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sort:
        if (mIsSort) {
          mFilters.setDirection(Direction.ASC);
          reset();
          mIsSort = false;
        } else {
          mFilters.setDirection(Direction.DESC);
          reset();
          mIsSort = true;
        }
        break;
      case R.id.search_cancel:
        mSearch.setText(StringUtils.EMPTY_STRING);
        reset();
        break;
    }
  }

  @Override
  public void load(int page) {
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
                mAdapter.reloadItems(response.getObjects());
              } else {
                mAdapter.addItems(response.getObjects());
              }
              if (mSearch.length() > 0) {
                mFrameFoundCount.setVisibility(View.VISIBLE);
                mFound.setVisibility(View.VISIBLE);
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

  public void setElementsVisible(int visible) {
    mBestRated.setVisibility(visible);
    mSort.setVisibility(visible);
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {}
}
