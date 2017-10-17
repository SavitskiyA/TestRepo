package com.ryj.models.filters;

import java.util.List;

/** Created by andrey on 10/17/17. */
public class Affairs {
  private List<String> mAffairsClient;
  private List<String> mAffairsServer;
  private List<Boolean> mAffairsBooleans;

  public List<String> getAffairsClient() {
    return mAffairsClient;
  }

  public void setAffairsClient(List<String> affairsClient) {
    this.mAffairsClient = affairsClient;
  }

  public List<String> getAffairsServer() {
    return mAffairsServer;
  }

  public void setAffairsServer(List<String> affairsServer) {
    this.mAffairsServer = affairsServer;
  }

  public List<Boolean> getAffairsBooleans() {
    return mAffairsBooleans;
  }

  public void setAffairsBooleans(List<Boolean> affairsBooleans) {
    this.mAffairsBooleans = affairsBooleans;
  }
}
