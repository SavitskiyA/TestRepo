package com.ryj.web;

import com.ryj.models.Password;
import com.ryj.models.request.SignUpQuery;
import com.ryj.models.response.Cities;
import com.ryj.models.response.Comments;
import com.ryj.models.response.Court;
import com.ryj.models.response.Courts;
import com.ryj.models.response.Favourites;
import com.ryj.models.response.Judge;
import com.ryj.models.response.Judges;
import com.ryj.models.response.Lawyer;
import com.ryj.models.response.Message;
import com.ryj.models.response.Regions;
import com.ryj.models.response.UserResponse;
import com.ryj.models.response.Vote;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
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
      @Query("filter[by_court_id]") Integer courtId, @Query("page") Integer page);

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

  @GET("courts/{court_id}")
  Observable<Court> getCourt(@Path("court_id") Integer courtId);

  @GET("regions")
  Observable<Regions> getRegions();

  @GET("cities")
  Observable<Cities> getCities(@Query("filter[by_region_id]") Integer regionId);

  @GET("judges/{id}")
  Observable<Judge> getJudge(@Path("id") Integer id);

  @GET("lawyers/{id}")
  Observable<Lawyer> getLawyer(@Path("id") Integer id);

  @GET("comments")
  Observable<Comments> getCommentsForJudge(
      @Query("filter[for_judge_id]") Integer judgeId, @Query("page") Integer page);

  @GET("comments")
  Observable<Comments> getCommentsForLawyer(
      @Query("filter[by_lawyer_id]") Integer lawyerId, @Query("page") Integer page);

  @GET("comments")
  Observable<Comments> getCommentsForJudge(
      @Query("filter[for_judge_id]") Integer lawyerId,
      @Query("page") Integer page,
      @Query("per_page") Integer perPage);

  @FormUrlEncoded
  @POST("judges/{judge_id}/comments")
  Completable sendComment(
      @Path("judge_id") Integer judgeId, @Field("comment[body]") String commentBody);

  @FormUrlEncoded
  @POST("favorites")
  Completable addToFavourites(@Field("favorite[judge_id]") Integer id);

  @FormUrlEncoded
  @POST("comments/{comment_id}/votes")
  Single<Vote> vote(@Path("comment_id") Integer commentId, @Field("vote[kind]") String voteKind);

  @DELETE("favorites")
  Completable deleteFromFavourites(@Query("favorite[judge_id]") Integer judgeId);

  @PATCH("passwords/reset")
  Observable<UserResponse> changePassword(@Body Password password);

  @FormUrlEncoded
  @PUT("passwords")
  Completable changePassword(
      @Field("current_password") CharSequence curPassword,
      @Field("password") CharSequence newPassword,
      @Field("password_confirmation") CharSequence repeatNewPassword);

  @DELETE("comments/{comment_id}/votes")
  Single<Vote> deleteVote(@Path("comment_id") Integer commentId);

  @GET("favorites")
  Observable<Favourites> getFavourites();
}
