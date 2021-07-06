package com.example.agricultureexpertsapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PostsModel {

    public String post_id;
    public String description;
    public String date;
    public String photo;
    @ServerTimestamp
    public Date created_at;

    public PostsModel() {
    }

    public PostsModel(String description, String date) {
        this.description = description;
        this.date = date;
    }
}
