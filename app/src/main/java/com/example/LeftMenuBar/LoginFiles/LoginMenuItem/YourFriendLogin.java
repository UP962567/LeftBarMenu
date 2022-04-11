package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.backcode.Friend;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class YourFriendLogin extends androidx.fragment.app.Fragment {

    FirebaseRecyclerOptions<Friend> options;
    FirebaseRecyclerAdapter<Friend, FriendViewHolder> adapter;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_your_friend_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        recyclerView= view.findViewById(R.id.YourFriendRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LoadFriends("");

        return view;
    }

    private void LoadFriends(String s) {
        Query query = mRef.child(mAuth.getUid()).orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        options= new FirebaseRecyclerOptions.Builder<Friend>().setQuery(query,Friend.class).build();
        adapter= new FirebaseRecyclerAdapter<Friend, FriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendViewHolder holder, int position, @NonNull Friend model) {
                Picasso.get().load(model.getProfileImageURL()).into(holder.profileImageURL);
                holder.username.setText(model.getUsername());
                if(model.getGender().equals(2131231137+"")){
                    holder.gender.setText("Male");
                } else if (model.getGender().equals(2131231135+"")){
                    holder.gender.setText("Female");
                }
            }

            @NonNull
            @Override
            public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_your_friend, parent, false);
                return new FriendViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}