package com.example.agricultureexpertsapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.agricultureexpertsapp.Adapter.discutionAdapter;
import com.example.agricultureexpertsapp.AddPostActivity;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.PostsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiscutionRoomFragment extends Fragment {

    ProgressBar loadingLY;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLY;
    TextView askMe;
    List<PostsModel> postModelsList;
    discutionAdapter adapter;

    FirebaseFirestore fireStoreDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_discution_room, container, false);
        ImageView imageView = root.findViewById(R.id.backbtn);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_discussion);

        loadingLY = root.findViewById(R.id.loadingLY);
        swipeRefreshLY = root.findViewById(R.id.swipeToRefreshLY);
        rv = root.findViewById(R.id.describctionRV);
        askMe = root.findViewById(R.id.ask_me);

        fireStoreDB = FirebaseFirestore.getInstance();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        postModelsList = new ArrayList<>();

        adapter = new discutionAdapter(getActivity(), postModelsList, false);
        rv.setAdapter(adapter);

        swipeRefreshLY.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(false);
            }
        });

        askMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddPostActivity.class));

            }
        });

        fetchData(true);

        return root;

    }

    public void fetchData(boolean showLoading) {

        if (showLoading) {
            loadingLY.setVisibility(View.VISIBLE);
            swipeRefreshLY.setVisibility(View.GONE);
        }

        fireStoreDB.collection(Constants.POST).orderBy("created_at", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                loadingLY.setVisibility(View.GONE);
                swipeRefreshLY.setRefreshing(false);

                if (task.isSuccessful()) {

                    swipeRefreshLY.setVisibility(View.VISIBLE);
                    postModelsList.clear();

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        PostsModel postModel = document.toObject(PostsModel.class);
                        postModelsList.add(postModel);
                    }
                    adapter.list = postModelsList;
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}