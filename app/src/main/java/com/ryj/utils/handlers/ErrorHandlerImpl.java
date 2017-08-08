package com.ryj.utils.handlers;

import com.ryj.Constants;
import com.ryj.utils.ToastUtil;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;


/**
 * Created by andrey on 3/8/17.
 */
public class ErrorHandlerImpl implements ErrorHandler {
  private static final String TAG = "ErrorHandlerImpl";

  @Override
  public void handleError(Throwable throwable) throws IOException {
    if (throwable instanceof IOException) {
      ToastUtil.show(throwable.getMessage(), false);
      return;
    }

    if (throwable instanceof HttpException) {
      HttpException httpException = (HttpException) throwable;
      Response response = httpException.response();
      switch (response.code()) {
        case Constants.RESPONSE_STATUS_UNAUTHORIZED:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response.errorBody().string()), false);
          break;
        case Constants.RESPONSE_STATUS_NOT_FOUND:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response.errorBody().string()), false);
          break;
        default:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response.errorBody().string()), false);
          break;
      }
      return;
    }
    ToastUtil.show(throwable.getMessage(), false);
  }
}
