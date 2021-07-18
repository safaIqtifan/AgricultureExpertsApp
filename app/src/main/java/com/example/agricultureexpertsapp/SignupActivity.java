package com.example.agricultureexpertsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agricultureexpertsapp.models.UserModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SignupActivity extends BaseActivity {

    EditText name, e_mail, password;
    TextView mLoginBtn;
    Button signupButton;
    ImageView gmailBtn, facebookBtn;
    ProgressBar progressBar;

    FirebaseFirestore fireStoreDB;
    private FirebaseAuth fAuth;
    StorageReference storageRef;

    //GoogleSignInClient signInClient;
    GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        e_mail = findViewById(R.id.e_mailAddress);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupButton);
        mLoginBtn = findViewById(R.id.Logintoaccount);
        gmailBtn = findViewById(R.id.gmailBtn);
        facebookBtn = findViewById(R.id.facebookBtn);
        progressBar = findViewById(R.id.progressBar);

        fireStoreDB = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        CreateRequest();

        gmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = name.getText().toString();
                String email = e_mail.getText().toString();
                String mpassword = password.getText().toString();

                firebaseAuth(fname, email, mpassword);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void checkData(String fname, String email, String mpassword) {

        if (fname.isEmpty()) {
            name.setError("name is Requird");
            return;
        }
        if (email.isEmpty()) {
            e_mail.setError("Email is Requird");
            return;
        }

        if (mpassword.isEmpty()) {
            password.setError("password is Requird");
            return;
        }

        if (mpassword.length() < 6) {
            password.setError("password Must be 6 or more characters");
        }

    }

    private void firebaseAuth(String fname, String email, String mpassword) {

        checkData(fname, email, mpassword);

        fAuth.createUserWithEmailAndPassword(email, mpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                progressBar.setVisibility(View.VISIBLE);

                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                assert firebaseUser != null;
                String userid = firebaseUser.getUid();

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("user_id", userid);
                userMap.put("username", fname);
                userMap.put("imageURL", "default");

                fireStoreDB.collection(Constants.USER).document(userid).set(userMap, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, HomePageActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    finish();

                                } else {
                                    Toast.makeText(SignupActivity.this, "fail_add_user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//        fAuth.createUserWithEmailAndPassword(email, mpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//
//                Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(SignupActivity.this, HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
////                        finish();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = fAuth.getCurrentUser();

        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void CreateRequest() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                    account.getDisplayName();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SignupActivity.this);

                            if (acct != null) {
                                String personName = acct.getDisplayName();
                                String personGivenName = acct.getGivenName();
                                String personId = acct.getId();
                                Uri personPhoto = acct.getPhotoUrl();
//                                uploadPhoto(personPhoto);

                                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                                assert firebaseUser != null;
                                String userid = firebaseUser.getUid();

                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("user_id", userid);
                                userMap.put("username", personName);
                                userMap.put("imageURL", personPhoto.toString());

                                Log.e("acct", "acct is not null ");
                                try {

                                    fireStoreDB.collection(Constants.USER).document(userid).set(userMap, SetOptions.merge())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.e("acct", "onComplete");
                                                    if (task.isSuccessful()) {

                                                        Log.e("acct", "onComplete: - ");
                                                        Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignupActivity.this, HomePageActivity.class)
                                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                        finish();

                                                    } else {
                                                        Toast.makeText(SignupActivity.this, "fail_add_user", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } catch (Exception e) {
                                    Log.e("acct", e.getMessage());
                                }
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}