package com.application.mealtimecoach.di.auth.login;

import androidx.lifecycle.ViewModel;


import com.application.mealtimecoach.di.ViewModelKey;
import com.application.mealtimecoach.viewmodels.auth.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}