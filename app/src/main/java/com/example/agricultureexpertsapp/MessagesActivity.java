package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.agricultureexpertsapp.Adapter.MessageAdapter;
import com.example.agricultureexpertsapp.models.MessageModels;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    RecyclerView rv;
    MessageAdapter adapter;
    List<MessageModels> messageModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        TextView title = findViewById(R.id.title);
        title.setText(R.string.title_messages);

        rv = findViewById(R.id.recyclerView_message);

        messageModelsList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
        adapter = new MessageAdapter(MessagesActivity.this, messageModelsList);
        rv.setAdapter(adapter);

    }
}