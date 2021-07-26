package com.example.agricultureexpertsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.DateHandler;
import com.example.agricultureexpertsapp.MessageActivity;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.databinding.ItemUserBinding;
import com.example.agricultureexpertsapp.models.ChatModel;

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

        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new UserViewHolder(binding);
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

        holder.binding.name.setText(friendName);
        String date = DateHandler.GetDateString(chatModel.created_at,"yyyy-MM-dd hh:mm aa");
        holder.binding.messageDatTv.setText(date);

        Glide.with(context).asBitmap().load(friendAvatar).placeholder(R.drawable.profile).into(holder.binding.messageProfileImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding binding;
//        TextView name;
//        TextView messageBody;
//        ImageView messageImage;

        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
//            name = itemView.findViewById(R.id.name);
//            messageBody = itemView.findViewById(R.id.message_body);
//            messageImage = itemView.findViewById(R.id.message_profile_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatModel chatModel = list.get(getAdapterPosition());

//                Intent intent = new Intent(context, MessageActivity.class);
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra(Constants.KEY_CHAT_ID, chatModel.id);
                    context.startActivity(intent);
                }
            });


        }
    }
}
