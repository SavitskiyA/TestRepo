package com.ryj.listeners;

/** Created by andrey on 8/18/17. */
public interface Loadable {
  void setItemCount(int count);

  void load(int page);

  int increment();

  void reset();
}
