package com.ryj;

import android.app.Application;
import android.graphics.Bitmap;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.ryj.di.ApplicationComponent;
import com.ryj.di.ApplicationModule;
import com.ryj.di.DaggerApplicationComponent;
import com.ryj.di.GlobalModule;
import com.ryj.di.NetworkModule;

import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

  protected static App mInstance;

  private ApplicationComponent mComponent;

  public static App get() {
    return mInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
//    Fabric.with(this, new Crashlytics());
    mInstance = this;
    JodaTimeAndroid.init(this);
    Fresco.initialize(
            mInstance,
            ImagePipelineConfig.newBuilder(mInstance)
                    .setBitmapsConfig(Bitmap.Config.RGB_565)
                    .setDownsampleEnabled(true)
                    .build());
    mComponent =
            DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(mInstance))
                    .networkModule(new NetworkModule(mInstance))
                    .globalModule(new GlobalModule(mInstance))
                    .build();
  }

  public ApplicationComponent getComponent() {
    return mComponent;
  }
}
