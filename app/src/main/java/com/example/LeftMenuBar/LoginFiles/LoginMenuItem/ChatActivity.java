package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.backcode.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerViewCHAT;
    ImageView sendSMS, imageSelect;
    EditText smsText;

    CircleImageView profileImage;
    TextView usernameChat, statusChat;

    String OtherUserID;
    String OtherUserName, OtherUserProfileIMG, OtherUserStatus, OtherUserGender;
    String MyPic;

    String URL="https://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;

    DatabaseReference mRef, smsRef;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    FirebaseRecyclerOptions<Chat> options;
    FirebaseRecyclerAdapter<Chat, ZzChatMyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        OtherUserID = getIntent().getStringExtra("OtherUserID");
        requestQueue = Volley.newRequestQueue(this);

        recyclerViewCHAT=findViewById(R.id.ChatAppRECYCLEVIEW);
        recyclerViewCHAT.setLayoutManager(new LinearLayoutManager(this));

        sendSMS=findViewById(R.id.ChatAppSENDBUTTON);
        imageSelect=findViewById(R.id.ChatAppSELECTIMAGE);
        smsText=findViewById(R.id.ChatAppTEXTEDIT);

        profileImage=findViewById(R.id.ChatAppPROFILEIMAGE);
        usernameChat=findViewById(R.id.ChatAppUSERNAME);
        statusChat=findViewById(R.id.ChatAppStatus);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        smsRef = FirebaseDatabase.getInstance().getReference().child("Message");

        LoadOtherUserID();
        LoadMyProfile();

        sendSMS.setOnClickListener(view -> sendSMS());

        loadSMS();
    }

    private void LoadMyProfile() {
        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MyPic = snapshot.child("profileImage").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSMS() {
        options = new FirebaseRecyclerOptions.Builder<Chat>().setQuery(smsRef.child(mUser.getUid()).child(OtherUserID), Chat.class).build();
        adapter = new FirebaseRecyclerAdapter<Chat, ZzChatMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ZzChatMyViewHolder holder, int position, @NonNull Chat model) {
                if(model.getUserID().equals(mUser.getUid())) {
                    holder.profile1.setVisibility(View.GONE);
                    holder.text1.setVisibility(View.GONE);

                    holder.text2.setVisibility(View.VISIBLE);
                    System.out.println("Your text" + holder.text2.getText().toString());
                    holder.profile2.setVisibility(View.VISIBLE);

                    holder.text2.setText(model.getSms());
                    Picasso.get().load(MyPic).into(holder.profile2);
                } else {
                    holder.profile1.setVisibility(View.VISIBLE);
                    holder.text1.setVisibility(View.VISIBLE);
                    System.out.println("Your text" + holder.text1.getText().toString());
                    holder.text2.setVisibility(View.GONE);
                    holder.profile2.setVisibility(View.GONE);

                    holder.text1.setText(model.getSms());
                    Picasso.get().load(OtherUserProfileIMG).into(holder.profile1);
                }
            }

            @NonNull
            @Override
            public ZzChatMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_sms, parent, false);
                return new ZzChatMyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerViewCHAT.setAdapter(adapter);
    }

    private void sendSMS() {
        String sms = smsText.getText().toString();
        if(sms.isEmpty()){
            Toast.makeText(this, "Enter Something", Toast.LENGTH_SHORT).show();
        }else {
            HashMap hashMap = new HashMap();
            hashMap.put("sms", sms);
            hashMap.put("status", "unseen");
            hashMap.put("userID", mUser.getUid());

            smsRef.child(OtherUserID).child(mUser.getUid()).push().updateChildren(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    smsRef.child(mUser.getUid()).child(OtherUserID).push().updateChildren(hashMap).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            sendNotification(sms);
                            smsText.setText(null);
                            Toast.makeText(getApplicationContext(), "SMS SEND", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }

    private void sendNotification(String sms) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to", "/topics/"+OtherUserID);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", "Message from: " + OtherUserName);
            jsonObject1.put("body",sms);
            jsonObject.put("notification", jsonObject1);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("content-type", "application/json");
                    map.put("authorization", "key=AAAAff5IYrM:APA91bFq19t4Upqm5I5bmcOZMnBNTwO_KtwoKlaFvnYdPsCPJrmjdFPh4ISAKi0eI-rNFudle4xbNnWbTUhNyId0b2ptV1VydX3RLe1dAOKRsEog5iTmSPD8kOMq6MwMs0o5wP2oPUrU");

                    return map;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadOtherUserID() {
        mRef.child(OtherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    OtherUserName = snapshot.child("username").getValue().toString();
                    OtherUserProfileIMG = snapshot.child("profileImage").getValue().toString();
                    OtherUserStatus = snapshot.child("status").getValue().toString();
                    OtherUserGender = snapshot.child("gender").getValue().toString();

                    System.out.println(OtherUserProfileIMG +  "  /  " + OtherUserName +  "  /  " + OtherUserStatus + "  /  " + OtherUserGender);

                    Picasso.get().load(OtherUserProfileIMG).into(profileImage);
                    usernameChat.setText(OtherUserName);
                    System.out.println(usernameChat.getText() + "USERNAME ON TOP");
                    statusChat.setText(OtherUserStatus);
                } else {
                    Toast.makeText(ChatActivity.this, "Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}