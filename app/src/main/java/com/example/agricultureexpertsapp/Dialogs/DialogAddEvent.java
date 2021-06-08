package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.AddEventModel;
import com.example.agricultureexpertsapp.models.IrrigationSourcesModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

public class DialogAddEvent extends Dialog {

    EditText textEventEt;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogAddEvent(Context context, final DataCallBack okCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_add_event);
//        setCancelable(false);
//        setTitle(title);

        textEventEt = findViewById(R.id.text_event);


        okBtn.setOnClickListener(view -> {

            String textEventStr = textEventEt.getText().toString();

            dismiss();
            if (okCall != null) {

                okCall.Result(textEventStr, "", null);
                AddEventModel dialogAddEvent = new AddEventModel();
            }
        });
        cancelBtn.setOnClickListener(view -> {

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
