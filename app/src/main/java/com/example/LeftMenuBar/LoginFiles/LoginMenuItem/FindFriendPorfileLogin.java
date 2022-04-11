package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LeftMenuBar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendPorfileLogin extends AppCompatActivity {

    DatabaseReference mRef, requestRef, friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String profileImageUrl, username, gender;
    Button buttonP, buttonC;

    String CurrentState="nothing_happened";



    CircleImageView profileImage1;
    TextView username1, username2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_porfile_login);


        String userID = getIntent().getExtras().getString("userKey");
        //Toast.makeText(FindFriendPorfileLogin.this, ""+userID, Toast.LENGTH_SHORT).show();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        requestRef = FirebaseDatabase.getInstance().getReference().child("Request");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        profileImage1=findViewById(R.id.profileImagePROFILEFIND);
        username1=findViewById(R.id.usernamePROFILEFIND);
        username2=findViewById(R.id.usernamePROFILEFIND2);
        buttonC=findViewById(R.id.buttonDecline);
        buttonP=findViewById(R.id.buttonPerform);

        LoadUser();

        buttonP.setOnClickListener(view -> PerformAction(userID));
        buttonC.setOnClickListener(view -> Unfried(userID));

        CheckUserExistance(userID);
    }

    private void Unfried(String userID){
        if (CurrentState.equals("friend")){
            friendRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        friendRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "UNFRIENDED !?", Toast.LENGTH_SHORT).show();
                                    CurrentState="nothing_happened";
                                    buttonP.setText("Send Friend");
                                    buttonC.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            });
        } if (CurrentState.equals("he_send_pending")){
            HashMap hashMap = new HashMap();
            hashMap.put("status", "decline");
            requestRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Declined !", Toast.LENGTH_SHORT).show();
                        CurrentState="he_send_decline";
                        buttonP.setVisibility(View.GONE);
                        buttonC.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void CheckUserExistance(String userID){
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState = "friend";
                    buttonP.setText("Send SMS");
                    buttonC.setText("Unfriend");
                    buttonC.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        friendRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState = "friend";
                    buttonP.setText("Send SMS");
                    buttonC.setText("Unfriend");
                    buttonC.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if(snapshot.child("status").getValue().toString().equals("pending")) {
                        CurrentState ="I_sent_pending";
                        buttonP.setText("Cancel Request");
                        buttonC.setVisibility(View.GONE);
                    }
                    if(snapshot.child("status").getValue().toString().equals("decline")) {
                        CurrentState ="I_sent_decline";
                        buttonP.setText("Cancel Request");
                        buttonC.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        requestRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    if(snapshot.child("status").getValue().equals("pending")){
                        CurrentState = "he_send_pending";
                        buttonP.setText("Accept Friend");
                        buttonC.setText("Decline Friend");
                        buttonC.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        if (CurrentState.equals("nothing_happened")){
            CurrentState = "nothing_happened";
            buttonP.setText("Send Friend");
            buttonC.setVisibility(View.GONE);
        }
    }

    private void PerformAction(String userID) {
        if(CurrentState.equals("nothing_happened")) {
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");
            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Request Send", Toast.LENGTH_SHORT).show();
                    buttonC.setVisibility(View.GONE);
                    CurrentState="I_sent_pending";
                    buttonP.setText("Cancel Request");
                } else {
                    Toast.makeText(getApplicationContext(), ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } if (CurrentState.equals("I_sent_pending") || CurrentState.equals("I_sent_decline")){
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "You cancelled Request", Toast.LENGTH_SHORT).show();
                    CurrentState = "nothing_happened";
                    buttonP.setText("Send Request");
                    buttonC.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } if (CurrentState.equals("he_send_pending")) {
            requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("status", "friend");
                        hashMap.put("username", username);
                        hashMap.put("profileImageURL", profileImageUrl);
                        hashMap.put("gender", gender);
                        friendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(
                                new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            friendRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    Toast.makeText(getApplicationContext(), "You Added Friend", Toast.LENGTH_SHORT).show();
                                                    CurrentState = "friend";
                                                    buttonP.setText("Send SMS");
                                                    buttonC.setText("Unfriend");
                                                    buttonC.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    }
                                }
                        );
                    }
                }
            });
        } if (CurrentState.equals("friend")){
            //
        }
    }

    private void LoadUser() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    profileImageUrl=snapshot.child("profileImage").getValue().toString();
                    username=snapshot.child("username").getValue().toString();
                    gender=snapshot.child("gender").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImage1);
                    username1.setText(username);

                    if(gender.equals(2131231137+"")){
                        username2.setText("Male");
                    } else if (gender.equals(2131231135+"")){
                        username2.setText("Female");
                    }
                } else {
                    Toast.makeText(FindFriendPorfileLogin.this, "Data not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindFriendPorfileLogin.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}