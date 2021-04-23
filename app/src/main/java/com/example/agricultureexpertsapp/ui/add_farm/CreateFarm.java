package com.example.agricultureexpertsapp.ui.add_farm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.MoreDetails;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.Util;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;

public class CreateFarm extends Fragment {

    TextView title, greenHouseText,
            hothousePlantText, fieldCropsText,
            irrigationSourcesText,
            toolsEquipmentText, workforceText;

    ImageView greenHouseImg, hothousePlantImg,
            fieldCropsImg, irrigationSourcesImg,
            toolsEquipmentRoomImg, workforceImg;

    Button add;
    Util util;
    private AddFarmViewModel addFarmViewModel;
    RecyclerView recyclerView;
    public ArrayList<String> selectedCategories = new ArrayList<>();
    public ArrayList<String> myArray = new ArrayList<>();
    public ArrayList<String> categoriesIcon = new ArrayList<>();
    Adapter adapter = new Adapter(selectedCategories);
    FirebaseFirestore firestore;

    String urlToLog ="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        addFarmViewModel =
                new ViewModelProvider(this).get(AddFarmViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_farm, container, false);

        title = root.findViewById(R.id.title);
        add = root.findViewById(R.id.changebtn);
        firestore = FirebaseFirestore.getInstance();

        greenHouseText = root.findViewById(R.id.text_greenhouse);
        hothousePlantText = root.findViewById(R.id.text_hothouse_plant);
        fieldCropsText = root.findViewById(R.id.text_field_crops);
        irrigationSourcesText = root.findViewById(R.id.text_irrigation_sources);
        toolsEquipmentText = root.findViewById(R.id.text_tools_equipment);
        workforceText = root.findViewById(R.id.text_workforce);

        hothousePlantImg = root.findViewById(R.id.btn_hothouse_plant);
        greenHouseImg = root.findViewById(R.id.btn_greenhouse);
        irrigationSourcesImg = root.findViewById(R.id.btn_irrigation_sources);
        fieldCropsImg = root.findViewById(R.id.btn_field_crops);
        workforceImg = root.findViewById(R.id.btn_workforce);
        toolsEquipmentRoomImg = root.findViewById(R.id.btn_tools_equipment);

        util = new Util();
        Glide.with(getActivity()).load(util.getLinks().get(0)).into(hothousePlantImg);
        Glide.with(getActivity()).load(util.getLinks().get(1)).into(greenHouseImg);
        Glide.with(getActivity()).load(util.getLinks().get(2)).into(irrigationSourcesImg);
        Glide.with(getActivity()).load(util.getLinks().get(3)).into(fieldCropsImg);
        Glide.with(getActivity()).load(util.getLinks().get(4)).into(workforceImg);
        Glide.with(getActivity()).load(util.getLinks().get(5)).into(toolsEquipmentRoomImg);


        title.setText(R.string.title_create);
        add.setText("ADD");
        add.setTextColor(Color.parseColor("#1492E6"));
        //add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_to_navigation_home));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MoreDetails.class).putExtra("util",util));
            }
        });

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(flowLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        greenHouseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenGreenHouse = greenHouseText.getText().toString();
                changeButton("greenHouse", greenHouseImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenGreenHouse);
                //Write logs here
            }
        });

        hothousePlantImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenHothousePlant = hothousePlantText.getText().toString();
                changeButton("hothousePlant", hothousePlantImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenHothousePlant);
            }
        });

        fieldCropsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenFieldCrops = fieldCropsText.getText().toString();
                changeButton("fieldCrops", fieldCropsImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenFieldCrops);
            }
        });

        irrigationSourcesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenIrrigationSources = irrigationSourcesText.getText().toString();
                changeButton("irrigationSources", irrigationSourcesImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenIrrigationSources);
            }
        });

        toolsEquipmentRoomImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenAgriculturalRoom = toolsEquipmentText.getText().toString();
                changeButton("agricultural_room", toolsEquipmentRoomImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenAgriculturalRoom);
            }
        });

        workforceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenWorkforce = workforceText.getText().toString();
                changeButton("workforce", workforceImg, R.drawable.selected_shap, R.drawable.unselected_shap, chosenWorkforce);
            }
        });

        return root;
    }

    private void changeButton(String type, ImageView img, int selectedIcon, int unSelectedIcon, String shapName) {

        if (!myArray.isEmpty() || selectedCategories.isEmpty()) {
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }

        if (selectedCategories.contains(type)) {
            selectedCategories.remove(type);
            myArray.remove(shapName);
            util.getLinks().get(Integer.parseInt((String) img.getTag())).setChecked(false);
            util.getLinks().get(Integer.parseInt((String) img.getTag())).setName("");
            img.setBackground(ContextCompat.getDrawable(getActivity(), unSelectedIcon));
        } else {
            selectedCategories.add(type);
            myArray.add(shapName);
            util.getLinks().get(Integer.parseInt((String) img.getTag())).setChecked(true);
            util.getLinks().get(Integer.parseInt((String) img.getTag())).setName(shapName);

            img.setBackground(ContextCompat.getDrawable(getActivity(), selectedIcon));
        }

    }

}