package com.ryj.utils.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryj.models.response.Message;

/**
 * Created by andrey on 3/8/17.
 */
public class ErrorUtils {

  private static final String SERVER_ERROR = "Server error, please try later";

  public static String getResponseErrorMessage(String body) {
    Gson gson = new GsonBuilder().create();
    Message mErrorResponse = gson.fromJson(body, Message.class);
    return mErrorResponse.getMessage();
  }
}
