package com.example.agricultureexpertsapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;

import java.util.List;

public class FarmsAdapter extends RecyclerView.Adapter<FarmsAdapter.userSelectedViewHolder> {

    Activity activity;
    List<FarmModel> list;
    DataCallBack dataCallBack;

    public FarmsAdapter(Activity activity, List<FarmModel> categoriesList, DataCallBack callBack) {
        this.activity = activity;
        this.list = categoriesList;
        this.dataCallBack = callBack;
    }

    @NonNull
    @Override
    public userSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        userSelectedViewHolder viewHolder = new userSelectedViewHolder(inflater.inflate(R.layout.item_farm, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull userSelectedViewHolder holder, int position) {

        FarmModel farmModel = list.get(position);

        holder.farmNameTv.setText(farmModel.name);
        holder.mobileNumberTv.setText(farmModel.mobile);
        holder.areaTv.setText(farmModel.area + " " + activity.getString(R.string.meter));

        Glide.with(activity).asBitmap().load(farmModel.photo).placeholder(R.drawable.icon_camera).into(holder.farmImg);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class userSelectedViewHolder extends RecyclerView.ViewHolder {

        ImageView farmImg;
        TextView farmNameTv;
        TextView mobileNumberTv;
        TextView areaTv;

        public userSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            farmImg = itemView.findViewById(R.id.farmImg);
            farmNameTv = itemView.findViewById(R.id.farmNameTv);
            mobileNumberTv = itemView.findViewById(R.id.mobileNumberTv);
            areaTv = itemView.findViewById(R.id.areaTv);

            itemView.setOnClickListener(v -> {

                FarmModel farmModel = list.get(getAdapterPosition());
                dataCallBack.Result(farmModel, "click", "");

            });
        }
    }
}
