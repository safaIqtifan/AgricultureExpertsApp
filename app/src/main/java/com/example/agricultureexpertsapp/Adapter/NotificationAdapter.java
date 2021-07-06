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
import com.example.agricultureexpertsapp.models.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{

    public Context context;
    public List<NotificationModel> list;

    public NotificationAdapter(Context context, List<NotificationModel> newsList) {
        this.context = context;
        this.list = newsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NotificationViewHolder viewHolder = new NotificationViewHolder(inflater.inflate(R.layout.item_notification, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        NotificationModel notificationModel = list.get(position);

        holder.title.setText(notificationModel.title);
        holder.notification_body.setText(notificationModel.notification_body);

        Glide.with(context).asBitmap().load(notificationModel.photo).placeholder(R.drawable.directory_user).into(holder.notificationImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView notification_body;
        ImageView notificationImage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            notification_body = itemView.findViewById(R.id.notification_body);
            notificationImage = itemView.findViewById(R.id.notification_image);

        }
    }
}
