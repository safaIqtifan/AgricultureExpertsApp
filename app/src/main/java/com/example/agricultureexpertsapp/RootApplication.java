package com.example.agricultureexpertsapp;

import com.akexorcist.localizationactivity.ui.LocalizationApplication;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RootApplication extends LocalizationApplication {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @NotNull
    @Override
    public Locale getDefaultLanguage() {
        return Locale.ENGLISH;
    }
}