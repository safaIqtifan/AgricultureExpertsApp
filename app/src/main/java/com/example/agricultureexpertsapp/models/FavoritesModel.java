package com.example.agricultureexpertsapp.models;

public class FavoritesModel {

    public String userName;
    public String description;
    public String date;
    public String photo;

    public FavoritesModel() {
    }

    public FavoritesModel(String userName, String description, String date) {
        this.userName = userName;
        this.description = description;
        this.date = date;
    }
}
