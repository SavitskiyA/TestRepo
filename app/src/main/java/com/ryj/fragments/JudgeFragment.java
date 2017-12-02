package com.ryj.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.CommentAdapter;
import com.ryj.adapters.RatingAdapter;
import com.ryj.dialogs.EstimationDialog;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.interfaces.OnLoadMoreClickListener;
import com.ryj.interfaces.OnVoteClickListener;
import com.ryj.models.enums.UserType;
import com.ryj.models.enums.VoteType;
import com.ryj.models.response.Comment;
import com.ryj.models.response.Comments;
import com.ryj.models.response.Court;
import com.ryj.models.response.Judge;
import com.ryj.models.response.Rating;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

/** Created by andrey on 10/25/17. */
public class JudgeFragment extends BaseFragment
    implements OnHolderClickListener, OnLoadMoreClickListener, OnVoteClickListener {
  public static final String TAG = "JudgeFragment";
  private static final String EXTRA_JUDGE_ID = "judge_id";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Prefs mPrefs;

  @BindView(R.id.app_bar_layout)
  AppBarLayout mAppBarLayout;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.abbr)
  TextView mAbbr;

  @BindView(R.id.court)
  TextView mCourtTxt;

  @BindView(R.id.comments_count)
  TextView mCommentsCount;

  @BindView(R.id.marks_count)
  TextView mMarksCount;

  @BindView(R.id.first_name)
  TextView mFirstName;

  @BindView(R.id.last_name)
  TextView mLastName;

  @BindView(R.id.comment)
  Button mComment;

  @BindView(R.id.estimate)
  Button mEstimate;

  @BindView(R.id.frame_buttons)
  LinearLayout mButtons;

  @BindView(R.id.marks_recycler_view)
  RecyclerView mRatingView;

  @BindView(R.id.comments_recycler_view)
  RecyclerView mCommentsView;

  @BindDrawable(R.drawable.ic_star_favourite_pressed)
  Drawable mFavouritePressed;

  @BindDrawable(R.drawable.ic_star_favourite_not_pressed)
  Drawable mFavouriteNotPressed;

  @BindDrawable(R.drawable.ic_settings)
  Drawable mSettingsIcon;

  @BindArray(R.array.text_detail_rating)
  String[] mRatingsClient;

  private boolean isFavouritePressed;
  private int mJudgeId;
  private Court mCourt;
  private String mJudgeFullName;
  private MenuItem mFavouriteItem;
  private RatingAdapter<Rating> mRatingAdapter;
  private CommentAdapter<Comment> mCommentsAdapter;
  private List<Comment> mCommentsList = new ArrayList<>();

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
    View view = bindView(inflater.inflate(R.layout.fragment_judge, container, false));
    mJudgeId = getArguments().getInt(EXTRA_JUDGE_ID);
    setEsimteAndMarkButtons();
    setToolbar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setHasOptionsMenu(true);
    mToolbar.setNavigationOnClickListener(
        v -> {
          if (mJudgeId == mPrefs.getCurrentUserId()) {
            toProfile();
          } else {
            getActivity().onBackPressed();
          }
        });
    mRatingAdapter = new RatingAdapter<Rating>(inflater.getContext());
    mRatingView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mRatingView.setAdapter(mRatingAdapter);
    mCommentsAdapter =
        new CommentAdapter<Comment>(
            inflater.getContext(), this, this, this, !TextUtils.isEmpty(mPrefs.getSessionToken()));
    mCommentsView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mCommentsAdapter.setAllCommentsButtonShown(true);
    mCommentsView.setAdapter(mCommentsAdapter);
    return view;
  }

  private void setEsimteAndMarkButtons() {
    if (mPrefs.getCurrentUserId() == mJudgeId) {
      mButtons.setVisibility(View.GONE);
      return;
    }
    if (mPrefs.getUserType() == UserType.LAWYER) {
      mButtons.setVisibility(View.VISIBLE);
      return;
    } else {
      mButtons.setVisibility(View.GONE);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    getJudge(mJudgeId);
    getComments(mJudgeId);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.clear();
    if (mJudgeId == mPrefs.getCurrentUserId() && mPrefs.getUserType() == UserType.JUDGE) {
      mToolbar.setNavigationIcon(mSettingsIcon);
      inflater.inflate(R.menu.menu_edit, menu);
      mFavouriteItem = menu.findItem(R.id.action_favourites);
      super.onCreateOptionsMenu(menu, inflater);
      return;
    }
    if (mPrefs.getUserType() == UserType.LAWYER) {
      inflater.inflate(R.menu.menu_favourites, menu);
      mFavouriteItem = menu.findItem(R.id.action_favourites);
      super.onCreateOptionsMenu(menu, inflater);
      return;
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_favourites:
        if (isFavouritePressed) {
          deleteFromFavourites(mJudgeId);
          item.setIcon(mFavouriteNotPressed);
        } else {
          item.setIcon(mFavouritePressed);
          addToFavourites(mJudgeId);
        }
        isFavouritePressed = !isFavouritePressed;
        return true;
      case R.id.action_edit:
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void getJudge(int judgeId) {
    doRequest(
        mApi.getJudge(judgeId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  configureJudgeViews(response);
                  mRatingAdapter.addItems(response.getRatingCriteria());
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private void getComments(int judgeId) {
    doRequest(
        mApi.getCommentsForJudge(judgeId, Constants.VALUE_ONE, Constants.VALUE_TWO)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  mCommentsList.clear();
                  mCommentsList.addAll(configureJudgeComments(mCommentsAdapter, response));
                  mCommentsAdapter.addItems(configureJudgeComments(mCommentsAdapter, response));
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private void addToFavourites(int judgeId) {
    doRequest(
        mApi.addToFavourites(judgeId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulersForCompletable())
            .subscribe(
                () -> {},
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private void deleteFromFavourites(int judgeId) {
    doRequest(
        mApi.deleteFromFavourites(judgeId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulersForCompletable())
            .subscribe(
                () -> {},
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private List<Comment> configureJudgeComments(CommentAdapter adapter, Comments comments) {
    List<Comment> commentsList = new ArrayList<>();
    if (comments.getComments().size() > 1) {
      adapter.setAllCommentsButtonShown(true);
      commentsList.add(comments.getComments().get(0));
    } else {
      adapter.setAllCommentsButtonShown(false);
      commentsList.addAll(comments.getComments());
    }
    return commentsList;
  }

  private void configureJudgeViews(Judge judge) {
    if (judge == null) return;
    mCourt = judge.getCourt();
    mCourtTxt.setText(judge.getCourt().getName());
    mCommentsCount.setText(String.valueOf(judge.getCommentsCount()));
    mMarksCount.setText(String.valueOf(judge.getRatingCount()));
    mFirstName.setText(StringUtils.firstUpperCase(judge.getFirstName()));
    mLastName.setText(StringUtils.firstUpperCase(judge.getLastName()));

    if (judge.getAvatar().getOrigin() != null) {
      mPhoto.setImageURI(Uri.parse(judge.getAvatar().getOrigin()));
      mAbbr.setVisibility(View.GONE);
    } else {
      mPhoto.setImageResource(
          DrawUtils.RESOURCES[StringUtils.getFullNameLength(judge) % DrawUtils.RESOURCES.length]);
      mAbbr.setText(StringUtils.getAbbrFromFullName(judge));
      mAbbr.setVisibility(View.VISIBLE);
    }
    if (mFavouriteItem != null) {
      if (judge.isFavourite()) {
        mFavouriteItem.setIcon(mFavouritePressed);
        isFavouritePressed = true;
      } else {
        mFavouriteItem.setIcon(mFavouriteNotPressed);
        isFavouritePressed = false;
      }
    }
    mJudgeFullName = StringUtils.getFullName(judge);
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    replaceFragment(
        LawyerFragment.newInstance(mCommentsList.get(position).getLawyer().getId()),
        R.id.container,
        true,
        false,
        LawyerFragment.TAG);
  }

  @Override
  public void onLoadMoreClick(boolean enable, int position) {
    toComments();
  }

  private void toComments() {
    replaceFragment(
        CommentsFragment.newInstance(mJudgeId, mJudgeFullName),
        R.id.container,
        true,
        false,
        CommentsFragment.TAG);
  }

  private void toProfile() {
    replaceFragment(
        SettingsFragment.newInstance(), R.id.container, true, false, SettingsFragment.TAG);
  }

  @OnClick({R.id.comment, R.id.estimate, R.id.court})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.comment:
        toComments();
        return;
      case R.id.estimate:
        toEstimation();
        return;
      case R.id.court:
        toCourt();
        return;
    }
  }

  private void toCourt() {
    replaceFragment(
        CourtFragment.newInstance(
            mCourt.getId(), mCourt.getName(), mCourt.getRatingCount(), mCourt.getRating()),
        R.id.container,
        true,
        false,
        LawyerFragment.TAG);
  }

  private void toEstimation() {
    EstimationDialog estimDialog = new EstimationDialog();
    estimDialog.setCancelable(false);
    estimDialog.show(getFragmentManager(), StringUtils.EMPTY_STRING);
  }

  @Override
  public void vote(VoteType type, int position) {
    doRequest(
        mApi.vote(mCommentsList.get(position).getId(), type.toString())
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulersForSingle())
            .subscribe(
                response -> {
                  mCommentsList.get(position).setLikesCount(response.getLikesCount());
                  mCommentsList.get(position).setDislikesCount(response.getDislikesCount());
                  mCommentsList.get(position).setVoteKind(response.getVoteKind());
                  mCommentsAdapter.reloadItems(mCommentsList);
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  @Override
  public void deleteVote(int position) {
    doRequest(
        mApi.deleteVote(mCommentsList.get(position).getId())
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulersForSingle())
            .subscribe(
                response -> {
                  mCommentsList.get(position).setLikesCount(response.getLikesCount());
                  mCommentsList.get(position).setDislikesCount(response.getDislikesCount());
                  mCommentsList.get(position).setVoteKind(response.getVoteKind());
                  mCommentsAdapter.reloadItems(mCommentsList);
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }
}
