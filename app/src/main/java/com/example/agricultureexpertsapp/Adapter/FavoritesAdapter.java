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
import com.example.agricultureexpertsapp.models.FavoritesModel;
import com.example.agricultureexpertsapp.models.PostsModel;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>{

    public Context context;
    public List<FavoritesModel> list;

    public FavoritesAdapter(Context context, List<FavoritesModel> newsList) {
        this.context = context;
        this.list = newsList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FavoritesViewHolder viewHolder = new FavoritesViewHolder(inflater.inflate(R.layout.item_favorites, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {

        FavoritesModel favoritesModel = list.get(position);

        holder.userName.setText(favoritesModel.userName);
        holder.describction.setText(favoritesModel.description);

        Glide.with(context).asBitmap().load(favoritesModel.photo).placeholder(R.drawable.camera).into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView postImage;
        TextView describction;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            postImage = itemView.findViewById(R.id.post_photo);
            describction = itemView.findViewById(R.id.describctionEd);

        }
    }
}
