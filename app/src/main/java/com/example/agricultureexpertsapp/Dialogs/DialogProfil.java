package com.example.agricultureexpertsapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.ProfilModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

public class DialogProfil extends Dialog {

    ImageView profilImg;
    EditText descriptionEt;
    Button cancelBtn, okBtn;
    Activity activity;

    public DialogProfil(Activity context, final DataCallBack okCall) {
        super(context);
        activity = context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.popup_profile);


        profilImg = findViewById(R.id.postImg);
        descriptionEt = findViewById(R.id.description);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        okBtn.setOnClickListener(view -> {

//            String getprofilImg = profilImg.getImageAlpha();
            String descriptionStr = descriptionEt.getText().toString();

            dismiss();
            if (okCall != null) {
//                okCall.Result(getprofilImg, "", null);
                okCall.Result(descriptionStr, "", null);

                ProfilModel profilModel = new ProfilModel();
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

//    private void pickImageFromGallery() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    pickImageFromGallery();
//                } else {
//                    Toast.makeText(getActivity(), "Permission denied...!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//
//            farmPhotoUri = data.getData();
//            farmPhoto.setImageURI(farmPhotoUri);
//        }
//    }

    public static abstract class Click {
        public abstract void click();
    }
}
