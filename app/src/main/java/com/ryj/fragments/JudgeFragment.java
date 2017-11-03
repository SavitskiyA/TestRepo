package com.ryj.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ryj.R;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.adapters.ItemListRecyclerAdapter;
import com.ryj.adapters.LoadableListRecyclerAdapter;
import com.ryj.dialogs.SpinnerDialog;
import com.ryj.interfaces.OnHolderListener;
import com.ryj.models.enums.UserType;
import com.ryj.models.response.Comment;
import com.ryj.models.response.DetailRatings;
import com.ryj.models.response.Judge;
import com.ryj.storage.prefs.Prefs;
import com.ryj.utils.DrawUtils;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrey on 10/25/17.
 */

public class JudgeFragment extends BaseFragment implements OnHolderListener {
  public static final String TAG = "JudgeFragment";
  private static final String EXTRA_JUDGE_ID = "judge_id";
  @Inject
  Api mApi;
  @Inject
  ErrorHandler mErrorHandler;
  @Inject
  Prefs mPrefs;

  @BindView(R.id.photo)
  SimpleDraweeView mPhoto;

  @BindView(R.id.abbr)
  TextView mAbbr;

  @BindView(R.id.court)
  TextView mCourt;

  @BindView(R.id.back)
  ImageView mBack;

  @BindView(R.id.settings)
  ImageView mSettings;

  @BindView(R.id.edit)
  ImageView mEdit;

  @BindView(R.id.favourites)
  ImageView mFavourites;

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
  RecyclerView mMarks;

  @BindView(R.id.comments_recycler_view)
  RecyclerView mComments;

  private SpinnerDialog mSpinnerDialog;

  private ItemListRecyclerAdapter<DetailRatings> mDetailRatingAdapter;

  private LoadableListRecyclerAdapter<Comment> mCommentsAdapter;

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
    mSpinnerDialog = getSpinnerDialog();
    mSpinnerDialog.setCancelable(false);
  }

  @Nullable
  @Override
  public View onCreateView(
          LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_judge, container, false);
    ButterKnife.bind(this, view);
    //getJudge ID
    int judgeId = getArguments().getInt(EXTRA_JUDGE_ID);
    //configure ICONS
    configProfileIcons(judgeId, mPrefs.getCurrentUserId(), mPrefs.getUserType());
    //configure JUDGE DATA
    getJudge(judgeId);
    //get COMMENTS FROM SERVER
    mCommentsAdapter = new LoadableListRecyclerAdapter<Comment>(inflater.getContext(), this);
    mComments.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
    mComments.setAdapter(mCommentsAdapter);
    getComments(judgeId, 1, 2);
    return view;
  }

  private void configProfileIcons(int judgeId, int currentId, UserType type) {
    if (judgeId == currentId) {
      mBack.setVisibility(View.GONE);
      mSettings.setVisibility(View.VISIBLE);
      mEdit.setVisibility(View.VISIBLE);
      mFavourites.setVisibility(View.GONE);
      mButtons.setVisibility(View.GONE);
      return;
    }
    if (type == UserType.LAWYER) {
      mBack.setVisibility(View.VISIBLE);
      mSettings.setVisibility(View.GONE);
      mEdit.setVisibility(View.GONE);
      mFavourites.setVisibility(View.VISIBLE);
      mButtons.setVisibility(View.VISIBLE);
      return;
    }
    mButtons.setVisibility(View.GONE);
    mBack.setVisibility(View.VISIBLE);
    mSettings.setVisibility(View.GONE);
    mEdit.setVisibility(View.GONE);
    mFavourites.setVisibility(View.GONE);
  }

  @Override
  public void onHolderClicked(boolean enable, int position) {

  }

  @Override
  public void onResume() {
    super.onResume();
    switchActivityTab(BottomBarContainerActivity.getTabPosition(TAG), true);
    setActivityToolbarVisibility(View.GONE);
  }

  @Override
  public void onPause() {
    super.onPause();
    setActivityToolbarVisibility(View.VISIBLE);
  }

  private void getJudge(int judgeId) {
    Log.d("Session-token", mPrefs.getSessionToken());
    mApi.getJudge(judgeId)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .compose(
                    RxUtils.applyBeforeAndAfter(
                            (disposable) ->
                                    mSpinnerDialog.show(getActivity().getSupportFragmentManager(), StringUtils.EMPTY_STRING),
                            () -> mSpinnerDialog.dismiss()))
            .subscribe(
                    response -> {
                      configureJudgeViews(response);
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable, this.getContext());
                    });
  }

  private void getComments(int judgeId, int page, int perPage) {
    mApi.getComments(judgeId, page, perPage)
            .compose(bindUntilEvent(FragmentEvent.STOP))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                    response -> {
                      mCommentsAdapter.addItems(response.getComments());
                    },
                    throwable -> {
                      mErrorHandler.handleError(throwable, this.getContext());
                    });
  }

  private void configureJudgeViews(Judge judge) {
    if (judge == null) return;
    if (judge.getCourt().getName() != null) {
      mCourt.setText(String.valueOf(judge.getCourt().getName()));
    } else {
      mCourt.setText(StringUtils.EMPTY_STRING);
    }
    if (judge.getCommentsCount() != null) {
      mCommentsCount.setText(String.valueOf(judge.getCommentsCount()));
    } else {
      mCommentsCount.setText(StringUtils.ZERO);
    }
    if (judge.getRatingCount() != null) {
      mMarksCount.setText(String.valueOf(judge.getRatingCount()));
    } else {
      mMarksCount.setText(StringUtils.ZERO);
    }
    if (judge.getFirstName() != null) {
      mFirstName.setText(judge.getFirstName());
    } else {
      mFirstName.setText(StringUtils.EMPTY_STRING);
    }
    if (judge.getLastName() != null) {
      mLastName.setText(judge.getLastName());
    } else {
      mLastName.setText(StringUtils.EMPTY_STRING);
    }
    if (judge.getAvatar().getOrigin() != null) {
      mPhoto.setImageURI(Uri.parse(judge.getAvatar().getOrigin()));
      mAbbr.setVisibility(View.GONE);
    } else {
      mPhoto.setImageResource(
              DrawUtils.RESOURCES[StringUtils.getFullNameLength(judge) % DrawUtils.RESOURCES.length]);
      mAbbr.setText(StringUtils.getAbbrFromFullName(judge));
      mAbbr.setVisibility(View.VISIBLE);
    }
  }
}
