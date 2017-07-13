package com.savitskiy.testsample.web;

import com.savitskiy.testsample.models.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andrey on 7/10/17.
 */

public interface Api {
  @FormUrlEncoded
  @POST("/api/v3/sign_in")
  Observable<UserResponse> signIn(
          @Field("user[email]") String email,
          @Field("user[password]") String password,
          @Field("user[provider]") String provider,
          @Field("session[device_platform]") String devicePlatform,
          @Field("session[device_token]") String deviceToken
  );
}
