package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Intent i = new Intent(OnboardingActivity.this, SignupActivity.class);
        startActivity(i);
        finish();

    }
}