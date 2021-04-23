package com.example.agricultureexpertsapp.ui.messages;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.R;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.messageViewHolder>{


    @NonNull
    @Override
    public messageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull messageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class messageViewHolder extends RecyclerView.ViewHolder {

        ImageView messageImage;
        TextView name;
        TextView messageBody;

        public messageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageImage = itemView.findViewById(R.id.message_profile_image);
            name = itemView.findViewById(R.id.name);
            messageBody = itemView.findViewById(R.id.messageBody);

        }
    }
}
