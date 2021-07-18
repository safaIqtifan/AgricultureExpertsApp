package com.example.agricultureexpertsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.agricultureexpertsapp.models.PostsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPostActivity extends BaseActivity {

    EditText userNameEd, describctionEd;
    RelativeLayout loadingLY;
    ImageView postPhoto;
    Button add;
    TextView title;
    Uri postPhotoUri;
    PostsModel postModel;

    FirebaseFirestore fireStoreDB;
    StorageReference storageRef;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postPhoto = findViewById(R.id.post_photo);
        describctionEd = findViewById(R.id.text_describctionEd);
        loadingLY = findViewById(R.id.loadingLY);
        add = findViewById(R.id.addBtn);
        title = findViewById(R.id.title);

        title.setText("Add Post");

        postModel = new PostsModel();

        fireStoreDB = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);

                    } else {
                        pickImageFromGallery();
                    }
                } else {
                      pickImageFromGallery();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userId = auth.getUid();
//                addModel.user_id = userId;
                checkData();
            }
        });

    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            postPhotoUri = data.getData();
            postPhoto.setImageURI(postPhotoUri);
        }
    }


    private void uploadPhoto(Uri photoUri) {

        StorageReference imgRef = storageRef.child("PostsImages" + "/" + UUID.randomUUID().toString());

        loadingLY.setVisibility(View.VISIBLE);
        UploadTask uploadTask = imgRef.putFile(photoUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                GlobalHelper.hideProgressDialog();
            }
        }).addOnSuccessListener(taskSnapshot -> {

            imgRef.getDownloadUrl().addOnCompleteListener(task -> {

                postModel.photo = task.getResult().toString();
                System.out.println("Log uploaded url " + postModel.photo);
                sendPostToFirebase();
            });


        });
    }

    private void checkData() {

        String describctionStr = describctionEd.getText().toString();

        boolean hasError = false;
        if (postPhotoUri == null) {
            Toast.makeText(this, getString(R.string.please_add_photo), Toast.LENGTH_SHORT).show();
            hasError = true;
        }
        if (describctionStr.isEmpty()) {
            describctionEd.setError(getString(R.string.invalid_input));
            hasError = true;
        }
        if (hasError)
            return;

        postModel.description = describctionStr;

        uploadPhoto(postPhotoUri);

    }


    private void sendPostToFirebase() {

        String postId = fireStoreDB.collection(Constants.POST).document().getId(); // this is auto genrat

        Map<String, Object> postModelMap = new HashMap<>();
        postModelMap.put("post_id", postId);
        postModelMap.put("description", postModel.description);
        postModelMap.put("date", postModel.date);
        postModelMap.put("photo", postModel.photo);
        postModelMap.put("created_at", FieldValue.serverTimestamp());

        fireStoreDB.collection(Constants.POST).document(postId).set(postModelMap, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingLY.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), getString(R.string.success_add_post), Toast.LENGTH_SHORT).show();
                            onBackPressed();

                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.fail_add_post), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}