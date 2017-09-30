package com.ryj.utils;

/**
 * Created by andrey on 9/15/17.
 */

public class Utils {
  public static int getRandomNumber(int min, int max) {
    return min + (int) (Math.random() * ((max - min) + 1));
  }
}
