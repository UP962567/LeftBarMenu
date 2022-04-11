package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.LoginFiles.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupLogin extends AppCompatActivity {

    CircleImageView profileImage;
    EditText inputUsername1, inputFullName1, inputCountry1, inputNumber1;
    CheckBox checkBox;
    Button button;
    RadioGroup radioGrp1;
    RadioButton radioM1 , radioF1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference storageRef;
    ProgressDialog mLoadingBar;

    Uri imageUri;
    private final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_login);


        profileImage = findViewById(R.id.profile_image);
        inputUsername1 = findViewById(R.id.inputUsername);
        inputFullName1 = findViewById(R.id.inputFullName);
        inputCountry1 = findViewById(R.id.inputCountry);
        inputNumber1 = findViewById(R.id.inputNumber);
        checkBox = findViewById(R.id.checkBox);
        button = findViewById(R.id.buttonSetUp);
        radioM1 = findViewById(R.id.radioM);
        radioF1 = findViewById(R.id.radioF);
        radioGrp1 = findViewById(R.id.radioGrp);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        storageRef = FirebaseStorage.getInstance().getReference().child("ProfileImage");
        mLoadingBar = new ProgressDialog(this);

        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE);
        });
        button.setOnClickListener(view -> SaveData());
    }


    private void SaveData() {
        String userName=inputUsername1.getText().toString();
        String fullName=inputFullName1.getText().toString();
        String country=inputCountry1.getText().toString();
        String number=inputNumber1.getText().toString();
        int selectedId = radioGrp1.getCheckedRadioButtonId();
        int levelAccessId = 1;

        if (userName.isEmpty() || userName.length() < 3) {
            showError(inputUsername1, "Enter valid Username! Min length three characters!");
        } else if (fullName.isEmpty() || fullName.length() < 3) {
            showError(inputFullName1, "Enter valid FullName! Min length three characters!");
        } else if (country.isEmpty() || country.length() < 3) {
            showError(inputCountry1, "Enter valid Country! Min length three characters!");
        } else if (number.isEmpty() || number.length() < 3) {
            showError(inputNumber1, "Enter valid Number! Min length three characters!");
        } else if (imageUri == null) {
            Toast.makeText(this, "Enter image!", Toast.LENGTH_SHORT).show();
        } else {
            mLoadingBar.setTitle("Adding information!");
            mLoadingBar.setMessage("We are uploading your information to our database");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            storageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(uri -> {
                        HashMap hashMap = new HashMap<>();
                        hashMap.put("username", userName);
                        hashMap.put("fullName", fullName);
                        hashMap.put("country", country);
                        hashMap.put("phone", number);
                        hashMap.put("gender", String.valueOf(selectedId));
                        hashMap.put("profileImage", uri.toString());
                        hashMap.put("status", "offline");
                        hashMap.put("accessLevel","member");
                        hashMap.put("ID_ACC_LEVEL",levelAccessId);
                        mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(o -> {
                            Intent intent = new Intent(SetupLogin.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            mLoadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Setup Successful", Toast.LENGTH_SHORT);
                        }).addOnFailureListener(e -> {
                            mLoadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                        });
                    });
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE && resultCode == RESULT_OK && data!=null) {
            imageUri=data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    private void showError(EditText til, String s) {
        til.setError(s);
        til.requestFocus();
    }
}