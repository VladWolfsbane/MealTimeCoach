package com.application.mealtimecoach.ui.activities.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.application.mealtimecoach.R;
import com.application.mealtimecoach.ui.activities.MainActivity;
import com.astritveliu.boom.Boom;

import java.util.Objects;

import cdflynn.android.library.checkview.CheckView;

public class PaymentGatewayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        CheckMarkDialog checkMarkDialog = new CheckMarkDialog(PaymentGatewayActivity.this);
        Objects.requireNonNull(checkMarkDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        checkMarkDialog.setCanceledOnTouchOutside(false);
        checkMarkDialog.setCancelable(false);

        CardView doneCardView = findViewById(R.id.done_card_view);
        doneCardView.setOnClickListener(view -> {
            if (TextUtils.isEmpty(((EditText) findViewById(R.id.credit_card_number_edit_text)).getText().toString())) {
                return;
            }
            if (TextUtils.isEmpty(((EditText) findViewById(R.id.credit_card_date_edit_text)).getText().toString())) {
                return;
            }
            if (TextUtils.isEmpty(((EditText) findViewById(R.id.cvv_edit_text)).getText().toString())) {
                return;
            }
            checkMarkDialog.show();
            new Handler().postDelayed(checkMarkDialog::checkMate, 3000);

        });

        new Boom(doneCardView);
    }

    @Override
    public void onBackPressed() {
        // nothing
    }

    public static class CheckMarkDialog extends Dialog {

        public Activity activity;
        private CheckView checkView;
        private LinearLayout uploadingLinear;

        public CheckMarkDialog(Activity a) {
            super(a);
            this.activity = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(null);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.checkmark_dialog_layout);

            uploadingLinear = findViewById(R.id.uploading_linear);
            checkView = findViewById(R.id.check_mark);

        }

        public void checkMate() {
            uploadingLinear.setVisibility(View.GONE);
            checkView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                checkView.check();
                new Handler().postDelayed(() -> {
                    dismiss();

                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.overridePendingTransition(R.anim.slide_right, R.anim.nothing);

                }, 1000);
            }, 300);
        }

        @Override
        protected void onStop() {
            super.onStop();
            uploadingLinear.setVisibility(View.VISIBLE);
            checkView.setVisibility(View.GONE);
            checkView.uncheck();
        }
    }
}