package com.example.agricultureexpertsapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agricultureexpertsapp.Dialogs.DialogFieldCrops;
import com.example.agricultureexpertsapp.Dialogs.DialogIrrigationSourcess;
import com.example.agricultureexpertsapp.Dialogs.DialogToolsEquipment;
import com.example.agricultureexpertsapp.Dialogs.DialogWorkforce;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.example.agricultureexpertsapp.Adapter.AdapterCategories;
import com.example.agricultureexpertsapp.models.CategoryModel;
import com.example.agricultureexpertsapp.models.IrrigationSourcesModel;
import com.example.agricultureexpertsapp.navigation.add_farm.DataCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MoreDetails extends BaseActivity {

    TextView title;
    ImageView farmImg;
    TextView farmNameTv;
    TextView farmAreaTv;
    TextView farmLocationTv;
    TextView ownerFarmTypeTv;
    TextView mobileNumberTv;
    RecyclerView categoriesRv;
    FarmModel farmModel;
    ArrayList<CategoryModel> selectedCategories;

    FirebaseFirestore fireStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        title = findViewById(R.id.title);
        farmImg = findViewById(R.id.farmImg);
        farmNameTv = findViewById(R.id.farmNameTv);
        farmAreaTv = findViewById(R.id.farmAreaTv);
        farmLocationTv = findViewById(R.id.farmLocationTv);
        ownerFarmTypeTv = findViewById(R.id.ownerFarmTypeTv);
        mobileNumberTv = findViewById(R.id.mobileNumberTv);
        categoriesRv = findViewById(R.id.categoriesRv);

        categoriesRv.setLayoutManager(new GridLayoutManager(getActiviy(), 3, GridLayoutManager.VERTICAL, false));

        fireStoreDB = FirebaseFirestore.getInstance();

        farmModel = (FarmModel) getIntent().getSerializableExtra(Constants.KEY_FARM_MODEL);


        title.setText(farmModel.name);

        getFarmCategoriesFirebase();

    }

    private void initData() {

        farmNameTv.setText(farmModel.name);
        farmAreaTv.setText(farmModel.area + " " + getString(R.string.meter));
        farmLocationTv.setText(farmModel.location);
        mobileNumberTv.setText(farmModel.mobile);

        Glide.with(getActiviy()).asBitmap().load(farmModel.photo).placeholder(R.drawable.icon_camera).into(farmImg);

        String ownerStr = getString(farmModel.owner_type
                .equals(Constants.OWNER_COMPANY) ? R.string.company : R.string.person);
        ownerFarmTypeTv.setText(ownerStr);
        initAdapter();
    }

    private void getFarmCategoriesFirebase() {

        GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait_loading));
        fireStoreDB.collection(Constants.FB_FARMS).document(farmModel.farm_id)
                .collection(Constants.FB_CATEGORIES).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                GlobalHelper.hideProgressDialog();
                if (task.isSuccessful()) {
                    selectedCategories = new ArrayList<>();
                    if (task.getResult() != null) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                            CategoryModel categoryModel = queryDocumentSnapshot.toObject(CategoryModel.class);
                            categoryModel.id = Integer.parseInt(queryDocumentSnapshot.getId());
                            selectedCategories.add(categoryModel);
                        }

                        initData();
                    }

                } else {
                    Toast.makeText(getActiviy(), getString(R.string.fail_get_farm_categories), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initAdapter() {

        AdapterCategories adapter = new AdapterCategories(getActiviy(), selectedCategories, AdapterCategories.CLICK, new DataCallBack() {

            @Override
            public void Result(Object obj, String type, Object otherData) {
                CategoryModel categoryModel = (CategoryModel) obj;

                switch (categoryModel.id) {

                    case Constants.CATEGORY_HOT_HOUSE_PLANET:
//                        new DialogHotHousePlanet(getActiviy(), new DataCallBack() {
//                            @Override
//                            public void Result(Object obj, String type, Object otherData) {
//
//                            }
//                        });

                        break;

                    case Constants.CATEGORY_GREEN_HOUSE:
//                        new DialogCategoryGreenHouse(getActiviy(), new DataCallBack() {
//                            @Override
//                            public void Result(Object obj, String type, Object otherData) {
//
//                            }
//                        });
                        break;

                    case Constants.CATEGORY_IRRIGATION_SOURCES:

                        new DialogIrrigationSourcess(getActiviy(), new DataCallBack() {
                            @Override
                            public void Result(Object obj, String type, Object otherData) {

                                IrrigationSourcesModel irrigationSourcesModel = (IrrigationSourcesModel) obj;
                                addCategoryDetailsToFirebase(String.valueOf(categoryModel.id), Constants.FB_CAT_IRRIGATION, irrigationSourcesModel);
                            }
                        });
                        break;

                    case Constants.CATEGORY_FIELD_CROPS:
                        new DialogFieldCrops(getActiviy(), new DataCallBack() {
                            @Override
                            public void Result(Object obj, String type, Object otherData) {

                            }
                        });
                        break;

                    case Constants.CATEGORY_WORK_FORCE:
                        new DialogWorkforce(getActiviy(), new DataCallBack() {
                            @Override
                            public void Result(Object obj, String type, Object otherData) {

                            }
                        });
                        break;

                    case Constants.CATEGORY_EQUIPMENT:
                        new DialogToolsEquipment(getActiviy(), new DataCallBack() {
                            @Override
                            public void Result(Object obj, String type, Object otherData) {

                            }
                        });
                        break;
                }
            }
        });

        categoriesRv.setAdapter(adapter);
    }

    private void addCategoryDetailsToFirebase(String categoryId, String catType, Object object) {

        GlobalHelper.showProgressDialog(getActiviy(), getString(R.string.please_wait_sending));

        fireStoreDB.collection(Constants.FB_FARMS).document(farmModel.farm_id)
                .collection(Constants.FB_CATEGORIES).document(categoryId).collection(catType).document().set(object)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        GlobalHelper.hideProgressDialog();
                        Toast.makeText(MoreDetails.this, getString(task.isSuccessful() ? R.string.success_add : R.string.fail_add), Toast.LENGTH_SHORT).show();

                    }
                });

    }


}