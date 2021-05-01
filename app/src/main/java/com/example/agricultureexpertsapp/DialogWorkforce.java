package com.example.agricultureexpertsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.EditText;

public class DialogWorkforce extends Dialog {

    EditText workerIDNumber, name, adress, mobileNumber;
//    RadioGroup
    Activity activity;

    public DialogWorkforce(Context context) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_profile);
        setCancelable(false);
//        setTitle(title);

        workerIDNumber = findViewById(R.id.worker_iD_number);
        name = findViewById(R.id.wName);
        adress = findViewById(R.id.wAdress);
        mobileNumber = findViewById(R.id.wMobile_number);

        //messageTxt.setText(message);

//        if (okStr != 0)
//            okBtn.setText(okStr);
//        if (cancelStr != 0)
//            cancelBtn.setText(cancelStr);
//
//        okBtn.setOnClickListener(view -> {
//            if (okCall != null)
//                okCall.click();
//            dismiss();
//        });
//        cancelBtn.setOnClickListener(view -> {
//            if (cancelCall != null)
//                cancelCall.click();
//            dismiss();
//        });

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
