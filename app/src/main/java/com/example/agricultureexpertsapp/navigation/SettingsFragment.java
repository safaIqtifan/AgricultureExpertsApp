package com.example.agricultureexpertsapp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.agricultureexpertsapp.R;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_messages);



        return root;
    }
}