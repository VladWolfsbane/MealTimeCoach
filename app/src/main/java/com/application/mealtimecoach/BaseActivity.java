package com.application.mealtimecoach;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.application.mealtimecoach.ui.activities.auth.GetStartedOrLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // it will automatically start the get started or login activity if the user has logged out
                startActivity(new Intent(getApplicationContext(), GetStartedOrLoginActivity.class));
                overridePendingTransition(R.anim.slide_right, R.anim.nothing);
                finish();
            }

        };

        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
