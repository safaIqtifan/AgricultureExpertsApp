package com.example.agricultureexpertsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogIrrigationSourcess extends Dialog {

    TextView chosen_item;
    EditText wellDepth, storagecapacity;
    AutoCompleteTextView ownerFarmType;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogIrrigationSourcess(Context context, int message, int okStr, int cancelStr, final DialogProfil.Click okCall, final DialogProfil.Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_irrigation_sources);
        setCancelable(false);
//        setTitle(title);

        chosen_item = findViewById(R.id.chosen_item);
        wellDepth = findViewById(R.id.well_depth);
        storagecapacity = findViewById(R.id.storage_capacity);
        ownerFarmType = findViewById(R.id.irrigation_sourcess_type);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        //messageTxt.setText(message);

        if (okStr != 0)
            okBtn.setText(okStr);
        if (cancelStr != 0)
            cancelBtn.setText(cancelStr);

        okBtn.setOnClickListener(view -> {
            if (okCall != null)
                okCall.click();
            dismiss();
        });
        cancelBtn.setOnClickListener(view -> {
            if (cancelCall != null)
                cancelCall.click();
            dismiss();
        });

        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }


    }

    public static abstract class Click {
        public abstract void click();
    }
}
