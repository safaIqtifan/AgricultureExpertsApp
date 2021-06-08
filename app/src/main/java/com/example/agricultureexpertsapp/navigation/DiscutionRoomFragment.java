package com.example.agricultureexpertsapp.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.agricultureexpertsapp.R;

public class DiscutionRoomFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        View root = inflater.inflate(R.layout.fragment_discution_room, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        ImageView imageView = root.findViewById(R.id.backbtn);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_discussion);




        return root;

    }
}