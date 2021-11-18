package com.application.mealtimecoach.di.main;


import com.application.mealtimecoach.ui.adapters.RecipesRecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @MainScope
    @Provides
    static RecipesRecyclerViewAdapter provideAdapter(){
        return new RecipesRecyclerViewAdapter();
    }

}
