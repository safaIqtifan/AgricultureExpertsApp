package com.example.agricultureexpertsapp.models;

public class NotificationModel {

    public String title;
    public String notification_body;
    public String photo;

    public NotificationModel() {
    }

    public NotificationModel(String title, String notification_body) {
        this.title = title;
        this.notification_body = notification_body;
    }

}
