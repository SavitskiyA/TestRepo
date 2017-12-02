package com.ryj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ryj.Constants;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.CommentAdapter;
import com.ryj.interfaces.OnHolderClickListener;
import com.ryj.interfaces.OnVoteClickListener;
import com.ryj.models.enums.UserType;
import com.ryj.models.enums.VoteType;
import com.ryj.models.response.Comment;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.EndlessRecyclerOnScrollListener;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/** Created by andrey on 11/15/17. */
public class CommentsFragment extends BaseFragment
    implements OnHolderClickListener, OnVoteClickListener {
  public static final String TAG = "CommentsActivity";
  private static final String EXTRA_JUDGE_ID = "judge_id";
  private static final String EXTRA_JUDGE_FULLNAME = "judge_fullname";
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject Prefs mPrefs;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.comments_recycler_view)
  RecyclerView mComments;

  @BindView(R.id.leave_comment)
  EditText mLeaveComment;

  @BindView(R.id.send_comment)
  ImageButton mSendComment;

  @BindView(R.id.frame_leave_comment)
  FrameLayout mFrameLeaveComment;

  private int mUserId;
  private CommentAdapter<Comment> mCommentsAdapter;
  private List<Comment> mCommentsList = new ArrayList<>();
  private EndlessRecyclerOnScrollListener mOnScrollListener;

  public static CommentsFragment newInstance(int judgeId, String fullname) {
    Bundle bundle = new Bundle();
    bundle.putInt(EXTRA_JUDGE_ID, judgeId);
    bundle.putString(EXTRA_JUDGE_FULLNAME, fullname);
    CommentsFragment instance = new CommentsFragment();
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
    View view = bindView(inflater.inflate(R.layout.fragment_comments, container, false));
    mUserId = getArguments().getInt(EXTRA_JUDGE_ID);
    mTitle.setText(getArguments().getString(EXTRA_JUDGE_FULLNAME));
    mCommentsAdapter =
        new CommentAdapter<Comment>(
            this.getContext(), this, this, !TextUtils.isEmpty(mPrefs.getSessionToken()));
    mCommentsAdapter.setPaginationEnable(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
    mComments.setLayoutManager(layoutManager);
    mComments.setAdapter(mCommentsAdapter);
    mOnScrollListener =
        new EndlessRecyclerOnScrollListener(layoutManager) {
          @Override
          public void onLoadMore(int current_page) {
            if (mCommentsAdapter.isLoading()) load(current_page);
          }
        };
    mComments.addOnScrollListener(mOnScrollListener);
    if (mPrefs.getUserType() == UserType.LAWYER) {
      mFrameLeaveComment.setVisibility(View.VISIBLE);
    } else {
      mFrameLeaveComment.setVisibility(View.GONE);
    }
    load(1);
    mSendComment.setEnabled(false);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
  }

  private void load(int page) {
    doRequest(
        mApi.getCommentsForJudge(mUserId, page)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
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

  private void sendComment() {
    mApi.sendComment(mUserId, mLeaveComment.getText().toString())
        .compose(bindUntilEvent(FragmentEvent.STOP))
        .compose(RxUtils.applySchedulersForCompletable())
        .subscribe(
            () -> {
              load(Constants.VALUE_ONE);
              mLeaveComment.setText(StringUtils.EMPTY_STRING);
              mSendComment.setEnabled(false);
            },
            throwable -> {
              mErrorHandler.handleError(throwable, this.getContext());
            });
  }

  @OnTextChanged(R.id.leave_comment)
  void onTextChanged(Editable editable) {
    mSendComment.setEnabled(editable.length() > 0);
  }

  @OnClick({R.id.send_comment, R.id.back})
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        onBackPressed();
        break;
      case R.id.send_comment:
        sendComment();
        break;
    }
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
