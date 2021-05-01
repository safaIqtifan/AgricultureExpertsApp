package com.example.agricultureexpertsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DialogFieldCrops extends Dialog {

    EditText productTypeName, plantedArea,
            plantedDate, growthTime,
            cropExpirationDate;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogFieldCrops(Context context, int message, int okStr, int cancelStr, final DialogProfil.Click okCall, final DialogProfil.Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_profile);
        setCancelable(false);
//        setTitle(title);

        productTypeName = findViewById(R.id.product_type_name);
        plantedArea = findViewById(R.id.description);
        plantedDate = findViewById(R.id.planted_date);
        growthTime = findViewById(R.id.growth_time);
        cropExpirationDate = findViewById(R.id.crop_expiration_date);
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
