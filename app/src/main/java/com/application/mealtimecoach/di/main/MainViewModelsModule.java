package com.application.mealtimecoach.di.main;

import androidx.lifecycle.ViewModel;


import com.application.mealtimecoach.di.ViewModelKey;
import com.application.mealtimecoach.viewmodels.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}