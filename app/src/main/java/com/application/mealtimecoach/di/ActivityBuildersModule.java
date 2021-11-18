package com.application.mealtimecoach.di;



import com.application.mealtimecoach.BaseActivity;
import com.application.mealtimecoach.di.auth.login.LoginScope;
import com.application.mealtimecoach.di.auth.login.LoginViewModelsModule;
import com.application.mealtimecoach.di.main.MainModule;
import com.application.mealtimecoach.di.main.MainScope;
import com.application.mealtimecoach.di.main.MainViewModelsModule;
import com.application.mealtimecoach.di.auth.signup.SignupScope;
import com.application.mealtimecoach.di.auth.signup.SignupViewModelsModule;
import com.application.mealtimecoach.ui.activities.auth.LoginActivity;
import com.application.mealtimecoach.ui.activities.MainActivity;
import com.application.mealtimecoach.ui.activities.RecipeDetailsActivity;
import com.application.mealtimecoach.ui.activities.auth.SignupActivity;
import com.application.mealtimecoach.ui.activities.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @SignupScope
    @ContributesAndroidInjector(
            modules = {SignupViewModelsModule.class})
    abstract SignupActivity contributeSignupActivity();

    @LoginScope
    @ContributesAndroidInjector(
            modules = {LoginViewModelsModule.class})
    abstract LoginActivity contributeLoginActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class, MainModule.class,})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract RecipeDetailsActivity contributeExerciseDetailsActivity();

    @ContributesAndroidInjector
    abstract SplashScreenActivity contributeSplashScreenActivity();

    @ContributesAndroidInjector
    abstract BaseActivity contributeBaseActivity();
}
