package com.application.mealtimecoach.ui.activities.auth;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.application.mealtimecoach.R;
import com.application.mealtimecoach.utils.Constants;
import com.astritveliu.boom.Boom;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import es.dmoral.toasty.Toasty;

public class GetStartedActivity extends AppCompatActivity {

    private EditText nameEditText;

    private TextView nameTextview;
    private TextView dietTextview;
    private TextView ageGroupTextview;
    private TextView goalTextview;

    private PowerSpinnerView dietDropdownView;
    private PowerSpinnerView ageGroupDropdownView;
    private PowerSpinnerView goalDropdownView;


    private CardView doneCardView;
    private CardView signupCardView;
    private CardView bottomCardView;

    private String diet;
    private String ageGroup;
    private String goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        initializeViews();

        // done button function for calculating the bmi of the user
        doneCardView.setOnClickListener(view -> {
            if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                return;
            }
            if (diet == null) {
                return;
            }
            if (ageGroup == null) {
                return;
            }
            if (goal == null) {
                return;
            }
            setTextViewsOnDoneClick();
        });

        // starting the sign up activity and passing the weight, height and level
        signupCardView.setOnClickListener(view -> {
            if (diet == null) {
                Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_dietary_preference)).show();
                return;
            }
            if (ageGroup == null) {
                Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_age_group)).show();
                return;
            }
            if (goal == null) {
                Toasty.error(getApplicationContext(), getResources().getString(R.string.what_is_goal)).show();
                return;
            }
            Intent intent = new Intent(this, SignupActivity.class);
            intent.putExtra(Constants.NAME, nameEditText.getText().toString());
            intent.putExtra(Constants.DIET, diet);
            intent.putExtra(Constants.AGE_GROUP, ageGroup);
            intent.putExtra(Constants.GOAL, goal);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.nothing);
        });
    }

    private void setTextViewsOnDoneClick() {
        Resources resources = getResources();

        String Diet_String = resources.getString(R.string.diet) + ": " + dietDropdownView.getText().toString().trim();
        String Age_String = resources.getString(R.string.age) + ": " + ageGroupDropdownView.getText().toString().trim();

        String Goal_String = resources.getString(R.string.goal) + ": " + goalDropdownView.getText().toString().trim();

        if (bottomCardView.getVisibility() == View.GONE) {
            bottomCardView.setVisibility(View.VISIBLE);
        }
        nameTextview.setText(nameEditText.getText().toString().trim());
        dietTextview.setText(Diet_String);
        ageGroupTextview.setText(Age_String);
        goalTextview.setText(Goal_String);
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.name_edit_text);
        doneCardView = findViewById(R.id.done_button_card_view);
        signupCardView = findViewById(R.id.signup_button_card_view);
        nameTextview = findViewById(R.id.name_text_view);
        dietTextview = findViewById(R.id.diet_text_view);
        ageGroupTextview = findViewById(R.id.age_text_view);
        goalTextview = findViewById(R.id.goal_text_view);
        dietDropdownView = findViewById(R.id.diets_drop_down);
        ageGroupDropdownView = findViewById(R.id.age_group_drop_down);
        goalDropdownView = findViewById(R.id.goal_drop_down);
        bottomCardView = findViewById(R.id.bottom_card_view);

        dietDropdownView.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (i, string) -> {
            switch (i) {
                case 0:
                    diet = Constants.BALANCED;
                    break;
                case 1:
                    diet = Constants.VEGAN;
                    break;
                default:
                    diet = Constants.PALEO;
                    break;
            }

        });

        ageGroupDropdownView.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (i, string) -> {
            switch (i) {
                case 0:
                    ageGroup = Constants.BELOW_18;
                    break;
                case 1:
                    ageGroup = Constants.EIGHTEEN_THIRTY;
                    break;
                default:
                    ageGroup = Constants.OVER_30;
                    break;
            }

        });

        goalDropdownView.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (i, string) -> {
            if (i == 0) {
                goal = Constants.EATING_HEALTHY;
            } else {
                goal = Constants.LOSING_WEIGHT;
            }

        });

        new Boom(doneCardView);
        new Boom(signupCardView);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_right_out);
    }
}