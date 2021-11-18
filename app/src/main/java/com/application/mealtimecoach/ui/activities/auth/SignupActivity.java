package com.application.mealtimecoach.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.application.mealtimecoach.R;
import com.application.mealtimecoach.models.SignupModel;
import com.application.mealtimecoach.utils.Constants;
import com.application.mealtimecoach.viewmodels.auth.SignupViewModel;
import com.application.mealtimecoach.viewmodels.ViewModelProviderFactory;
import com.astritveliu.boom.Boom;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import es.dmoral.toasty.Toasty;

public class SignupActivity extends DaggerAppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private ProgressBar progressBar;

    private TextView signupTextView;

    private CardView signupCardView;

    private SignupViewModel viewModel;

    private String name;
    private String diet;
    private String ageGroup;
    private String goal;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        viewModel = new ViewModelProvider(this, providerFactory).get(SignupViewModel.class);

        initializeViews();
        subscribeToObservers();

        signupCardView.setOnClickListener(view -> {
            if (TextUtils.isEmpty(emailEditText.getText().toString().trim())) {
                return;
            }
            if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
                return;
            }
            if (name == null || diet == null || ageGroup == null || goal == null) {
                return;
            }
            signup();
        });
    }

    private void signup() {

        SignupModel signupModel = new SignupModel();
        signupModel.setName(name);
        signupModel.setDiet(diet);
        signupModel.setAgeGroup(ageGroup);
        signupModel.setGoal(goal);

        viewModel.signup(
                emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim(),
                signupModel);
    }

    private void showProgressbar(boolean isShown) {
        progressBar.setVisibility(isShown ? View.VISIBLE : View.INVISIBLE);
        signupTextView.setVisibility(!isShown ? View.VISIBLE : View.INVISIBLE);
        signupCardView.setEnabled(!isShown);
    }

    private void subscribeToObservers() {
        viewModel.getAuthResourceMutableLiveData().observe(this, userAuthResource -> {
            if (userAuthResource != null)
                switch (userAuthResource.status) {
                    case ERROR:
                        showProgressbar(false);
                        if (userAuthResource.message != null)
                            Toasty.error(getApplicationContext(), userAuthResource.message).show();

                        break;
                    case LOADING:
                        showProgressbar(true);
                        break;
                    case AUTHENTICATED:
                        showProgressbar(false);

                        startActivity(new Intent(getApplicationContext(), PaymentGatewayActivity.class));
                        overridePendingTransition(R.anim.slide_right, R.anim.nothing);

                        break;
                    case NOT_AUTHENTICATED:
                        showProgressbar(false);
                        break;
                }
        });
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        signupCardView = findViewById(R.id.signup_button_card_view);
        progressBar = findViewById(R.id.progress_bar);
        signupTextView = findViewById(R.id.signup_text_view);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.NAME)) {
            name = intent.getStringExtra(Constants.NAME);
        }
        if (intent.hasExtra(Constants.DIET)) {
            diet = intent.getStringExtra(Constants.DIET);
        }
        if (intent.hasExtra(Constants.AGE_GROUP)) {
            ageGroup = intent.getStringExtra(Constants.AGE_GROUP);
        }
        if (intent.hasExtra(Constants.GOAL)) {
            goal = intent.getStringExtra(Constants.GOAL);
        }

        new Boom(signupCardView);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_right_out);
    }
}