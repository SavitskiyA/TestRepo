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
import com.ryj.adapters.LoadableAdapter;
import com.ryj.listeners.LoadListener;
import com.ryj.listeners.OnHolderListener;
import com.ryj.models.Filters;
import com.ryj.models.enums.Direction;
import com.ryj.models.response.Court;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by andrey on 8/24/17.
 */
public class CourtsFragment extends BaseFragment implements LoadListener, OnHolderListener {
  public static final String TAG = "CourtsFragment";
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
  @Inject
  Filters mFilters;

  @BindString(R.string.text_courts)
  String mTitle;

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

  private LoadableAdapter mAdapter;
  private boolean mIsSort;
  private int mPage = 1;
  private List<Court> mCourtList = new ArrayList<>();

  public static CourtsFragment newInstance() {
    return new CourtsFragment();
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
    View view = inflater.inflate(R.layout.fragment_courts, container, false);
    ButterKnife.bind(this, view);
    onTextChanged();
    mAdapter = new LoadableAdapter(inflater.getContext(), this, this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    setActivityToolBarTitle(mTitle);
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
    mApi.getCourts(
            mSearch.getText().toString(),
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
                      mFoundCount.setText(String.valueOf(response.getTotalEntries()));
                      if (page == 1) {
                        mCourtList.clear();
                        mCourtList.addAll(response.getObjects());
                        mAdapter.reloadItems(mCourtList);
                      } else {
                        mCourtList.addAll(response.getObjects());
                        mAdapter.addItems(mCourtList);
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

  @Override
  public void onHolderClicked(boolean enable, int position) {
    Court court = mCourtList.get(position);
    replaceFragment(
            CourtFragment.newInstance(court.getId(),
                    court.getName(),
                    34,
                    4f),
            R.id.container,
            true,
            false,
            CourtFragment.TAG);

  }
}
