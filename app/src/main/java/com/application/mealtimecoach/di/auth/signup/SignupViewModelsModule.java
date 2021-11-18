package com.application.mealtimecoach.di.auth.signup;

import androidx.lifecycle.ViewModel;


import com.application.mealtimecoach.di.ViewModelKey;
import com.application.mealtimecoach.viewmodels.auth.SignupViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SignupViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel.class)
    public abstract ViewModel bindSignupViewModel(SignupViewModel viewModel);
}