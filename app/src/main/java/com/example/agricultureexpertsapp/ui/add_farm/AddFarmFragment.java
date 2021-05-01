package com.example.agricultureexpertsapp.ui.add_farm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFarmFragment extends Fragment {

    private AddFarmViewModel addFarmViewModel;
    Button next;
    EditText farmName, area, location, idNumber, mobileNumber;
    AutoCompleteTextView ownerFarmType;


    private String[] ownerModelList;
    private String[] ownerData;

    String selectedOwner = null;

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

        ownerModelList = new String[]{Constants.OWNER_COMPANY, Constants.OWNER_PERSON};
        ownerData = new String[]{getString(R.string.select_farm_owner), getString(R.string.company), getString(R.string.person)};

        next.setText("Next");
        next.setTextColor(Color.parseColor("#1492E6"));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(v);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, ownerData);
        AutoCompleteTextView ownerFarmType = root.findViewById(R.id.ownerFarmType);
        ownerFarmType.setAdapter(adapter);

        ownerFarmType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedOwner = ownerModelList[position - 1];
                } else
                    selectedOwner = null;
            }
        });

        return root;
    }

    private void checkData(View view) {

        String farmNameStr = farmName.getText().toString();
        String areaStr = area.getText().toString();
        String locationStr = location.getText().toString();
        String mobileStr = mobileNumber.getText().toString();
        String nationalIdStr = idNumber.getText().toString();

        boolean hasError = false;
        if (farmNameStr.isEmpty()) {
            hasError = true;
        }
        if (areaStr.isEmpty()) {
            hasError = true;
        }
        if (locationStr.isEmpty()) {
            hasError = true;
        }
        if (mobileStr.isEmpty()) {
            hasError = true;
        }
        if (nationalIdStr.isEmpty()) {
            hasError = true;
        }
        if (selectedOwner == null) {
            hasError = true;
        }
        if (hasError) {
            Toast.makeText(getActivity(), getString(R.string.please_fill_data), Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();

        FarmModel farmModel = new FarmModel(farmNameStr, mobileStr, locationStr, areaStr, nationalIdStr, selectedOwner);
        bundle.putSerializable(Constants.KEY_FARM_MODEL, farmModel);

//        Navigation.findNavController(this).
        Navigation.createNavigateOnClickListener(R.id.action_to_createFarm, bundle).onClick(view);

    }
}