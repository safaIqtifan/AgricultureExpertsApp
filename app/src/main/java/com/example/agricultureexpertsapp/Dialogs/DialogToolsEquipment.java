package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.agricultureexpertsapp.Dialogs.DialogProfil;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.IrrigationSourcesModel;
import com.example.agricultureexpertsapp.models.ToolsEquipmentModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

public class DialogToolsEquipment extends Dialog {

    EditText machineTypeEt, propertyTypeEt, machinesNumberEt;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogToolsEquipment(Activity context, final DataCallBack okCall) {
        super(context);

        activity = context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_tools_equipment);
        setCancelable(false);
//        setTitle(title);

        machineTypeEt = findViewById(R.id.machine_type);
        propertyTypeEt = findViewById(R.id.property_type);
        machinesNumberEt = findViewById(R.id.machines_number);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);


        okBtn.setOnClickListener(view -> {

            String machineTypeStr = machineTypeEt.getText().toString();
            String propertyTypeStr = propertyTypeEt.getText().toString();
            String machinesNumberStr = machinesNumberEt.getText().toString();

            dismiss();
            if (okCall != null) {
                okCall.Result(machineTypeStr, "", null);
                okCall.Result(propertyTypeStr, "", null);
                okCall.Result(machinesNumberStr, "", null);

                ToolsEquipmentModel toolsEquipmentModel = new ToolsEquipmentModel();
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
