package com.ryj.di;

import com.ryj.App;
import com.ryj.BuildConfig;
import com.ryj.Constants;
import com.ryj.web.Api;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

  private App mApp;

  public NetworkModule(App app) {
    mApp = app;
  }

  private OkHttpClient.Builder applyLogLevel(OkHttpClient.Builder builder) {
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.interceptors().add(interceptor);
    }
    return builder;
  }

  @Provides
  @PerApp
  OkHttpClient provideOkHttpClient() {
    return applyLogLevel(
            new OkHttpClient.Builder()
                    .readTimeout(Constants.TIMEOUT_DURATION_MS, TimeUnit.SECONDS)
                    .connectTimeout(Constants.TIMEOUT_DURATION_MS, TimeUnit.SECONDS)
                    .addNetworkInterceptor(chain -> {
                      Request request = chain.request()
                              .newBuilder()
                              .addHeader(Constants.HEADER_ACCEPT, Constants.HEADER_CONTENT_TYPE)
                              .build();
                      return chain.proceed(request);
                    }))
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

  @Provides
  @PerApp
  Api provideApi(Retrofit retrofit) {
    return retrofit.create(Api.class);
  }
}
