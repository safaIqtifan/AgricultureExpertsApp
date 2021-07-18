package com.example.agricultureexpertsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.agricultureexpertsapp.classes.UtilityApp;
import com.example.agricultureexpertsapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends BaseActivity {


    private NavController navController;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.test);
//        TextView textView = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbar_title);
//
//        textView.setText("SAFA TOOLBAR");
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("aaaa");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_discussion, R.id.navigation_add, R.id.navigation_favorite)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getMyProfile();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//
//       if (!navController.popBackStack()) {
//            finish();
//        }
//        Log.e(TAG, "onBackPressed: - " + navController.g);
//        Toast.makeText(this, "mmmmmm", Toast.LENGTH_SHORT).show();
    }

    private void getMyProfile() {

        FirebaseFirestore.getInstance().collection(Constants.USER).document(firebaseUser.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            UserModel userModel = task.getResult().toObject(UserModel.class);
                            UtilityApp.setUserData(userModel);
                        }
                    }
                });
    }


    public NavController getNavController() {
        return navController;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: " + requestCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("TAG", "1165461");
    }
}