package com.ryj.web;

import com.ryj.models.request.SignUpQuery;
import com.ryj.models.response.Cities;
import com.ryj.models.response.Courts;
import com.ryj.models.response.Judges;
import com.ryj.models.response.Message;
import com.ryj.models.response.Regions;
import com.ryj.models.response.UserResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Api {

  @FormUrlEncoded
  @POST("sign_in")
  Observable<UserResponse> signIn(
      @Field("account[email]") String email,
      @Field("account[password]") String password,
      @Field("session[device_platform]") String devicePlatform,
      @Field("session[device_token]") String deviceToken);

  @POST("sign_up")
  Observable<UserResponse> signUp(@Body SignUpQuery query);

  @FormUrlEncoded
  @POST("passwords/restore")
  Observable<Message> restorePassword(@Field("email") String email);

  @Multipart
  @POST("sign_up")
  Observable<UserResponse> signUpLawyer(
      @PartMap() Map<String, RequestBody> userMap,
      @PartMap() Map<String, RequestBody> accountMap,
      @PartMap() Map<String, RequestBody> sessionMap,
      @Part MultipartBody.Part avatar,
      @Part MultipartBody.Part doc);

  @GET("judges")
  Observable<Judges> getJudgesByName(
      @Query("filter[by_full_name]]]") String fullName, @Query("page") Integer page);

  @GET("judges")
  Observable<Judges> getJudges(
      @Query("filter[by_full_name]]") String fullName,
      @Query("filter[by_court_id]") Integer courtId,
      @Query("filter[by_affairs]") List<String> affairs,
      @Query("filter[by_court_kind]") String courtType,
      @Query("filter[by_city_id]") Integer cityId,
      @Query("filter[by_region_id]") Integer regionId,
      @Query("sort") String sort,
      @Query("direction") String direction,
      @Query("page") Integer page);

  @GET("courts")
  Observable<Courts> getCourts(
      @Query("filter[by_name]") String fullName,
      @Query("filter[by_kind]") String courtType,
      @Query("filter[by_city_id]") Integer cityId,
      @Query("filter[by_region_id]") Integer regionId,
      @Query("sort") String sort,
      @Query("direction") String direction,
      @Query("page") Integer page);

  @GET("regions")
  Observable<Regions> getRegions(@Query("page") Integer page);

  @GET("cities")
  Observable<Cities> getCities(
      @Query("filter[by_region_id]") Integer regionId, @Query("page") Integer page);
}
