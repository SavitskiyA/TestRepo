package com.ryj.utils;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/** Created by andrey on 10/4/17. */
public class RxUtils {
  public static <T> ObservableTransformer<T, T> applySchedulers() {
    return observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> applySchedulersForSingle() {
    return observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer applySchedulersForCompletable() {
    return observable ->
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> applySchedulersAndBindUntillEvent(
      RxAppCompatActivity activity, ActivityEvent event) {
    if (activity != null) {
      return observable ->
          observable.compose(activity.bindUntilEvent(event)).compose(applySchedulers());
    } else {
      throw new RuntimeException(
          "Caller activity is null or not an instance of RxAppCompatActivity");
    }
  }

  public static <T> ObservableTransformer<T, T> applySchedulersAndBindUntillEvent(
      RxFragment fragment, FragmentEvent event) {
    if (fragment != null) {
      return observable ->
          observable.compose(fragment.bindUntilEvent(event)).compose(applySchedulers());
    } else {
      throw new RuntimeException("Caller fragment is null or not an instance of RxFragment");
    }
  }

  public static <T> ObservableTransformer<T, T> applyBeforeAndAfter(
      Consumer<? super Disposable> onSubscribe, Runnable after) {
    return observable -> observable.doOnSubscribe(onSubscribe::accept).doOnTerminate(after::run);
  }

  public static CompletableTransformer applyBeforeAndAfterForCompletable(
      Consumer<? super Disposable> onSubscribe, Runnable after) {
    return observable -> observable.doOnSubscribe(onSubscribe::accept).doOnTerminate(after::run);
  }
}
