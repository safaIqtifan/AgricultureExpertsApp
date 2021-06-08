package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.FieldCropsModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

import java.util.Calendar;

public class DialogFieldCrops extends Dialog {

    EditText productTypeNameEt, plantedAreaEt;
    TextView plantedDateTv, cropExpirationDateTv, growthTimeTv;
    Button cancelBtn, okBtn;
    Activity activity;

    int selectedYear, selectedMonth, selectedDay;
    long minDate;

    public DialogFieldCrops(Activity context, final DataCallBack okCall) {
        super(context);

        activity = context;

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.popup_field_crops);
//        setTitle(title);

        productTypeNameEt = findViewById(R.id.product_type_name);
        plantedAreaEt = findViewById(R.id.planted_area);
        plantedDateTv = findViewById(R.id.planted_date);
        growthTimeTv = findViewById(R.id.growth_time);
        cropExpirationDateTv = findViewById(R.id.crop_expiration_date);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        initDates();

        plantedDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FieldCropsDate(context, plantedDateTv);
            }
        });


        cropExpirationDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FieldCropsDate(context, cropExpirationDateTv);
            }
        });


        okBtn.setOnClickListener(view -> {

            String productTypeNameStr = productTypeNameEt.getText().toString();
            String plantedAreaStr = plantedAreaEt.getText().toString();
            String plantedDateStr = plantedDateTv.getText().toString();
            String growthTimeStr = growthTimeTv.getText().toString();
            String cropExpirationDateStr = cropExpirationDateTv.getText().toString();

            dismiss();
            if (okCall != null) {
                okCall.Result(productTypeNameStr, "", null);
                okCall.Result(plantedAreaStr, "", null);
                okCall.Result(plantedDateStr, "", null);
                okCall.Result(growthTimeStr, "", null);
                okCall.Result(cropExpirationDateStr, "", null);

                FieldCropsModel fieldCropsModel = new FieldCropsModel();
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

    private void FieldCropsDate(Context context, TextView dateTextView) {

        DatePickerDialog dateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;

                String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                dateTextView.setText(date);

            }
        }, selectedYear, selectedMonth, selectedDay);

        dateDialog.getDatePicker().setMinDate(minDate);
        dateDialog.show();


    }

    private void initDates() {

        Calendar c = Calendar.getInstance();

        selectedYear = c.get(Calendar.YEAR);
        selectedMonth = c.get(Calendar.MONTH);
        selectedDay = c.get(Calendar.DAY_OF_MONTH);
        minDate = (c.getTime().getTime() - 60000);

    }

}
