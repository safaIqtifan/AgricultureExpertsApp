package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.IrrigationSourcesModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

public class DialogIrrigationSourcess extends Dialog {

    //    TextView chosenTypeTv;
    RadioButton groundWaterRb;
    RadioButton wellRb;
    RadioButton tankRb;
    EditText wellDepthEt, storageCapacityEt;
    Button cancelBtn, okBtn;
    Activity activity;

    String selectedType = null;

    public DialogIrrigationSourcess(Activity context, final DataCallBack okCall) {
        super(context);

        activity = context;

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup_irrigation_sources);

//        chosenTypeTv = findViewById(R.id.chosenTypeTv);
        wellDepthEt = findViewById(R.id.well_depth);
        storageCapacityEt = findViewById(R.id.storage_capacity);
        groundWaterRb = findViewById(R.id.groundWaterRb);
        wellRb = findViewById(R.id.wellRb);
        tankRb = findViewById(R.id.tankRb);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        groundWaterRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedType = activity.getString(R.string.ground_water);
                wellDepthEt.setVisibility(View.GONE);
                storageCapacityEt.setVisibility(View.GONE);
            }
        });

        wellRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedType = activity.getString(R.string.well);
                wellDepthEt.setVisibility(View.VISIBLE);
                storageCapacityEt.setVisibility(View.VISIBLE);
            }
        });

        tankRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedType = activity.getString(R.string.tank);
                wellDepthEt.setVisibility(View.GONE);
                storageCapacityEt.setVisibility(View.VISIBLE);
            }
        });

        groundWaterRb.performClick();

        okBtn.setOnClickListener(view -> {

            String wellDepthStr = wellDepthEt.getText().toString();
            String capacityStr = storageCapacityEt.getText().toString();

            // here we can make validation

            IrrigationSourcesModel irrigationSourcesModel = new IrrigationSourcesModel();
            irrigationSourcesModel.type = selectedType;
            irrigationSourcesModel.depth = Double.parseDouble(wellDepthStr);
            irrigationSourcesModel.capacity = Double.parseDouble(capacityStr);

            dismiss();

            if (okCall != null) {
                okCall.Result(irrigationSourcesModel, "", null);
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
