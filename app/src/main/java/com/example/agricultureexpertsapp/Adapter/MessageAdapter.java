package com.example.agricultureexpertsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.MessageModels;
import com.example.agricultureexpertsapp.models.PostsModel;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.messageViewHolder>{

    public Context context;
    public List<MessageModels> list;

    public MessageAdapter(Context context, List<MessageModels> newsList) {
        this.context = context;
        this.list = newsList;
    }

    @NonNull
    @Override
    public messageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        messageViewHolder viewHolder = new messageViewHolder(inflater.inflate(R.layout.item_messages, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull messageViewHolder holder, int position) {

        MessageModels messageModels = list.get(position);

        holder.name.setText(messageModels.name);
        holder.messageBody.setText(messageModels.message_body);

        Glide.with(context).asBitmap().load(messageModels.photo).placeholder(R.drawable.camera).into(holder.messageImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class messageViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView messageBody;
        ImageView messageImage;

        public messageViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            messageBody = itemView.findViewById(R.id.message_body);
            messageImage = itemView.findViewById(R.id.message_profile_image);

        }
    }
}
