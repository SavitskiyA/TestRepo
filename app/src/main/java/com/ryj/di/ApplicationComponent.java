package com.ryj.di;

import com.ryj.activities.BaseActivity;
import com.ryj.activities.BottomBarContainerActivity;
import com.ryj.activities.auth.MainActivity;
import com.ryj.activities.auth.PasswordRecoveryActivity;
import com.ryj.activities.auth.SplashActivity;
import com.ryj.activities.auth.signin.SignInActivity;
import com.ryj.activities.auth.signup.citizen.SignUpCitizenActivity;
import com.ryj.activities.auth.signup.judge.SignUpJudgeActivity;
import com.ryj.activities.auth.signup.judge.SignUpJudgeAutocompleteActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerAvatarActivity;
import com.ryj.activities.auth.signup.lawyer.SignUpLawyerDocActivity;
import com.ryj.activities.filters.FiltersActivity;
import com.ryj.activities.filters.FiltersCategoryActivity;
import com.ryj.activities.filters.FiltersCityActivity;
import com.ryj.activities.filters.FiltersCourtTypeActivity;
import com.ryj.activities.filters.FiltersRegionActivity;
import com.ryj.fragments.CourtFragment;
import com.ryj.fragments.CourtsFragment;
import com.ryj.fragments.JudgesFragment;

import dagger.Component;

@PerApp
@Component(modules = {ApplicationModule.class, NetworkModule.class, GlobalModule.class})
public interface ApplicationComponent {
  void inject(BaseActivity activity);

  void inject(MainActivity activity);

  void inject(SplashActivity activity);

  void inject(SignInActivity activity);

  void inject(SignUpJudgeActivity activity);

  void inject(PasswordRecoveryActivity activity);

  void inject(SignUpLawyerActivity activity);

  void inject(SignUpLawyerAvatarActivity activity);

  void inject(SignUpLawyerDocActivity activity);

  void inject(SignUpCitizenActivity activity);

  void inject(SignUpJudgeAutocompleteActivity activity);

  void inject(BottomBarContainerActivity activity);

  void inject(FiltersActivity activity);

  void inject(FiltersCityActivity activity);

  void inject(FiltersRegionActivity activity);

  void inject(FiltersCategoryActivity activity);

  void inject(FiltersCourtTypeActivity activity);

  void inject(JudgesFragment fragment);

  void inject(CourtsFragment fragment);

  void inject(CourtFragment fragment);

}
