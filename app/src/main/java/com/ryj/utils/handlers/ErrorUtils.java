package com.ryj.utils.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryj.models.response.Message;

import java.io.IOException;

import retrofit2.Response;

/** Created by andrey on 3/8/17. */
public class ErrorUtils {

  private static final String SERVER_ERROR = "Server error, please try later";

  public static String getResponseErrorMessage(Response response) throws IOException {
    Gson gson = new GsonBuilder().create();
    Message mErrorResponse = gson.fromJson(response.errorBody().string(), Message.class);
    return mErrorResponse.getMessage();
  }
}
