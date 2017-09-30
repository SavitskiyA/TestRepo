package com.ryj.utils.handlers;


import android.content.Context;

import com.ryj.models.enums.RequestType;

import java.io.IOException;

/**
 * Created by andrey on 03/08/17.
 */
public interface ErrorHandler {

  void handleError(Throwable throwable, Context context) throws IOException;

  void handleErrorByRequestType(Throwable throwable, Context context, RequestType requestType) throws IOException;
}
