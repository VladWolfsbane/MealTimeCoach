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
import com.application.mealtimecoach.ui.activities.MainActivity;
import com.application.mealtimecoach.viewmodels.auth.LoginViewModel;
import com.application.mealtimecoach.viewmodels.ViewModelProviderFactory;
import com.astritveliu.boom.Boom;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends DaggerAppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private ProgressBar progressBar;
    private TextView loginTextView;
    private CardView loginCardView;
    private LoginViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this, providerFactory).get(LoginViewModel.class);

        initializeViews();
        subscribeToObservers();

        loginCardView.setOnClickListener(view -> {
            if (TextUtils.isEmpty(emailEditText.getText().toString().trim())) {
                return;
            }
            if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
                return;
            }
            login();
        });
    }

    private void login() {
        viewModel.login(
                emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim());
    }

    private void showProgressbar(boolean isShown) {
        progressBar.setVisibility(isShown ? View.VISIBLE : View.INVISIBLE);
        loginTextView.setVisibility(!isShown ? View.VISIBLE : View.INVISIBLE);
        loginCardView.setEnabled(!isShown);
    }

    // observing the mutable live data from the viewModel to update the UI
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

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        loginCardView = findViewById(R.id.login_button_card_view);
        progressBar = findViewById(R.id.progress_bar);
        loginTextView = findViewById(R.id.login_text_view);

        new Boom(loginCardView);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_right_out);
    }
}