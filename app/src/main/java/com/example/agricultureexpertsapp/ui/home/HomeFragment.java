package com.example.agricultureexpertsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.agricultureexpertsapp.HomePageActivity;
import com.example.agricultureexpertsapp.Profile;
import com.example.agricultureexpertsapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageView profileImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        profileImg = root.findViewById(R.id.profile_img);

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profile.class));

//                startActivityForResult(new Intent(getActivity(), Profile.class), 2311);
            }
        });

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return root;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.e("TAG", "onActivityResultFragment: " + requestCode);
//        if (requestCode == 2311) {
//            HomePageActivity anActivity = (HomePageActivity) getActivity();
//            if (anActivity != null)
//                if (anActivity.getNavController() != null)
//                    anActivity.getNavController().navigate(R.id.navigation_messages);
//        }
//    }
}