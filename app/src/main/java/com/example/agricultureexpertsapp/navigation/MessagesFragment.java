package com.example.agricultureexpertsapp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.R;

public class MessagesFragment extends Fragment {

    RecyclerView messageRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_messages);

        messageRecyclerView = root.findViewById(R.id.recyclerView_message);

        return root;
    }
}