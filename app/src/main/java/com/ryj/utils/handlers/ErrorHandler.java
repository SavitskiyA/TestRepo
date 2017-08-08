package com.ryj.utils.handlers;


import java.io.IOException;

/**
 * Created by andrey on 03/08/17.
 */
public interface ErrorHandler {

  void handleError(Throwable throwable) throws IOException;

}
