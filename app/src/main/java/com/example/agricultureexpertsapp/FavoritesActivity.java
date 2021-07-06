package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.agricultureexpertsapp.Adapter.FavoritesAdapter;
import com.example.agricultureexpertsapp.Adapter.NotificationAdapter;
import com.example.agricultureexpertsapp.models.FavoritesModel;
import com.example.agricultureexpertsapp.models.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeToRefreshLY;
    RecyclerView rv;
    FavoritesAdapter adapter;
    List<FavoritesModel> favoritesModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        swipeToRefreshLY = findViewById(R.id.swipeToRefreshLY);
        rv = findViewById(R.id.favoritesRV);

        favoritesModelList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(FavoritesActivity.this, favoritesModelList);
        rv.setAdapter(adapter);

        swipeToRefreshLY.setEnabled(false);

    }
}