package com.application.mealtimecoach.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.mealtimecoach.BaseActivity;
import com.application.mealtimecoach.R;
import com.application.mealtimecoach.models.Recipe;
import com.application.mealtimecoach.ui.adapters.RecipesRecyclerViewAdapter;
import com.application.mealtimecoach.utils.Constants;
import com.application.mealtimecoach.viewmodels.MainViewModel;
import com.application.mealtimecoach.viewmodels.ViewModelProviderFactory;
import com.astritveliu.boom.Boom;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {

    private TextView logoutTextview;
    private ProgressBar progressBar;
    private MainViewModel viewModel;

    @Inject
    RequestManager requestManager;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);

        initializeViews();
        subscribeToObservers();

        recipesRecyclerViewAdapter.setOnItemClickListener(mPosition -> {
            ArrayList<Recipe> exerciseArrayList = recipesRecyclerViewAdapter.getExercisesArraylist();
            Recipe exercise = exerciseArrayList.get(mPosition);

            Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
            intent.putExtra(Constants.TITLE, exercise.getTitle());
            intent.putExtra(Constants.DESCRIPTION, exercise.getDescription());
            intent.putExtra(Constants.IMAGE_URL, exercise.getImageUrl());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.nothing);
        });

        logoutTextview.setOnClickListener(view -> firebaseAuth.signOut());
    }

    // observing the viewModel live data to update the UI
    private void subscribeToObservers() {
        viewModel.getResourceMutableLiveData().observe(this, arrayListResource -> {
            if (arrayListResource != null) {
                switch (arrayListResource.status) {
                    case ERROR:
                        showProgressBar(false);
                        if (arrayListResource.message != null)
                            Toasty.error(getApplicationContext(), arrayListResource.message).show();
                        break;
                    case SUCCESS:
                        showProgressBar(false);
                        if (arrayListResource.data != null)
                            recipesRecyclerViewAdapter.setArraylist(arrayListResource.data);
                        break;
                    case LOADING:
                        showProgressBar(true);
                        break;
                }
            }
        });
    }

    private void showProgressBar(boolean isShown) {
        progressBar.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    private void initializeViews() {
        logoutTextview = findViewById(R.id.logout_text_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recipesRecyclerViewAdapter);

        recipesRecyclerViewAdapter.setupGlide(requestManager);

        new Boom(logoutTextview);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}