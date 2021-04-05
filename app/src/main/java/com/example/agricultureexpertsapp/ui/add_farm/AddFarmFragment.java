package com.example.agricultureexpertsapp.ui.add_farm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.agricultureexpertsapp.R;

public class AddFarmFragment extends Fragment {

    private AddFarmViewModel addFarmViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        addFarmViewModel =
                new ViewModelProvider(this).get(AddFarmViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_farm, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_create);

        addFarmViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}