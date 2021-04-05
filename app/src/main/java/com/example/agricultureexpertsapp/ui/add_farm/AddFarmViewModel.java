package com.example.agricultureexpertsapp.ui.add_farm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddFarmViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddFarmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}