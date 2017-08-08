package com.ryj.web;

import com.ryj.models.request.SignUpQuery;
import com.ryj.models.response.RestorePasswordResponse;
import com.ryj.models.response.SignInResponse;
import com.ryj.models.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
  @FormUrlEncoded
  @POST("sign_in")
  Observable<SignInResponse> signIn(
          @Field("account[email]") String email,
          @Field("account[password]") String password,
          @Field("session[device_platform]") String devicePlatform,
          @Field("session[device_token]") String deviceToken
  );

  @POST("sign_up")
  Observable<UserResponse> signUp(@Body SignUpQuery query);

  @FormUrlEncoded
  @POST("passwords/restore")
  Observable<RestorePasswordResponse> restorePassword(
          @Field("email") String email
  );
}
