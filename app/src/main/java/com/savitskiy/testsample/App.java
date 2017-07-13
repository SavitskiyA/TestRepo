package com.savitskiy.testsample;

import android.app.Application;

import com.savitskiy.testsample.di.AppComponent;
import com.savitskiy.testsample.di.DaggerAppComponent;
import com.savitskiy.testsample.di.NetworkModule;


/**
 * Created by andrey on 7/10/17.
 */

public class App extends Application {
  protected static App mInstance;
  private AppComponent mComponent;

  public static App getmInstance() {
    return mInstance;
  }

  public AppComponent getmComponent() {
    return mComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
    mComponent = DaggerAppComponent.builder().networkModule(new NetworkModule()).build();
  }
}
