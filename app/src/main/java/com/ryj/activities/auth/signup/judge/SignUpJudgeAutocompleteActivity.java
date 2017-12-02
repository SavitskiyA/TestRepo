package com.ryj.activities.auth.signup.judge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ryj.R;
import com.ryj.activities.BaseActivity;
import com.ryj.adapters.AutocompleteAdapter;
import com.ryj.interfaces.LoadListener;
import com.ryj.models.User;
import com.ryj.models.request.SignUpQuery;
import com.ryj.models.response.Judge;
import com.ryj.utils.RxUtils;
import com.ryj.utils.StringUtils;
import com.ryj.utils.ToastUtil;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.web.Api;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/** Created by andrey on 8/17/17. */
public class SignUpJudgeAutocompleteActivity extends BaseActivity implements LoadListener {
  @Inject Api mApi;
  @Inject ErrorHandler mErrorHandler;
  @Inject SignUpQuery mQuery;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.title)
  TextView mTitle;

  @BindView(R.id.lawyer_fullname)
  AutoCompleteTextView mName;

  @BindString(R.string.text_user_is_already_registered)
  String mUserIsAlreadyRegistered;

  private AutocompleteAdapter mAdapter;
  private int mPage = 1;

  public static void start(Context context) {
    Intent i = new Intent(context, SignUpJudgeAutocompleteActivity.class);
    context.startActivity(i);
  }

  @Nullable
  @Override
  protected Toolbar getToolbar() {
    return mToolbar;
  }

  @Nullable
  @Override
  protected TextView getToolbarTitle() {
    return mTitle;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_judge_autocomplete);
    getComponent().inject(this);
    setSupportActionBar(mToolbar);
    setToolbarBackArrowEnabled(true);
    setDefaultDisplayShowTitleEnabled(false);
    onTextChanged();
    mAdapter = new AutocompleteAdapter(this, this);
    mName.setAdapter(mAdapter);
    mName.setOnItemClickListener(
        (parent, view, position, id) -> {
          Judge judge = (Judge) parent.getItemAtPosition(position);
          if (judge.getAccessStatus() != 0) {
            ToastUtil.show(mUserIsAlreadyRegistered, false);
            mName.setText(StringUtils.EMPTY_STRING);
          } else {
            mName.setText(StringUtils.getFullName(judge));
            mQuery.setUser(new User());
            mQuery.getUser().setId(judge.getId());
            mQuery.getUser().setFirstName(judge.getFirstName());
            mQuery.getUser().setLastName(judge.getLastName());
            SignUpJudgeActivity.start(SignUpJudgeAutocompleteActivity.this);
          }
        });
  }

  @Override
  public void load(int page) {
    doRequest(
        mApi.getJudgesByName(mName.getText().toString().trim(), page)
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .compose(RxUtils.applySchedulers())
            .subscribe(
                response -> {
                  if (page > 1) {
                    mAdapter.addItems(response.getObjects());
                  } else {
                    mAdapter.reloadItems(response.getObjects());
                  }
                  if (response.getNextPage() == null) {
                    mAdapter.setIsLoadable(false);
                  }
                },
                throwable -> {
                  mErrorHandler.handleError(throwable, this);
                }));
  }

  @Override
  public int increment() {
    mPage++;
    return mPage;
  }

  @Override
  public void reset() {
    mPage = 1;
    mAdapter.setIsLoadable(true);
    load(mPage);
  }

  private void onTextChanged() {
    RxTextView.textChanges(mName)
        .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(query -> reset());
  }
}
