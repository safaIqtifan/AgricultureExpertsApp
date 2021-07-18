package com.example.agricultureexpertsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.Adapter.ChatsAdapter;
import com.example.agricultureexpertsapp.models.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView userNme;
    ProgressBar loadingLY;

    RecyclerView rv;
    ChatsAdapter adapter;
    List<ChatModel> chatslist;

    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        profileImage = findViewById(R.id.message_imag);
        userNme = findViewById(R.id.title);
        loadingLY = findViewById(R.id.loadingLY);
        rv = findViewById(R.id.recyclerView_message);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreDB = FirebaseFirestore.getInstance();


        rv.setLayoutManager(new LinearLayoutManager(ChatsActivity.this));

        chatslist = new ArrayList<>();
        adapter = new ChatsAdapter(ChatsActivity.this, chatslist, firebaseUser.getUid());
        rv.setAdapter(adapter);

        readChats(true);
        //fetchData(true);

    }

    private void readChats(boolean showLoading) {

        if (showLoading) {
            loadingLY.setVisibility(View.VISIBLE);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreDB.collection(Constants.CHAT).whereEqualTo("sender_id", firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                loadingLY.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    chatslist.clear();

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        ChatModel chat = document.toObject(ChatModel.class);
                        chatslist.add(chat);

//                        if (user.getId().equals(firebaseUser.getUid())) {
//                        }
                    }

                    adapter.list = chatslist;
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ChatsActivity.this, getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                }
            }

        });


    }


}