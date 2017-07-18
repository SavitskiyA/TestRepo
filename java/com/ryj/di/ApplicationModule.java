package com.ryj.di;

import com.ryj.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private App mApp;

  public ApplicationModule(App app) {
    mApp = app;
  }

  @Provides
  @PerApp
  App provideApplication() {
    return mApp;
  }
}
