package com.example.agricultureexpertsapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.Adapter.FarmsAdapter;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.GlobalHelper;
import com.example.agricultureexpertsapp.MoreDetails;
import com.example.agricultureexpertsapp.Profile;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageView profileImg;
    RecyclerView farmsRV;

    FirebaseFirestore fireStoreDB;
    List<FarmModel> farmModelList;
    FarmModel farmModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        profileImg = root.findViewById(R.id.profile_img);
        farmsRV = root.findViewById(R.id.farmsRV);

        farmsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        fireStoreDB = FirebaseFirestore.getInstance();

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Profile.class));

//                startActivityForResult(new Intent(getActivity(), Profile.class), 2311);
            }
        });

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        getFarmsFromFirebase();

        return root;
    }

    private void getFarmsFromFirebase() {

        GlobalHelper.showProgressDialog(getActivity(), getString(R.string.please_wait_loading));
        fireStoreDB.collection(Constants.FB_FARMS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                GlobalHelper.hideProgressDialog();

                if (task.isSuccessful()) {
                    farmModelList = new ArrayList<>();

                    if (task.getResult() != null) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            FarmModel farmModel = queryDocumentSnapshot.toObject(FarmModel.class);
                            farmModel.farm_id = queryDocumentSnapshot.getId();
                            farmModelList.add(farmModel);
                        }

                        initAdapter();
                    }

                } else {
                    Toast.makeText(getActivity(), getString(R.string.fail_get_farms_list), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAdapter() {

        FarmsAdapter adapter = new FarmsAdapter(getActivity(), farmModelList, new DataCallBack() {

            @Override
            public void Result(Object obj, String type, Object otherData) {
                farmModel = (FarmModel) obj;
//                Toast.makeText(getActivity(), farmModel.name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MoreDetails.class);
                intent.putExtra(Constants.KEY_FARM_MODEL, farmModel);
                startActivity(intent);

            }
        });

        farmsRV.setAdapter(adapter);


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