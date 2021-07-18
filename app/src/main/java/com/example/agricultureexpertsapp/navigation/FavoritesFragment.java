package com.example.agricultureexpertsapp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.agricultureexpertsapp.Adapter.FarmsAdapter;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.RootApplication;
import com.example.agricultureexpertsapp.models.FarmModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    SwipeRefreshLayout swipeToRefreshLY;
    RecyclerView rv;
    FarmsAdapter adapter;
    List<FarmModel> favoritesModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_messages);

        swipeToRefreshLY = root.findViewById(R.id.swipeToRefreshLY);
        rv = root.findViewById(R.id.favoritesRV);

        favoritesModelList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        swipeToRefreshLY.setEnabled(false);
        favoritesModelList = RootApplication.dbRealm.where(FarmModel.class).findAll();

        adapter = new FarmsAdapter(getActivity(), favoritesModelList,true);
        rv.setAdapter(adapter);

        return root;
    }
}