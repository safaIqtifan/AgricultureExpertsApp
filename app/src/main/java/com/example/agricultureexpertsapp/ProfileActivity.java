package com.example.agricultureexpertsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.Adapter.ProfilePagerAdapter;
import com.example.agricultureexpertsapp.classes.UtilityApp;
import com.example.agricultureexpertsapp.databinding.ActivityProfileBinding;
import com.example.agricultureexpertsapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileActivity extends BaseActivity {

    Uri profilePhotoUri;
    UserModel userModel;

    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB;
    StorageReference storageRef;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(profilePagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

//        loadingLY = findViewById(R.id.loadingL);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userModel = (UserModel) getIntent().getSerializableExtra(Constants.KEY_USER_MODEL);
        fireStoreDB = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        if (userModel != null)
            initData();
        else {
            binding.buttonLY.setVisibility(View.GONE);
            getMyProfile();
        }

        binding.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActiviy(), MessageActivity.class);
                intent.putExtra(Constants.KEY_USER_MODEL, userModel);
                startActivity(intent);
            }
        });

        binding.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
//                        GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait_loading));
                    }
                } else {
                    pickImageFromGallery();
                }
            }

        });
    }

    private void initData() {

        binding.toolbarLayout.setTitle(userModel.username);
        setSupportActionBar(binding.toolbar);

        Glide.with(getActiviy())
                .asBitmap()
                .load(userModel.imageURL)
                .placeholder(R.drawable.profile)
                .into(binding.profileImg);

        if (!userModel.user_id.equals(firebaseUser.getUid())) {
            binding.buttonLY.setVisibility(View.VISIBLE);
        } else {
            binding.buttonLY.setVisibility(View.GONE);
        }
    }

    private void getMyProfile() {

        userModel = UtilityApp.getUserData();
        if (userModel == null){
            GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait_loading));
            FirebaseFirestore.getInstance().collection(Constants.USER).document(firebaseUser.getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            GlobalHelper.hideProgressDialog();
                            if (task.isSuccessful()) {
                                userModel = task.getResult().toObject(UserModel.class);
                                initData();
                            } else {
                                Toast.makeText(getActiviy(), getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            initData();
        }

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
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            profilePhotoUri = data.getData();
            Glide.with(getActiviy())
                    .asBitmap()
                    .load(profilePhotoUri)
                    .placeholder(R.drawable.profile)
                    .into(binding.profileImg);

            uploadPhoto(profilePhotoUri);

        }
    }

    private void uploadPhoto(Uri photoUri) {

        GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait));
        StorageReference imgRef = storageRef.child("UsersImages" + "/" + UUID.randomUUID().toString());
//        loadingLY.setVisibility(View.VISIBLE);

        UploadTask uploadTask = imgRef.putFile(photoUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                exception.printStackTrace();
            }

        }).addOnSuccessListener(taskSnapshot -> {
            imgRef.getDownloadUrl().addOnCompleteListener(task -> {

                userModel.imageURL = task.getResult().toString();
                sendImageToFirebase(userModel.imageURL);

            });


        });
    }


    private void sendImageToFirebase(String photoUrl) {

        Map<String, Object> profileModelMap = new HashMap<>();
        profileModelMap.put("imageURL", photoUrl);

        fireStoreDB.collection(Constants.USER).document(firebaseUser.getUid()).update(profileModelMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait));

//                        loadingLY.setVisibility(View.GONE);
                        GlobalHelper.hideProgressDialog();

                        Glide.with(getActiviy())
                                .asBitmap()
                                .load(photoUrl)
                                .placeholder(R.drawable.profile)
                                .into(binding.profileImg);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.success_add), Toast.LENGTH_SHORT).show();

                            GlobalHelper.hideProgressDialog();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}