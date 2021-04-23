package com.example.agricultureexpertsapp.ui.add_farm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    ArrayList<String> myArray;

    public Adapter(ArrayList<String> myArray){
        this.myArray = myArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View chosenView = inflater.inflate(R.layout.chosen_shap, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(chosenView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       holder.chosenitem.setText(myArray.get(position));
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView chosenitem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chosenitem = itemView.findViewById(R.id.tv);
        }
    }
}
