package com.ryj.utils.handlers;

import android.content.Context;

import com.ryj.Constants;
import com.ryj.activities.auth.PasswordRecoveryFinishActivity;
import com.ryj.activities.auth.signup.ThankYouActivity;
import com.ryj.models.enums.RequestType;
import com.ryj.models.events.UnauthorizedEvent;
import com.ryj.rx.RxBus;
import com.ryj.utils.ToastUtil;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

import static com.ryj.models.enums.RequestType.SIGN_UP_TEMP;

/** Created by andrey on 3/8/17. */
public class ErrorHandlerImpl implements ErrorHandler {
  private static final String TAG = "ErrorHandlerImpl";
  private static final String SERVER_ERROR = "500 Server error";
  private static final String ERROR = "Error";

  private String getMessageFromJSON(Response response, Throwable throwable) {
    try {
      return ErrorUtils.getResponseErrorMessage(response);
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  @Override
  public void handleErrorByRequestType(
      Throwable throwable, Context context, RequestType requestType) throws IOException {
    if (throwable instanceof IOException) {
      ToastUtil.show(throwable.getMessage(), false);
      return;
    } else if (throwable instanceof HttpException) {
      HttpException httpException = (HttpException) throwable;
      Response response = httpException.response();
      switch (response.code()) {
        case Constants.RESPONSE_STATUS_UNAUTHORIZED:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response), false);
          RxBus.postEvent(new UnauthorizedEvent());
          return;
        case Constants.RESPONSE_STATUS_INVALID_PLATFORM:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response), false);
          return;
        case Constants.RESPONSE_STATUS_MISSING_PARAM:
          ToastUtil.show(ErrorUtils.getResponseErrorMessage(response), false);
          return;
        case Constants.RESPONSE_STATUS_NOT_FOUND:
          if (requestType == RequestType.GENERIC) {
            ToastUtil.show(ErrorUtils.getResponseErrorMessage(response), false);
            return;
          } else if (requestType == RequestType.PASSWORD_RECOVERY) {
            PasswordRecoveryFinishActivity.start(context);
            return;
          }
        case Constants.RESPONSE_STATUS_SERVER_ERROR:
          ToastUtil.show(SERVER_ERROR, false);
          return;
        default:
          ToastUtil.show(ERROR, false);
          return;
      }
    } else {
      if (requestType == SIGN_UP_TEMP) {
        ThankYouActivity.start(context);
      } else {
        ToastUtil.show(throwable.getMessage(), false);
      }
      return;
    }
  }

  @Override
  public void handleError(Throwable throwable, Context context) throws IOException {
    handleErrorByRequestType(throwable, context, RequestType.GENERIC);
  }
}
