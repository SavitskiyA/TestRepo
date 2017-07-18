package com.ryj.di;

import android.preference.PreferenceManager;

import com.ryj.App;
import com.ryj.storage.prefs.Prefs;
import com.ryj.storage.prefs.PrefsImpl;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class GlobalModule {

  private App mApp;

  public GlobalModule(App app) {
    mApp = app;
  }

  @Provides
  @PerApp
  Prefs providePrefs() {
    return new PrefsImpl(PreferenceManager.getDefaultSharedPreferences(mApp));
  }

  @Provides
  @PerApp
  EventBus provideEventBus() {
    return EventBus.getDefault();
  }
}
