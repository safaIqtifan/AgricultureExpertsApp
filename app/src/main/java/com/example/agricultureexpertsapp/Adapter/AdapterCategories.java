package com.example.agricultureexpertsapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.CategoryModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.userSelectedViewHolder> {

    Activity activity;
    ArrayList<CategoryModel> list;
    int clickType;
    DataCallBack dataCallBack;

    public static int NO_CLICK = 0,SELECT = 1,CLICK = 2;

    public AdapterCategories(Activity activity, ArrayList<CategoryModel> categoriesList, int clickType, DataCallBack callBack) {
        this.activity = activity;
        this.list = categoriesList;
        this.clickType = clickType;
        this.dataCallBack = callBack;
    }

    @NonNull
    @Override
    public userSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        userSelectedViewHolder viewHolder = new userSelectedViewHolder(inflater.inflate(R.layout.item_user_selected, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull userSelectedViewHolder holder, int position) {

        CategoryModel categoryModel = list.get(position);

        holder.textImage.setText(categoryModel.name);

        Glide.with(activity).load(categoryModel.link).into(holder.categoryImg);

        if (categoryModel.isChecked) {
            holder.categoryBgImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.directory_selected_shap));
        } else {
            holder.categoryBgImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.unselected_shap));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class userSelectedViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImg;
        ImageView categoryBgImg;
        TextView textImage;

        public userSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBgImg = itemView.findViewById(R.id.categoryBgImg);
            categoryImg = itemView.findViewById(R.id.categoryImg);
            textImage = itemView.findViewById(R.id.textImage);

            itemView.setOnClickListener(v -> {

                    CategoryModel categoryModel = list.get(getAdapterPosition());
                    if (clickType == SELECT) {
                    if (categoryModel.isChecked) {
                        categoryModel.isChecked = false;
                        dataCallBack.Result(categoryModel, "remove", "");
                    } else {
                        categoryModel.isChecked = true;
                        dataCallBack.Result(categoryModel, "add", "");
                    }
                        notifyItemChanged(getAdapterPosition());
                    }else if (clickType == CLICK){
                        dataCallBack.Result(categoryModel, "click", "");
                    }

            });
        }
    }
}
