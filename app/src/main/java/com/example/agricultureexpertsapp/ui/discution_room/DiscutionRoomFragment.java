package com.example.agricultureexpertsapp.ui.discution_room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.agricultureexpertsapp.R;

public class DiscutionRoomFragment extends Fragment {

    private DiscutionRoomViewModel discutionRoomViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        discutionRoomViewModel =
                new ViewModelProvider(this).get(DiscutionRoomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discution_room, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        ImageView imageView = root.findViewById(R.id.backbtn);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_discussion);

//        final TextView tv = root.findViewById(R.id.tv);
//        String s = "Discution Room";
//        tv.setText(s);

        discutionRoomViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }
}