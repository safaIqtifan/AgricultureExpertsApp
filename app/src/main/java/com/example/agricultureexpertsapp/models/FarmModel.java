package com.example.agricultureexpertsapp.models;

import java.io.Serializable;

public class FarmModel implements Serializable {

    public String farm_id;
    public String user_id;
    public String name;
    public String mobile;
    public String location;
    public double area;
    public int personal_id;
    public String owner_type;
    public String photo;
//    public List<CategoryModel> Categories;


    public FarmModel() {
    }

    public FarmModel(String name, String mobile, String location, double area, int personal_id, String owner_type) {
        this.name = name;
        this.mobile = mobile;
        this.location = location;
        this.area = area;
        this.personal_id = personal_id;
        this.owner_type = owner_type;
    }
}
