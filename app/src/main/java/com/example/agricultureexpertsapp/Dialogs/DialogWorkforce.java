package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.agricultureexpertsapp.Dialogs.DialogProfil;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.WorkforceModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

public class DialogWorkforce extends Dialog {

    EditText workerIDNumberEt, nameEt, adressEt, mobileNumberEt;
    RadioButton Female;
    RadioButton Male;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogWorkforce(Activity context, final DataCallBack okCall) {
        super(context);

        activity = context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.popup_workforce);
//        setTitle(title);

        workerIDNumberEt = findViewById(R.id.worker_iD_number);
        nameEt = findViewById(R.id.workerName);
        adressEt = findViewById(R.id.workerAdress);
        mobileNumberEt = findViewById(R.id.workerMobile_number);
        Female = findViewById(R.id.female);
        Male = findViewById(R.id.male);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        //messageTxt.setText(message);

        okBtn.setOnClickListener(view -> {

            String workerIDNumberStr = workerIDNumberEt.getText().toString();
            String nameStr = nameEt.getText().toString();
            String adressStr = adressEt.getText().toString();
            String mobileNumberStr = mobileNumberEt.getText().toString();

            dismiss();
            if (okCall != null) {
                okCall.Result(workerIDNumberStr, "", null);
                okCall.Result(nameStr, "", null);
                okCall.Result(adressStr, "", null);
                okCall.Result(mobileNumberStr, "", null);

                WorkforceModel workforceModel = new WorkforceModel();
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
