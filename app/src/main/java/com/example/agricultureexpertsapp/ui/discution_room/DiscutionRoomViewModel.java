package com.example.agricultureexpertsapp.ui.discution_room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscutionRoomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiscutionRoomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Discussion Room fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}