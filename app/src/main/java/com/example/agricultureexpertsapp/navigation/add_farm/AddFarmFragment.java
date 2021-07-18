package com.example.agricultureexpertsapp.navigation.add_farm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.databinding.FragmentAddFarmBinding;
import com.example.agricultureexpertsapp.models.FarmModel;

public class AddFarmFragment extends Fragment {

//    Button next;
//    EditText farmName, area, location, idNumber, mobileNumber;
//    ImageView farmPhoto;

    Uri farmPhotoUri;
    //    AutoCompleteTextView ownerFarmType;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    private String[] ownerModelList;
    private String[] ownerData;

    String selectedOwner = null;
    ArrayAdapter<String> adapter;
    FragmentAddFarmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentAddFarmBinding.inflate(inflater, container, false);

//        View root = inflater.inflate(R.layout.fragment_add_farm, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.include2.title.setText(R.string.title_create);

//        if (savedInstanceState != null) {
//            return;
//        }

        initListeners();

        initFarmOwnerType();


        binding.farmPhoto.setDrawingCacheEnabled(true);
        binding.farmPhoto.buildDrawingCache();


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    private void initListeners() {

        binding.include2.changebtn.setVisibility(View.VISIBLE);
        binding.include2.changebtn.setText("Next");
        binding.include2.changebtn.setTextColor(Color.parseColor("#1492E6"));
        binding.include2.changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(v);
            }
        });
        binding.ownerFarmType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedOwner = ownerModelList[position - 1];
                } else
                    selectedOwner = null;
            }
        });

        binding.farmPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        });

    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//
//        System.out.println("Log onViewStateRestored");
//    }

    private void initFarmOwnerType() {

        ownerModelList = new String[]{Constants.OWNER_COMPANY, Constants.OWNER_PERSON};
        ownerData = new String[]{getString(R.string.select_farm_owner), getString(R.string.company), getString(R.string.person)};

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ownerData);
        binding.ownerFarmType.setAdapter(adapter);

    }

    private void checkData(View view) {

        String farmNameStr = binding.farmName.getText().toString();
        String areaStr = binding.farmArea.getText().toString();
        String locationStr = binding.farmLocation.getText().toString();
        String mobileStr = binding.mobileNumber.getText().toString();
        String nationalIdStr = binding.idNumber.getText().toString();

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
//        if (selectedOwner == null) {
//            hasError = true;
//        }
        if (farmPhotoUri == null) {
            hasError = true;
        }
        if (hasError) {
            Toast.makeText(getActivity(), getString(R.string.please_fill_data), Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();

        FarmModel farmModel = new FarmModel(farmNameStr, mobileStr, locationStr, Double.parseDouble(areaStr),
                Integer.parseInt(nationalIdStr), selectedOwner);

        bundle.putSerializable(Constants.KEY_FARM_MODEL, farmModel);
        bundle.putString(Constants.KEY_PHOTO_URI, farmPhotoUri.toString());

//        Navigation.findNavController(this).
        Navigation.createNavigateOnClickListener(R.id.action_to_createFarm, bundle).onClick(view);

    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            farmPhotoUri = data.getData();
            binding.farmPhoto.setImageURI(farmPhotoUri);

//            if (binding.ownerFarmType.getAdapter() == null || binding.ownerFarmType.getAdapter().isEmpty())
//                initFarmOwnerType();
        }
    }

}