package com.ryj.di;

import com.ryj.activities.auth.MainActivity;
import com.ryj.activities.auth.PasswordRecoveryActivity;
import com.ryj.activities.auth.SplashActivity;
import com.ryj.activities.auth.TutorialActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.activities.auth.signup.judge.JudgeSignUpActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerActivityPhoto;

import dagger.Component;

@PerApp
@Component(modules = {ApplicationModule.class, NetworkModule.class, GlobalModule.class})
public interface ApplicationComponent {
  void inject(MainActivity activity);

  void inject(SplashActivity activity);

  void inject(TutorialActivity activity);

  void inject(SignInActivity activity);

  void inject(JudgeSignUpActivity activity);

  void inject(SignUpLawyerActivity activity);

  void inject(PasswordRecoveryActivity activity);

  void inject(SignUpLawyerActivityPhoto activity);
}
