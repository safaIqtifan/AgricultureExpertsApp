package com.example.agricultureexpertsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agricultureexpertsapp.databinding.ActivityLoginAsBinding;

public class LoginAs extends AppCompatActivity {

    ActivityLoginAsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginAsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.guestEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginAs.this, LoginActivity.class));
                finish();

            }
        });

        binding.farmerEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginAs.this, HomePageActivity.class));
                finish();

            }
        });

        binding.engineerEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginAs.this, HomePageActivity.class));
                finish();

            }
        });


    }
}