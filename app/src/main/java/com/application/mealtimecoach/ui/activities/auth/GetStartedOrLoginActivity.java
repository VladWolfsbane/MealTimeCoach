package com.application.mealtimecoach.ui.activities.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.application.mealtimecoach.R;
import com.astritveliu.boom.Boom;

public class GetStartedOrLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_or_login);

        initializeViews();
    }

    private void initializeViews() {
        CardView getStartedCardView = findViewById(R.id.get_started_card_view);
        CardView loginCardView = findViewById(R.id.login_card_view);

        getStartedCardView.setOnClickListener(view -> startActivityFunc(new GetStartedActivity()));

        loginCardView.setOnClickListener(view -> startActivityFunc(new LoginActivity()));

        new Boom(getStartedCardView);
        new Boom(loginCardView);
    }

    private void startActivityFunc(Activity activity) {
        startActivity(new Intent(this, activity.getClass()));
        overridePendingTransition(R.anim.slide_right, R.anim.nothing);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}