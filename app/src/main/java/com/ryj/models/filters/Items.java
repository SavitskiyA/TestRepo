package com.ryj.models.filters;

import com.ryj.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Items<T> {
  private List<String> mItemsClient = new ArrayList<>();
  private List<T> mItemsServer = new ArrayList<>();
  private boolean[] mBooleans;

  public void init(List<String> itemsClient, List<T> itemsServer) {
    setItemsClient(itemsClient);
    setItemsServer(itemsServer);
    mBooleans = new boolean[mItemsClient.size()];
  }

  public List<String> getItemsClient() {
    return mItemsClient;
  }

  public void setItemsClient(List<String> itemsClient) {
    mItemsClient = itemsClient;
  }

  public List<T> getItemsServer() {
    return mItemsServer;
  }

  public void setItemsServer(List<T> itemsServer) {
    mItemsServer = itemsServer;
  }

  public boolean[] getBooleans() {
    return mBooleans;
  }

  public void setBoolean(boolean enable, int position) {
    mBooleans[position] = enable;
  }

  public String getResultClient() {
    return StringUtils.getStringFromList(
        getItemsClient(), getBooleans(), StringUtils.DOT_COMMA_SPACE);
  }

  public List<T> getResultServer() {
    return StringUtils.getListFromList(getItemsServer(), getBooleans());
  }
}
