package com.ryj.di;

import android.text.TextUtils;

import com.ryj.App;
import com.ryj.BuildConfig;
import com.ryj.Constants;
import com.ryj.storage.prefs.Prefs;
import com.ryj.web.Api;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
  OkHttpClient provideOkHttpClient(Prefs prefs) {
    return applyLogLevel(
            new OkHttpClient.Builder()
                .readTimeout(Constants.TIMEOUT_DURATION_MS, TimeUnit.SECONDS)
                .connectTimeout(Constants.TIMEOUT_DURATION_MS, TimeUnit.SECONDS)
                .addNetworkInterceptor(
                    chain -> {
                      Request request = chain.request();
                      Request.Builder requestBuilder = request.newBuilder();
                      requestBuilder.addHeader(
                          Constants.HEADER_ACCEPT, Constants.HEADER_CONTENT_TYPE);
                      if (!TextUtils.isEmpty(prefs.getSessionToken())) {
                        requestBuilder.addHeader(
                            Constants.HEADER_SESSION_TOKEN, prefs.getSessionToken());
                      }
                      requestBuilder.build();
                      Response response = chain.proceed(request);
                      if (!TextUtils.isEmpty(response.header(Constants.HEADER_SESSION_TOKEN))) {
                        prefs.setSessionToken(response.header(Constants.HEADER_SESSION_TOKEN));
                      }
                      return response;
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
