package com.example.agricultureexpertsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Util implements Serializable {

    public ArrayList<MainLink> getLinks(){
        ArrayList<MainLink> links = new ArrayList<>();
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Fhothouse_plant.png?alt=media&token=a1d1236e-2645-433a-b129-01565806e164"));
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Fgreenhouses.png?alt=media&token=9bafe9ef-af04-46d4-a14d-29c1382d5dd6"));
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Firrigation_sources.png?alt=media&token=76169c9c-c19c-4a6f-b17b-32000101db14"));
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Ffield_crops.png?alt=media&token=b93d362d-640d-4a51-9727-b791423e6bb3"));
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Fworkforce.png?alt=media&token=20c7a421-1674-46b6-99f5-31eed4f3a753"));
        links.add(new MainLink("https://firebasestorage.googleapis.com/v0/b/agricultureexpertsapp.appspot.com/o/Images%2Ftools_equipment.png?alt=media&token=133fac7d-cadd-4657-96a5-f6de65ab1074"));


        return links;
    }

    public class MainLink implements Serializable {

        String name = "";
        String link;
        boolean isChecked = false;

        public MainLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
