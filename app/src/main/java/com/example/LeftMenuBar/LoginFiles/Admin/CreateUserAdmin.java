package com.example.LeftMenuBar.LoginFiles.Admin;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateUserAdmin extends AppCompatActivity {

    CircleImageView profileImage;
    EditText inputEmail, inputPassword, LevelID;
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
        setContentView(R.layout.activity_create_user_admin);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

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
        LevelID = findViewById(R.id.inputLevel);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE);
        });
        button.setOnClickListener(view -> SaveData());
    }


    private void SaveData() {
        String pass = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();

        String userName=inputUsername1.getText().toString();
        String fullName=inputFullName1.getText().toString();
        String country=inputCountry1.getText().toString();
        String number=inputNumber1.getText().toString();
        int selectedId = radioGrp1.getCheckedRadioButtonId();
        String access = LevelID.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError1(inputEmail, "Email is not valid!");
        }  else if (pass.isEmpty() || pass.length()<5) {
            showError1(inputPassword, "Password mast be more then 5 characters");
        } else if (userName.isEmpty() || userName.length() < 3) {
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
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mLoadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();

                    mLoadingBar.setTitle("Adding information!");
                    mLoadingBar.setMessage("We are uploading your information to our database");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();

                    mAuth = FirebaseAuth.getInstance();
                    mUser = mAuth.getCurrentUser();
                    mRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    storageRef = FirebaseStorage.getInstance().getReference().child("ProfileImage");

                    storageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            storageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(uri -> {
                                HashMap hashMap = new HashMap<>();
                                hashMap.put("username", userName);
                                hashMap.put("fullName", fullName);
                                hashMap.put("country", country);
                                hashMap.put("phone", number);
                                hashMap.put("gender", selectedId);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");
                                if (access.equals("2")) {
                                    hashMap.put("accessLevel","admin");
                                } else if (access.equals("1")) {
                                    hashMap.put("accessLevel","member");
                                }
                                hashMap.put("ID_ACC_LEVEL",access);
                                mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(o -> {
                                    mLoadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), "Setup Successful", Toast.LENGTH_SHORT);
                                }).addOnFailureListener(e -> {
                                    mLoadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                                });
                            });
                        }
                    });




                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration is Failed", Toast.LENGTH_SHORT).show();
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

    private void showError1(EditText til, String s) {
        til.setError(s);
        til.requestFocus();
    }

}