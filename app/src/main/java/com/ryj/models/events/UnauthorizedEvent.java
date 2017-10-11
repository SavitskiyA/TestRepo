package com.ryj.models.events;

/** Created by andrey on 9/7/17. */
public class UnauthorizedEvent {
  private String mMessage;

  public UnauthorizedEvent(String message) {
    this.mMessage = message;
  }

  public UnauthorizedEvent() {
  }

  public String getMessage() {
    return mMessage;
  }

  public void setMessage(String message) {
    this.mMessage = message;
  }
}
