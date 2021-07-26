package com.example.agricultureexpertsapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.agricultureexpertsapp.Adapter.FarmsAdapter;
import com.example.agricultureexpertsapp.ChatsActivity;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.MoreDetails;
import com.example.agricultureexpertsapp.NotificationDisplay;
import com.example.agricultureexpertsapp.ProfileActivity;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.UserActivity;
import com.example.agricultureexpertsapp.databinding.FragmentHomeBinding;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

//    ProgressBar loadingLY;
//    ImageView profileImg;
//    RecyclerView farmsRV;
//    SwipeRefreshLayout swipeRefreshLY;
//    ImageView notificationImg;
//    ImageView messages;

    FirebaseFirestore fireStoreDB;
    List<FarmModel> farmModelList;
    FarmModel farmModel;
    FarmsAdapter adapter;

    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

//        loadingLY = root.findViewById(R.id.loadingLY);
//        profileImg = root.findViewById(R.id.profile_img);
//        farmsRV = root.findViewById(R.id.farmsRV);
//        swipeRefreshLY = root.findViewById(R.id.swipeToRefreshLY);
//        notificationImg = root.findViewById(R.id.notification_img);
//        messages = root.findViewById(R.id.messages_img);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.farmsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        fireStoreDB = FirebaseFirestore.getInstance();

        binding.include3.notificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationDisplay.class));

            }
        });

        binding.include3.messagesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ChatsActivity.class));

            }
        });

        binding.searchLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserActivity.class));

            }
        });

        binding.include3.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(),ProfileActivity.class);

                startActivity(new Intent(getActivity(), ProfileActivity.class));

//                startActivityForResult(new Intent(getActivity(), Profile.class), 2311);
            }
        });

        binding.swipeToRefreshLY.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFarmsFromFirebase(false);
            }
        });

        getFarmsFromFirebase(true);
    }

    private void getFarmsFromFirebase(boolean showLoading) {

        if (showLoading) {
            binding.loadingLY.setVisibility(View.VISIBLE);
            binding.swipeToRefreshLY.setVisibility(View.GONE);
        }

//        GlobalHelper.showProgressDialog(getActivity(), getString(R.string.please_wait_loading));
        fireStoreDB.collection(Constants.FB_FARMS).orderBy("created_at", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                binding.loadingLY.setVisibility(View.GONE);
                binding.swipeToRefreshLY.setRefreshing(false);

                if (task.isSuccessful()) {

                    binding.swipeToRefreshLY.setVisibility(View.VISIBLE);
                    farmModelList = new ArrayList<>();

                    if (task.getResult() != null) {

                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            FarmModel farmModel = queryDocumentSnapshot.toObject(FarmModel.class);
                            farmModel.farm_id = queryDocumentSnapshot.getId();
                            farmModelList.add(farmModel);

//                            adapter.getLocalFavorite();
//                            adapter.notifyDataSetChanged();
                        }

                        // you can store farm to local database


                        initAdapter();
                    }

                } else {
                    Toast.makeText(getActivity(), getString(R.string.fail_get_farms_list), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAdapter() {

        adapter = new FarmsAdapter(getActivity(), farmModelList, new DataCallBack() {

            @Override
            public void Result(Object obj, String type, Object otherData) {
                farmModel = (FarmModel) obj;
//                Toast.makeText(getActivity(), farmModel.name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MoreDetails.class);
                intent.putExtra(Constants.KEY_FARM_MODEL, farmModel);
                startActivity(intent);

            }
        });

        binding.farmsRV.setAdapter(adapter);
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