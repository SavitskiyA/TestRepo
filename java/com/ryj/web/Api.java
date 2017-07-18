package com.ryj.web;

import com.ryj.models.response.SignInResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
  @FormUrlEncoded
  @POST("/sign_in")
  Observable<SignInResponse> signIn(
          @Field("account[email]") String email,
          @Field("account[password]") String password,
          @Field("session[device_platform]") String devicePlatform,
          @Field("session[device_token]") String deviceToken
  );
}
