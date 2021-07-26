package com.example.agricultureexpertsapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ChatModel {

    public String id;
    public String sender_id;
    public String sender_name;
    public String sender_avatar;
    public String friend_id;
    public String friend_name;
    public String friend_avatar;
    @ServerTimestamp
    public Date created_at;

}
