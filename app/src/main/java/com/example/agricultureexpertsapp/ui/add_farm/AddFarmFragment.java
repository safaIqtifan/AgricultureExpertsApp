package com.example.agricultureexpertsapp.ui.add_farm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.LoginActivity;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.SignupActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;

public class AddFarmFragment extends Fragment {

    private AddFarmViewModel addFarmViewModel;
    Button next;
    EditText farmName, area, location, idNumber, mobileNumber;
    AutoCompleteTextView ownerFarmType;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static final String[] COUNTRIES = new String[] {"individual", "institution"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        addFarmViewModel =
                new ViewModelProvider(this).get(AddFarmViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_farm, container, false);

        TextView title = root.findViewById(R.id.title);
        title.setText(R.string.title_create);
        next = root.findViewById(R.id.changebtn);
        farmName = root.findViewById(R.id.farmName);
        area = root.findViewById(R.id.farmArea);
        location = root.findViewById(R.id.farmLocation);
        idNumber = root.findViewById(R.id.idNumber);
        mobileNumber = root.findViewById(R.id.mobileNumber);
        ownerFarmType = root.findViewById(R.id.ownerFarmType);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");


        next.setText("Next");
        next.setTextColor(Color.parseColor("#1492E6"));
        next.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_to_createFarm));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView ownerFarmType = root.findViewById(R.id.ownerFarmType);
        ownerFarmType.setAdapter(adapter);


        return root;
    }

}