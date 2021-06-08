package com.example.agricultureexpertsapp.navigation.add_farm;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.Adapter.AdapterCategories;
import com.example.agricultureexpertsapp.Adapter.AdapterSelectedCategory;
import com.example.agricultureexpertsapp.Constants;
import com.example.agricultureexpertsapp.GlobalHelper;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.CategoryModel;
import com.example.agricultureexpertsapp.models.FarmModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.UUID;

public class CreateFarmFragment extends Fragment {

    TextView title;
    RecyclerView selectedRV, categoriesRV;
    Button add;
    public ArrayList<CategoryModel> selectedCategories = new ArrayList<>();
    public ArrayList<CategoryModel> categoriesList;

    FirebaseFirestore fireStoreDB;
    FirebaseAuth auth;
    StorageReference storageRef;

    FarmModel farmModel;
    Uri farmPhotoUri;
    String farmId;
    String userId;
    int catIndex = 0;
    ViewGroup viewGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        viewGroup = container;

        View root = inflater.inflate(R.layout.fragment_create_farm2, container, false);

        title = root.findViewById(R.id.title);
        categoriesRV = root.findViewById(R.id.rv);
        selectedRV = root.findViewById(R.id.selectedRV);
        add = root.findViewById(R.id.changebtn);

        auth = FirebaseAuth.getInstance();
        fireStoreDB = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
//        farmsCol = fireStoreDB.collection(Constants.FB_FARMS);

        categoriesList = new ArrayList<>();
        categoriesList.add(new CategoryModel(1, getString(R.string.hothouse_plant), CategoryModel.getLinkByPos(0)));
        categoriesList.add(new CategoryModel(2, getString(R.string.green_house), CategoryModel.getLinkByPos(1)));
        categoriesList.add(new CategoryModel(3, getString(R.string.irrigation_sources), CategoryModel.getLinkByPos(2)));
        categoriesList.add(new CategoryModel(4, getString(R.string.field_crops), CategoryModel.getLinkByPos(3)));
        categoriesList.add(new CategoryModel(5, getString(R.string.workforce), CategoryModel.getLinkByPos(4)));
        categoriesList.add(new CategoryModel(6, getString(R.string.tools_equipment), CategoryModel.getLinkByPos(5)));

        title.setText(R.string.title_create);
        add.setText("ADD");
        add.setTextColor(Color.parseColor("#1492E6"));

        Bundle bundle = getArguments();
        farmModel = (FarmModel) bundle.getSerializable(Constants.KEY_FARM_MODEL);
        farmPhotoUri = Uri.parse(bundle.getString(Constants.KEY_PHOTO_URI));

        initData();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        selectedRV.setLayoutManager(flowLayoutManager);
        selectedRV.setNestedScrollingEnabled(false);
        selectedRV.setVisibility(View.VISIBLE);


        categoriesRV.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        categoriesRV.setNestedScrollingEnabled(false);

        add.setOnClickListener(v -> {

            if (selectedCategories.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.please_select_one_least), Toast.LENGTH_SHORT).show();
                return;
            }

            if (auth == null) {
                Toast.makeText(getActivity(), getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                return;
            }

            userId = auth.getUid();
            farmModel.user_id = userId;

            GlobalHelper.showProgressDialog(getActivity(), getString(R.string.please_wait_sending));
            uploadPhoto(farmPhotoUri);

        });


        return root;
    }

    private void initData() {

        AdapterCategories adapter = new AdapterCategories(getActivity(), categoriesList, AdapterCategories.SELECT, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {
                CategoryModel categoryModel = (CategoryModel) obj;
                if (type.equals("remove")) {
                    selectedCategories.remove(categoryModel);
                } else {
                    selectedCategories.add(categoryModel);
                }
                initSelectedAdapter();
            }
        });
        categoriesRV.setAdapter(adapter);

    }

    private void sendFarmToFirebase() {
        farmId = fireStoreDB.collection(Constants.FB_FARMS).document().getId(); // this is auto genrat

        fireStoreDB.collection(Constants.FB_FARMS).document(farmId).set(farmModel,
                SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    sendCategoriesToFirebase();

                } else {
                    GlobalHelper.hideProgressDialog();
                    Toast.makeText(getActivity(), getString(R.string.fail_add_farm), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendCategoriesToFirebase() {

        if (catIndex < selectedCategories.size()) {
            CategoryModel categoryModel = selectedCategories.get(catIndex);

            fireStoreDB.collection(Constants.FB_FARMS).document(farmId).collection(Constants.FB_CATEGORIES)
                    .document(String.valueOf(categoryModel.id)).set(categoryModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        catIndex++;
                        sendCategoriesToFirebase();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.fail_category), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            GlobalHelper.hideProgressDialog();
            Toast.makeText(getActivity(), getString(R.string.succes_add_farm), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(viewGroup).popBackStack(R.id.navigation_home, false);

        }
    }

    private void initSelectedAdapter() {
        AdapterSelectedCategory adapter = new AdapterSelectedCategory(selectedCategories);
        selectedRV.setAdapter(adapter);
    }

    private void uploadPhoto(Uri photoUri) {
//        Bitmap bitmap = ((BitmapDrawable) farmPhoto.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();

        StorageReference imgRef = storageRef.child(Constants.FS_FARMS_IMAGES + "/" + UUID.randomUUID().toString());

        UploadTask uploadTask = imgRef.putFile(photoUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                GlobalHelper.hideProgressDialog();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(taskSnapshot -> {

            imgRef.getDownloadUrl().addOnCompleteListener(task -> {

                farmModel.photo = task.getResult().toString();
                System.out.println("Log uploaded url " + farmModel.photo);
                sendFarmToFirebase();
            });


        });
    }
}