package com.savitskiy.testsample.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by andrey on 7/10/17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
