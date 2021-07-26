package com.example.agricultureexpertsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agricultureexpertsapp.Adapter.UserAdapter;
import com.example.agricultureexpertsapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView userNme;
    ProgressBar loadingLY;

    RecyclerView rv;
    UserAdapter adapter;
    List<UserModel> userModelist;
    UserModel userModel;

    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        profileImage = findViewById(R.id.message_imag);
        userNme = findViewById(R.id.title);
        loadingLY = findViewById(R.id.loadingLY);
        rv = findViewById(R.id.rv);

        fireStoreDB = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        profileImage.setVisibility(View.VISIBLE);
        profileImage.setVisibility(View.VISIBLE);

        rv.setLayoutManager(new LinearLayoutManager(UserActivity.this));

        userModelist = new ArrayList<>();
        adapter = new UserAdapter(UserActivity.this, userModelist);
        rv.setAdapter(adapter);

        readUsers(true);
        //fetchData(true);

    }

    private void readUsers(boolean showLoading) {

        if (showLoading) {
            loadingLY.setVisibility(View.VISIBLE);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreDB.collection(Constants.USER).whereNotEqualTo("user_id", firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                loadingLY.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    userModelist.clear();

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        UserModel user = document.toObject(UserModel.class);
                        userModelist.add(user);

//                        if (user.getId().equals(firebaseUser.getUid())) {
//                        }
                    }

                    adapter.list = userModelist;
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(UserActivity.this, getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}