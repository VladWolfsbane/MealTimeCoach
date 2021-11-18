package com.application.mealtimecoach.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.mealtimecoach.BaseActivity;
import com.application.mealtimecoach.R;
import com.application.mealtimecoach.utils.Constants;
import com.astritveliu.boom.Boom;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

public class RecipeDetailsActivity extends BaseActivity {

    private ImageView exerciseImageView;

    private TextView titleTextview;
    private TextView descriptionTextview;

    private ImageView backButton;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initializeViews();

        Intent intent = getIntent();

        // getting the title, description and imageUrl from the list activity and show
        if (intent.hasExtra(Constants.IMAGE_URL) && intent.hasExtra(Constants.TITLE) && intent.hasExtra(Constants.DESCRIPTION)) {

            String imageUrl = intent.getStringExtra(Constants.IMAGE_URL);
            String title = intent.getStringExtra(Constants.TITLE);
            String description = intent.getStringExtra(Constants.DESCRIPTION);

            // injecting glide and using it
            requestManager
                    .load(imageUrl)
                    .into(exerciseImageView);

            titleTextview.setText(title);
            descriptionTextview.setText(description);
        }

        backButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.nothing, R.anim.slide_right_out);
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_right_out);
    }

    private void initializeViews() {
        exerciseImageView = findViewById(R.id.exercise_image_view);
        titleTextview = findViewById(R.id.title_text_view);
        descriptionTextview = findViewById(R.id.description_text_view);
        backButton = findViewById(R.id.back_button);

        new Boom(backButton);
    }
}