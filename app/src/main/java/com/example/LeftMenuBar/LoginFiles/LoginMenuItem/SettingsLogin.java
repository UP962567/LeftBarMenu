package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsLogin extends Fragment {

    static UserLogin U = new UserLogin();

    CircleImageView profileImage;
    EditText inputUsername1, inputFullName1, inputCountry1, inputNumber1, inputAccessLevel;
    Button button;
    RadioGroup radioGrp1;
    RadioButton radioM1 , radioF1;


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_settings_login, container, false);

        profileImage = view.findViewById(R.id.PROFILEprofile_image);
        inputUsername1 = view.findViewById(R.id.PROFILEinputUsername);
        inputUsername1.setOnClickListener(view1 -> showError(inputUsername1, "Not allow to change!"));
        inputFullName1 = view.findViewById(R.id.PROFILEinputFullName);
        inputCountry1 = view.findViewById(R.id.PROFILEinputCountry);
        inputNumber1 = view.findViewById(R.id.PROFILEinputNumber);
        inputAccessLevel = view.findViewById(R.id.PROFILEaccessLevel);
        inputAccessLevel.setOnClickListener(view1 -> showError(inputAccessLevel, "Not allow to change!"));
        button = view.findViewById(R.id.PROFILEbuttonSetUp);
        radioM1 = view.findViewById(R.id.PROFILEradioM);
        radioF1 = view.findViewById(R.id.PROFILEradioF);
        radioGrp1 = view.findViewById(R.id.PROFILEradioGrp);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");



        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String profileImageURL = snapshot.child("profileImage").getValue().toString();
                    String country = snapshot.child("country").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();
                    String fullname = snapshot.child("fullName").getValue().toString();
                    String gender = snapshot.child("gender").getValue().toString();
                    String level = snapshot.child("accessLevel").getValue().toString();

                    Picasso.get().load(profileImageURL).into(profileImage);
                    inputCountry1.setText(country);
                    inputNumber1.setText(phone);
                    inputUsername1.setText(username);
                    inputFullName1.setText(fullname);
                    inputAccessLevel.setText(level);

                    if(gender.equals(2131231164+"") || gender.equals(2131231170+"")){
                        radioM1.setChecked(true);
                        radioF1.setChecked(false);
                    } else if (gender.equals(2131231162+"") || gender.equals(2131231168+"")){
                        radioM1.setChecked(false);
                        radioF1.setChecked(true);
                    }


                } else {
                    Toast.makeText(getContext(), "Data do not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showError(EditText til, String s) {
        til.setError(s);
        til.requestFocus();
    }

}
