package com.example.agricultureexpertsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MoreDetails extends BaseActivity {

    Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        util= (Util)getIntent().getSerializableExtra("util");

        getChecked();
    }

    ArrayList<Util.MainLink> getChecked(){
        ArrayList<Util.MainLink> selected = new ArrayList<>();
        if (util!=null){
            for (int i = 0; i < util.getLinks().size(); i++) {
                if (util.getLinks().get(i).isChecked){
                    selected.add(util.getLinks().get(i));
                }
            }
        }
        return selected;
    }
}