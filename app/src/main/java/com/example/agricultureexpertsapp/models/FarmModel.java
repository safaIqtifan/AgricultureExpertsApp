package com.example.agricultureexpertsapp.models;

import java.io.Serializable;

public class FarmModel implements Serializable {

    public String name;
    public String mobile;
    public String location;
    public String area;
    public String personal_id;
    public String owner_type;
//    public List<CategoryModel> Categories;


    public FarmModel(String name, String mobile, String location, String area, String personal_id, String owner_type) {
        this.name = name;
        this.mobile = mobile;
        this.location = location;
        this.area = area;
        this.personal_id = personal_id;
        this.owner_type = owner_type;
    }
}
