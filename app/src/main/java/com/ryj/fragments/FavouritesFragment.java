package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.FavouriteAdapter;
import com.ryj.interfaces.OnDeleteClickListener;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.models.filters.Filters;
import com.ryj.models.response.Judge;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.RxUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 8/24/17. */
public class FavouritesFragment extends BaseFragment
    implements OnHolderClickListener, OnDeleteClickListener {
  public static final String TAG = "FavouritesFragment";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Prefs mPrefs;
  @Inject Filters mFilters;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;

  private FavouriteAdapter<Judge> mAdapter;
  private List<Judge> mJudgeList = new ArrayList<>();

  public static FavouritesFragment newInstance() {
    Bundle bundle = new Bundle();
    FavouritesFragment instance = new FavouritesFragment();
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
    View view = bindView(inflater.inflate(R.layout.fragment_favourites_judges, container, false));
    mAdapter = new FavouriteAdapter<Judge>(inflater.getContext(), this, this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    mRecyclerView.setAdapter(mAdapter);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setCurrentFragmentTag(TAG);
    load();
  }

  @Override
  public void onPause() {
    super.onPause();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), false);
  }

  @OnClick({R.id.back})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.back:
        getActivity().onBackPressed();
        break;
    }
  }

  public void load() {
    doRequest(
        mApi.getFavourites()
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  mJudgeList.clear();
                  mJudgeList.addAll(response.getFavorites());
                  mAdapter.reloadItems(response.getFavorites());
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

  @Override
  public void onDeleteClick(int position) {
    doRequest(
        mApi.deleteFromFavourites(mJudgeList.get(position).getId())
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulersForCompletable())
            .subscribe(
                this::load,
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }
}
