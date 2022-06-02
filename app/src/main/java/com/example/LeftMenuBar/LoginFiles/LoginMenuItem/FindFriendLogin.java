package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.backcode.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class FindFriendLogin extends Fragment {

    FirebaseRecyclerOptions<Users> options;
    FirebaseRecyclerAdapter<Users, ZzFindFriendViewHolder> adapter;

    DatabaseReference mUserRef, mFriendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView;

    String userID;

    ConstraintLayout constraintLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_find_friend_login, container, false);

        constraintLayout = view.findViewById(R.id.ConstrainLayout);

        recyclerView = view.findViewById(R.id.recycleViewFindFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mFriendRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        LoadUsers("");

        return view;
    }

    // !mFriendRef.child(mUser.getUid()).orderByChild("username").equals(mUserRef.orderByChild("username"))
    // !mUserRef.getKey().equals(mFriendRef.child(mUser.getUid()).getKey())
    // !query.equals(mFriendRef.child(mUser.getUid()).getKey())

    private void LoadUsers(String s) {
        Query query = mUserRef.orderByChild("username").startAt("").endAt("" +"\uf8ff");
        Query query1 = mFriendRef.child(mAuth.getUid()).orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        
        options= new FirebaseRecyclerOptions.Builder<Users>().setQuery(query, Users.class).build();
        adapter= new FirebaseRecyclerAdapter<Users, ZzFindFriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ZzFindFriendViewHolder holder, int position, @NonNull Users model) {
                if (!mUser.getUid().equals(getRef(position).getKey())
                        && !query.equals(query1) ) {
                    Picasso.get().load(model.getProfileImage()).into(holder.circleImageView);
                    holder.username.setText(model.getUsername());
                    //holder.gender.setText("Male");

                } else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }
                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), FindFriendPorfileLogin.class);
                    intent.putExtra("userKey", getRef(position).getKey());
                    startActivity(intent);
                });
            }

            @NonNull
            @Override
            public ZzFindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_find_friends, parent, false);
                return new ZzFindFriendViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
