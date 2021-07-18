package com.example.agricultureexpertsapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FmessageModel {

    public String user_id;
    public String user_avatar;
    public String content;
    public String type;
    //    public String fcm;
    public Date my_date;
    @ServerTimestamp
    public Date date;

}
