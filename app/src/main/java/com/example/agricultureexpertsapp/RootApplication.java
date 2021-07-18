package com.example.agricultureexpertsapp;

import com.akexorcist.localizationactivity.ui.LocalizationApplication;
import com.example.agricultureexpertsapp.classes.SharedPManger;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RootApplication extends LocalizationApplication {

//    public static Realm dbRealm = null;

    private static RootApplication instance;
    private  SharedPManger sharedPManger;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        sharedPManger = new SharedPManger(instance);
//        Realm.init(this);

//        String realmName = "Agriculture_Experts_App";
//        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).allowWritesOnUiThread(true).build();
//        dbRealm = Realm.getInstance(config);

    }

    public static RootApplication getInstance() {
        return instance;
    }

    public  SharedPManger getSharedPManger() {
        return sharedPManger;
    }

    @NotNull
    @Override
    public Locale getDefaultLanguage() {
        return Locale.ENGLISH;
    }
}