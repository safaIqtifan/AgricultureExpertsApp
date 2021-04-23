package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agricultureexpertsapp.ui.messages.MessagesFragment;

public class Profile extends BaseActivity {

    Button messagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        messagebtn = findViewById(R.id.messagebtn);

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity a = new HomePageActivity();
                if (a.getNavController() != null){
                    a.getNavController().navigate(R.id.navigation_messages);
                    finish();
                }
//                Intent anIntent = new Intent();
//                setResult(2311,anIntent);


            }
        });
    }
}