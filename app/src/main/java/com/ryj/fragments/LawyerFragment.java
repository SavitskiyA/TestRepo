package com.ryj.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.EditProfileLawyerActivity;
import com.ryj.adapters.CommentAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.interfaces.OnVoteClickListener;
import com.ryj.models.enums.UserType;
import com.ryj.models.enums.VoteType;
import com.ryj.models.response.Comment;
import com.ryj.models.response.Lawyer;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.EndlessRecyclerOnScrollListener;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;

/** Created by andrey on 10/25/17. */
public class LawyerFragment extends BaseFragment implements OnHolderClickListener, OnVoteClickListener {
  public static final String TAG = "LawyerFragment";
  private static final String EXTRA_LAWYER_ID = "lawyer_id";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Prefs mPrefs;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.abbr)
  TextView mAbbr;

  @BindView(R.id.company)
  TextView mCompany;

  @BindView(R.id.first_name)
  TextView mFirstName;

  @BindView(R.id.last_name)
  TextView mLastName;

  @BindView(R.id.comments_recycler_view)
  RecyclerView mCommentsView;

  @BindDrawable(R.drawable.ic_settings)
  Drawable mSettingsIcon;

  @BindView(R.id.frame_no_comments)
  RelativeLayout mFrameNoComments;

  private int mLawyerId;
  private CommentAdapter<Comment> mCommentsAdapter;
  private List<Comment> mCommentsList = new ArrayList<>();
  private EndlessRecyclerOnScrollListener mOnScrollListener;

  public static LawyerFragment newInstance(int judgeId) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_LAWYER_ID, judgeId);
    LawyerFragment instance = new LawyerFragment();
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
    View view = bindView(inflater.inflate(R.layout.fragment_lawyer, container, false));
    mLawyerId = getArguments().getInt(EXTRA_LAWYER_ID);
    setToolbar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setHasOptionsMenu(true);
    mToolbar.setNavigationOnClickListener(
        v -> {
          if (mLawyerId == mPrefs.getCurrentUserId() && mPrefs.getUserType() == UserType.LAWYER) {
            toProfile();
          } else {
            getActivity().onBackPressed();
          }
        });
    mCommentsAdapter =
        new CommentAdapter<Comment>(
            inflater.getContext(), this, this, !TextUtils.isEmpty(mPrefs.getSessionToken()));

    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
    mCommentsView.setLayoutManager(layoutManager);
    mCommentsView.setAdapter(mCommentsAdapter);
    mOnScrollListener =
        new EndlessRecyclerOnScrollListener(layoutManager) {
          @Override
          public void onLoadMore(int current_page) {
            if (mCommentsAdapter.isLoading()) getComments(current_page);
          }
        };
    mCommentsView.addOnScrollListener(mOnScrollListener);
    return view;
  }

  private void toProfile() {
    replaceFragment(
        SettingsFragment.newInstance(), R.id.container, true, false, SettingsFragment.TAG);
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    getLawyer();
    getComments(Constants.VALUE_ONE);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.clear();
    if (mLawyerId == mPrefs.getCurrentUserId() && mPrefs.getUserType() == UserType.LAWYER) {
      mToolbar.setNavigationIcon(mSettingsIcon);
      inflater.inflate(R.menu.menu_edit, menu);
      super.onCreateOptionsMenu(menu, inflater);
      return;
    }
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_edit:
        toLawyerEditScreen();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void toLawyerEditScreen() {
    EditProfileLawyerActivity.start(this.getContext());
  }

  private void getComments(int page) {
    doRequest(
        mApi.getCommentsForLawyer(mLawyerId, page)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  if (response.getComments().size() > 0) {
                    mFrameNoComments.setVisibility(View.GONE);
                  }
                  if (page == Constants.VALUE_ONE) {
                    mCommentsList.clear();
                    mCommentsList.addAll(response.getComments());
                    mCommentsAdapter.reloadItems(response.getComments());
                  } else {
                    mCommentsList.addAll(response.getComments());
                    mCommentsAdapter.addItems(response.getComments());
                  }
                  if (response.getNextPage() == null) {
                    mCommentsAdapter.setIsLoading(false);
                  } else {
                    mCommentsAdapter.setIsLoading(true);
                  }
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private void getLawyer() {
    doRequest(
        mApi.getLawyer(mLawyerId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                this::configureLawyerViews,
                throwable -> {
                  mErrorHandler.handleError(throwable, this.getContext());
                }));
  }

  private void configureLawyerViews(Lawyer lawyer) {
    if (lawyer == null) return;
    if (lawyer.getCompany() != null) {
      mCompany.setText(lawyer.getCompany());
    } else {
      mCompany.setText(StringUtils.EMPTY_STRING);
    }

    if (lawyer.getFirstName() != null) {
      mFirstName.setText(StringUtils.firstUpperCase(lawyer.getFirstName()));
    } else {
      mFirstName.setText(StringUtils.EMPTY_STRING);
    }
    if (lawyer.getLastName() != null) {
      mLastName.setText(StringUtils.firstUpperCase(lawyer.getLastName()));
    } else {
      mLastName.setText(StringUtils.EMPTY_STRING);
    }
    if (lawyer.getAvatar().getOrigin() != null) {
      mPhoto.setImageURI(Uri.parse(lawyer.getAvatar().getOrigin()));
      mAbbr.setVisibility(View.GONE);
    } else {
      mPhoto.setImageResource(
          DrawUtils.RESOURCES[StringUtils.getFullNameLength(lawyer) % DrawUtils.RESOURCES.length]);
      mAbbr.setText(StringUtils.getAbbrFromFullName(lawyer));
      mAbbr.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onHolderClick(boolean enable, int position) {
    replaceFragment(
        JudgeFragment.newInstance(mCommentsList.get(position).getJudge().getId()),
        R.id.container,
        true,
        false,
        JudgeFragment.TAG);
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
