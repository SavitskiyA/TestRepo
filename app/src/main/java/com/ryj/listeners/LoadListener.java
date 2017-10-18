package com.ryj.listeners;

/** Created by andrey on 8/18/17. */
public interface LoadListener {

  void load(int page);

  int increment();

  void reset();
}
