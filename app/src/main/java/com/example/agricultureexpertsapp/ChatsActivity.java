package com.example.agricultureexpertsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.agricultureexpertsapp.Adapter.ChatsAdapter;
import com.example.agricultureexpertsapp.databinding.ActivityUserBinding;
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

public class ChatsActivity extends AppCompatActivity {

//    CircleImageView profileImage;
//    TextView userNme;
//    ProgressBar loadingLY;
//    RecyclerView rv;

    ChatsAdapter adapter;
    List<ChatModel> chatslist;

    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB;

    boolean getMyChats;
    boolean getOtherChats;

    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        profileImage = findViewById(R.id.message_imag);
//        userNme = findViewById(R.id.title);
//        loadingLY = findViewById(R.id.loadingLY);
//        rv = findViewById(R.id.recyclerView_message);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreDB = FirebaseFirestore.getInstance();

        binding.rv.setLayoutManager(new LinearLayoutManager(ChatsActivity.this));

        chatslist = new ArrayList<>();
        adapter = new ChatsAdapter(ChatsActivity.this, chatslist, firebaseUser.getUid());
        binding.rv.setAdapter(adapter);

        readChats(true);
        //fetchData(true);

    }

    private void readChats(boolean showLoading) {

        if (showLoading) {
            binding.loadingLY.setVisibility(View.VISIBLE);
        }

        chatslist.clear();
        getMyChats = false;
        getOtherChats = false;

        readMyChats();
        readOtherChats();


    }

    private void readMyChats() {

        fireStoreDB.collection(Constants.CHAT).whereEqualTo("sender_id", firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        ChatModel chat = document.toObject(ChatModel.class);
                        chat.id = document.getId();
                        chatslist.add(chat);
                    }
                    getMyChats = true;

                    if (getOtherChats) {
                        binding.loadingLY.setVisibility(View.GONE);
                        initAdapter();
                    }

                } else {
                    Toast.makeText(ChatsActivity.this, getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void readOtherChats() {

        fireStoreDB.collection(Constants.CHAT).whereEqualTo("friend_id", firebaseUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        ChatModel chat = document.toObject(ChatModel.class);
                        chat.id = document.getId();
                        chatslist.add(chat);
                    }
                    getOtherChats = true;

                    if (getMyChats) {
                        binding.loadingLY.setVisibility(View.GONE);
                        initAdapter();
                    }

                } else {
                    Toast.makeText(ChatsActivity.this, getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void initAdapter() {

        adapter.list = chatslist;
        adapter.notifyDataSetChanged();


    }


}