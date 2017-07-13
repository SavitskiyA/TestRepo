package com.savitskiy.testsample.di;

import com.savitskiy.testsample.MainActivity;

import dagger.Component;

/**
 * Created by andrey on 7/10/17.
 */
@PerApp
@Component(modules = {NetworkModule.class})
public interface AppComponent {
  void inject(MainActivity activity);
}
