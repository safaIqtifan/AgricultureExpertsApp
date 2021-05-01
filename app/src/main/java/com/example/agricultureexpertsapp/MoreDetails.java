package com.example.agricultureexpertsapp;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.agricultureexpertsapp.ui.add_farm.AdapterCategories;
import com.example.agricultureexpertsapp.models.CategoryModel;
import com.example.agricultureexpertsapp.ui.add_farm.DataCallBack;

import java.util.ArrayList;

public class MoreDetails extends BaseActivity {

    TextView title;
    RecyclerView categoriesRV;
    ArrayList<CategoryModel> selectedCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        title = findViewById(R.id.title);
        categoriesRV = findViewById(R.id.details_recyclerView);

        categoriesRV.setLayoutManager(new GridLayoutManager( getActiviy(), 3, GridLayoutManager.VERTICAL, false));

        selectedCategories= (ArrayList<CategoryModel>) getIntent().getSerializableExtra("selected");

        title.setText(R.string.title_create);
        initData();

    }



    private void initData() {

        AdapterCategories adapter = new AdapterCategories(getActiviy(), selectedCategories, AdapterCategories.CLICK, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {
                CategoryModel categoryModel = (CategoryModel) obj;



            }
        });
        categoriesRV.setAdapter(adapter);


    }


}