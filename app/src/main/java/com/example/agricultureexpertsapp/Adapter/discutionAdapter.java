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
import com.example.agricultureexpertsapp.models.CategoryModel;
import com.example.agricultureexpertsapp.models.PostsModel;

import java.util.ArrayList;
import java.util.List;

public class discutionAdapter extends RecyclerView.Adapter<discutionAdapter.discutionViewHolder>{

    public Context context;
    public List<PostsModel> list;

    public discutionAdapter(Context context, List<PostsModel> newsList) {
        this.context = context;
        this.list = newsList;
    }

    @NonNull
    @Override
    public discutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        discutionViewHolder viewHolder = new discutionViewHolder(inflater.inflate(R.layout.item_image, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull discutionViewHolder holder, int position) {

        PostsModel postsModel = list.get(position);

        holder.describction.setText(postsModel.description);
        Glide.with(context).asBitmap().load(postsModel.photo).placeholder(R.drawable.camera).into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class discutionViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView describction;

        public discutionViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.post_photo);
            describction = itemView.findViewById(R.id.describctionEd);

        }
    }
}
