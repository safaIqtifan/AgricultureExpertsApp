package com.example.agricultureexpertsapp.models;

import java.io.Serializable;

public class UserModel implements Serializable {

    public String user_id;
    public String username;
    public String message_body;
    public String date;
    public String imageURL;

    public UserModel() {
    }

    public UserModel(String name, String message_body, String date) {
        this.username = name;
        this.message_body = message_body;
        this.date = date;
    }

//    public String getId() {
//        return user_id;
//    }
//
//    public void setId(String id) {
//        this.user_id = id;
//    }
//
//    public String getName() {
//        return username;
//    }
//
//    public void setName(String name) {
//        this.username = name;
//    }
//
//    public String getMessage_body() {
//        return message_body;
//    }
//
//    public void setMessage_body(String message_body) {
//        this.message_body = message_body;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getPhoto() {
//        return imageURL;
//    }
//
//    public void setPhoto(String photo) {
//        this.imageURL = photo;
//    }
}
