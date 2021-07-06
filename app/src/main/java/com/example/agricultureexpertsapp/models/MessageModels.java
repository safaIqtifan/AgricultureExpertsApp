package com.example.agricultureexpertsapp.models;

public class MessageModels {

    public String name;
    public String message_body;
    public String date;
    public String photo;

    public MessageModels() {
    }

    public MessageModels(String name, String message_body, String date) {
        this.name = name;
        this.message_body = message_body;
        this.date = date;
    }
}
