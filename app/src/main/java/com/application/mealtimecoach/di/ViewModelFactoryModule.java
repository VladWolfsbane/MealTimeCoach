package com.application.mealtimecoach.di;

import androidx.lifecycle.ViewModelProvider;


import com.application.mealtimecoach.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
}
