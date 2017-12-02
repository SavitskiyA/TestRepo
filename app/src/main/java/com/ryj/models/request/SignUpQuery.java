package com.ryj.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryj.models.Account;
import com.ryj.models.Session;
import com.ryj.models.User;

import java.io.Serializable;

/**
 * Created by andrey on 7/27/17.
 */

public class SignUpQuery implements Serializable {
  @SerializedName("user")
  @Expose
  private User mUser;

  @SerializedName("account")
  @Expose
  private Account mAccount;

  @SerializedName("session")
  @Expose
  private Session mSession;

  public SignUpQuery() {
  }

  public SignUpQuery(User user, Account account, Session session) {
    mUser = user;
    mAccount = account;
    mSession = session;
  }

  public User getUser() {
    return mUser;
  }

  public void setUser(User user) {
    mUser = user;
  }

  public Account getAccount() {
    return mAccount;
  }

  public void setAccount(Account account) {
    mAccount = account;
  }

  public Session getSession() {
    return mSession;
  }

  public void setSession(Session session) {
    mSession = session;
  }
}
