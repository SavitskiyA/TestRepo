package com.ryj.di;

import android.preference.PreferenceManager;

import com.ryj.App;
import com.ryj.models.filters.Filters;
import com.ryj.models.filters.Items;
import com.ryj.models.request.SignUpQuery;
import com.ryj.storage.prefs.Prefs;
import com.ryj.storage.prefs.PrefsImpl;
import com.ryj.utils.handlers.ErrorHandler;
import com.ryj.utils.handlers.ErrorHandlerImpl;

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

  @Provides
  @PerApp
  ErrorHandler provideErrorHandler() {
    return new ErrorHandlerImpl();
  }

  @Provides
  @PerApp
  SignUpQuery provideSignUpQuery() {
    return new SignUpQuery();
  }

  @Provides
  @PerApp
  Filters provideFilters() {
    return new Filters();
  }

  @Provides
  @PerApp
  Items provideCategories() {
    return new Items();
  }

}
