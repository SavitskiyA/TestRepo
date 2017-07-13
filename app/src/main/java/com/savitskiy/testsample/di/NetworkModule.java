package com.savitskiy.testsample.di;

import com.savitskiy.testsample.Constants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrey on 7/10/17.
 */
@Module
public class NetworkModule {

  @Provides
  @PerApp
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
            .readTimeout(Constants.TIMEOUT_DURATION_MS, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(Constants.TIMEOUT_DURATION_MS, java.util.concurrent.TimeUnit.SECONDS)
            .build();
  }

  @Provides
  @PerApp
  Retrofit provideRetrofit(OkHttpClient client) {
    return new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
  }
}
