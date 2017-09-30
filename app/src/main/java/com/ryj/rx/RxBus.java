package com.ryj.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/** Created by andrey on 9/1/17. */
public class RxBus {
  private static RxBus mInstance;
  private PublishSubject<Object> subject = PublishSubject.create();

  private static synchronized RxBus instanceOf() {
    if (mInstance == null) {
      mInstance = new RxBus();
    }
    return mInstance;
  }

  public static void postEvent(Object event) {
    RxBus.instanceOf().setEvent(event);
  }

  public static Observable<Object> receiveEvent() {
    return RxBus.instanceOf().getEvent();
  }

  private Observable<Object> getEvent() {
    return subject;
  }

  private void setEvent(Object event) {
    subject.onNext(event);
  }
}
