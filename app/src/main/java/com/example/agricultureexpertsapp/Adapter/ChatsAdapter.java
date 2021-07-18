package com.example.agricultureexpertsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.ProfileActivity;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.ChatModel;
import com.example.agricultureexpertsapp.models.UserModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.UserViewHolder> {

    public Context context;
    public List<ChatModel> list;

    String myUserId;

    public ChatsAdapter(Context context, List<ChatModel> newsList, String myuserId) {
        this.context = context;
        this.list = newsList;
        this.myUserId = myuserId;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserViewHolder viewHolder = new UserViewHolder(inflater.inflate(R.layout.item_user, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        ChatModel chatModel = list.get(position);

        String friendName, friendAvatar = "";
        if (myUserId.equals(chatModel.sender_id)) {
            friendName = chatModel.friend_name;
            friendAvatar = chatModel.friend_avatar;
        } else {
            friendName = chatModel.sender_name;
            friendAvatar = chatModel.sender_avatar;
        }
        holder.name.setText(friendName);

//        Glide.with(context).asBitmap().load(userModel.imageURL).placeholder(R.drawable.profile).into(holder.messageImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView messageBody;
        ImageView messageImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            messageBody = itemView.findViewById(R.id.message_body);
            messageImage = itemView.findViewById(R.id.message_profile_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatModel chatModel = list.get(getAdapterPosition());

//                Intent intent = new Intent(context, MessageActivity.class);
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(Constants.KEY_CHAT_ID, chatModel.id);
                    context.startActivity(intent);
                }
            });


        }
    }
}
