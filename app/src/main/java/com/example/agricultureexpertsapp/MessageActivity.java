package com.example.agricultureexpertsapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.agricultureexpertsapp.Adapter.MessageAdapter;
import com.example.agricultureexpertsapp.databinding.ActivityMessageBinding;
import com.example.agricultureexpertsapp.models.FmessageModel;
import com.example.agricultureexpertsapp.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    //    RecyclerView rv;
    MessageAdapter adapter;
    UserModel userModel;

    String chatId;
    //    FirebaseUser fuser;
    CollectionReference msgRef;
    DocumentReference chatDoc;
    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB;

    ActivityMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        profileImage = findViewById(R.id.message_imag);
        binding.include.messageImag.setVisibility(View.VISIBLE);
//
//        backBtn = findViewById(R.id.backbtn);
        binding.include.backbtn.setVisibility(View.GONE);
//
//        rv = findViewById(R.id.recyclerView_message);
//
//        loadingLY = findViewById(R.id.loadingLY);
//        userName = findViewById(R.id.title);
//        messagEd = findViewById(R.id.text_send);
//        iconsend = findViewById(R.id.btn_send);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chatId = bundle.getString(Constants.KEY_CHAT_ID);
        }
        userModel = (UserModel) getIntent().getSerializableExtra(Constants.KEY_USER_MODEL);

        fireStoreDB = FirebaseFirestore.getInstance();

//        boolean createChat = false;
        if (chatId == null) {
            chatId = GlobalHelper.getChatId(firebaseUser.getUid(), userModel.user_id);
//            createChat = true;
        }

        System.out.println("Log chatId " + chatId);
        chatDoc = fireStoreDB.collection(Constants.CHAT).document(chatId);
//        if (createChat) {

//        }

        msgRef = chatDoc.collection(Constants.MESSAGES);

        chatDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    error.printStackTrace();
                    return;
                }

                if (value != null && !value.contains("sender_id")) {
                    setChatUsers();
                }

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        binding.rv.setLayoutManager(llm);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();
            }
        });

        initAdapter();

    }

    private void sendMessage() {

        final String msg = GlobalHelper.arabicToDecimal(binding.textSend.getText().toString().trim());

        if (msg.isEmpty()) {
            binding.textSend.setError(getString(R.string.message_empty));
            return;
        }

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("user_id", firebaseUser.getUid());
//        messageMap.put("user_avatar", "");
        messageMap.put("date", FieldValue.serverTimestamp());
        messageMap.put("my_date", new Date());
        messageMap.put("type", "text");
        messageMap.put("content", msg);

        binding.textSend.setText("");

        sendFirebaseMessage(messageMap);

    }

    private void setChatUsers() {
        if (userModel != null) {
            Map<String, Object> chatMap = new HashMap<>();
            chatMap.put("sender_id", firebaseUser.getUid());
            chatMap.put("sender_name", "my name");
            chatMap.put("sender_avatar", "null");
            chatMap.put("friend_id", userModel.user_id);
            chatMap.put("friend_name", userModel.username);
            chatMap.put("friend_avatar", userModel.imageURL);

            chatDoc.set(chatMap, SetOptions.merge())
                    .addOnSuccessListener(documentReference -> {
                        System.out.println("Log success send message");

                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                    });

        }

    }

    private void sendFirebaseMessage(Map<String, Object> messageMap) {

        String msg = (String) messageMap.get("msg");
        String type = (String) messageMap.get("type");

        msgRef.add(messageMap)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Log success send message");

                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    if (type.equals("text"))
                        binding.textSend.setText(msg);
                });

    }

    private void initAdapter() {

        List<FmessageModel> fmessageModelList = new ArrayList<>();

//        chatLoadingLY.setVisibility(View.VISIBLE);
//        rv.setVisibility(View.GONE);
        adapter = new MessageAdapter(this, binding.rv, firebaseUser.getUid(), fmessageModelList, msgRef);
        binding.rv.setAdapter(adapter);

    }

}