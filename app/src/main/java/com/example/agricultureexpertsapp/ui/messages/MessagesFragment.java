package com.example.agricultureexpertsapp.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.R;
import com.google.android.material.appbar.AppBarLayout;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    RecyclerView messageRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        messagesViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_messages);

        messageRecyclerView = root.findViewById(R.id.recyclerView_message);

        return root;
    }
}