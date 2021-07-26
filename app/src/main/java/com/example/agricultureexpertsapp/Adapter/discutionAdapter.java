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
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.DateHandler;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.PostsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class discutionAdapter extends RecyclerView.Adapter<discutionAdapter.discutionViewHolder>{

    public Context context;
    public List<PostsModel> list;
    private Map<String, PostsModel> favMap;
    FirebaseFirestore fireStoreDB;

    public discutionAdapter(Context context, List<PostsModel> newsList, boolean isFavorite) {
        this.context = context;
        this.list = newsList;

        favMap = new HashMap<>();
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

//        String time = jsonObject.getString("date_gmt");

        String MyFinalValue = DateHandler.covertDateToAgo(postsModel.created_at);

        holder.describction.setText(postsModel.description);
        holder.postDate.setText(MyFinalValue);

        Glide.with(context).asBitmap().load(postsModel.photo).placeholder(R.drawable.camera).into(holder.postImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class discutionViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView describction;
        TextView postDate;
        ImageView favBtn;

        public discutionViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.post_photo);
            describction = itemView.findViewById(R.id.describctionEd);
            postDate = itemView.findViewById(R.id.post_date);
            favBtn = itemView.findViewById(R.id.favBtn);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    PostsModel postsModel = list.get(pos);


                    if (favMap.containsKey(postsModel.post_id)) {
                        fireStoreDB.collection(Constants.POST).document(postsModel.post_id).set(favMap, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
//
//                        // need to delete news from favorite
//                        RootApplication.dbRealm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                FarmModel deleteFarm = favMap.get(farmModel.publishedAt);
//                                if (deleteFarm != null) {
//                                    deleteFarm.deleteFromRealm();
//                                    if (isFavorite) {
//                                        list.remove(pos);
//                                        notifyItemRemoved(pos);
//                                    } else {
//                                        favMap.remove(farmModel.publishedAt);
//                                        notifyItemChanged(getAdapterPosition());
//                                    }
                                        }


                                    }
                                });
//                    } else {
//                        // need to add farm to favorite
//                        RootApplication.dbRealm.beginTransaction();
//                        FarmModel addedModel = RootApplication.dbRealm.copyToRealm(farmModel);
//                        RootApplication.dbRealm.commitTransaction();
//                        favMap.put(farmModel.publishedAt, addedModel);
//                        notifyItemChanged(getAdapterPosition());
//                    }
//                                }
//                            });
//
//

//                    if (favMap.containsKey(postsModel.post_id)) {
//                        // need to delete news from favorite
//                        RootApplication.dbRealm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                FarmModel deleteFarm = favMap.get(farmModel.publishedAt);
//                                if (deleteFarm != null) {
//                                    deleteFarm.deleteFromRealm();
//                                    if (isFavorite) {
//                                        list.remove(pos);
//                                        notifyItemRemoved(pos);
//                                    } else {
//                                        favMap.remove(farmModel.publishedAt);
//                                        notifyItemChanged(getAdapterPosition());
//                                    }
//                                }
//
//
//                            }
//                        });
//                    } else {
//                        // need to add farm to favorite
//                        RootApplication.dbRealm.beginTransaction();
//                        FarmModel addedModel = RootApplication.dbRealm.copyToRealm(farmModel);
//                        RootApplication.dbRealm.commitTransaction();
//                        favMap.put(farmModel.publishedAt, addedModel);
//                        notifyItemChanged(getAdapterPosition());
//                    }

//                }
//            });
//
//
//
//
                    }
                }
            });
        }}
}
