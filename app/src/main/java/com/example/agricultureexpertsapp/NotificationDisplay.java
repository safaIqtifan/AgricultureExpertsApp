package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agricultureexpertsapp.Adapter.NotificationAdapter;
import com.example.agricultureexpertsapp.models.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationDisplay extends AppCompatActivity {

    RecyclerView rv;
    NotificationAdapter adapter;
    List<NotificationModel> notificationModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_display);

        notificationModelList = new ArrayList<>();
        rv = findViewById(R.id.notificationRV);
        adapter = new NotificationAdapter(NotificationDisplay.this, notificationModelList);
        rv.setAdapter(adapter);

    }
}