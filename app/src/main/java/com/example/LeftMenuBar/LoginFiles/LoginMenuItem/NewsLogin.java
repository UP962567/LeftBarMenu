package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.LoginFiles.User;
import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.backcode.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewsLogin extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    StorageReference sRef;
    DatabaseReference mUserRef;

    DatabaseReference TUserRef;

    DatabaseReference mRef,pRef, lRef;
    ProgressDialog mBar;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Posts, ZzMyViewHolder> adapter;
    FirebaseRecyclerOptions<Posts> options;

    private final int REQUEST_CODE = 101;
    Uri imageUri;

    ConstraintLayout constraintLayout5;

    String profileImageUrlV, userNameV;
    EditText inputAddPost1;
    ImageView addPostImageView1, sendPostImageView1;

    NavigationView navigationView;
    TextView usernameHeader1;
    CircleImageView ProfileImage_Header1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_news_login, container, false);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        pRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        TUserRef = FirebaseDatabase.getInstance().getReference().child("Test");

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        lRef = FirebaseDatabase.getInstance().getReference().child("Liked");
        mBar = new ProgressDialog(getContext());
        sRef = FirebaseStorage.getInstance().getReference().child("PostImages");
        recyclerView=view.findViewById(R.id.recycleViewPost);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        navigationView=view.findViewById(R.id.nav_view_login);
        ProfileImage_Header1=view.findViewById(R.id.LoginTopImage);
        usernameHeader1=view.findViewById(R.id.UserNameShowTopBar);


        System.out.println(User.getLevel());
        int level = User.getLevelID();

        addPostImageView1=view.findViewById(R.id.addPostImageView);
        sendPostImageView1=view.findViewById(R.id.sendPostImageView);
        inputAddPost1=view.findViewById(R.id.inputAddPost);
        constraintLayout5=view.findViewById(R.id.constraintLayout5POSTING);

        if (level == 1) {
            constraintLayout5.setVisibility(View.GONE);
            addPostImageView1.setVisibility(View.INVISIBLE);
            sendPostImageView1.setVisibility(View.INVISIBLE);
            inputAddPost1.setVisibility(View.VISIBLE);
            inputAddPost1.setText("Only Admin Can Post");
            inputAddPost1.setClickable(false);
            inputAddPost1.setCursorVisible(false);
            inputAddPost1.setFocusable(false);
            inputAddPost1.setFocusableInTouchMode(false);

        } else if (level == 2) {
            constraintLayout5.setVisibility(View.VISIBLE);
            addPostImageView1.setVisibility(View.VISIBLE);
            sendPostImageView1.setVisibility(View.VISIBLE);
            inputAddPost1.setVisibility(View.VISIBLE);
            inputAddPost1.setText(null);
            inputAddPost1.setClickable(true);
            inputAddPost1.setCursorVisible(true);
            inputAddPost1.setFocusable(true);
            inputAddPost1.setFocusableInTouchMode(true);
        }

        sendPostImageView1.setOnClickListener(view1 -> AddPost());
        addPostImageView1.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE);
        });

        LoadPosts("");
        return view;
    }


    private void AddPost() {
        String postText= inputAddPost1.getText().toString();
        if(postText.isEmpty()) {
            inputAddPost1.setError("Please Write something!");
        } else if (addPostImageView1 == null) {
            Toast.makeText(getContext(), "please add an image!", Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(getContext(), "please add an image!", Toast.LENGTH_SHORT).show();
        } else {
            mBar.setTitle("Posting it!");
            mBar.setCanceledOnTouchOutside(false);
            mBar.show();

            int i = (int) new Date().getTime();
            int w = i - i - i;

            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String strDate = formatter.format(date);

            sRef.child(mUser.getUid() + " - "+ strDate + " - " + userNameV).putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sRef.child(mUser.getUid() + " - "+strDate + " - " + userNameV).getDownloadUrl().addOnSuccessListener(uri -> {
                        HashMap hashMap = new HashMap();
                        hashMap.put("date", strDate);
                        hashMap.put("imagePost", uri.toString());
                        hashMap.put("postText",postText);
                        hashMap.put("sort",w);
                        hashMap.put("userProfileImage", profileImageUrlV);
                        hashMap.put("usernameID", userNameV);
                        pRef.child(User.getUsername() + " - " + User.getLevel() + " - " +  mUser.getUid() + " - " + strDate).updateChildren(hashMap).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                mBar.dismiss();
                                Toast.makeText(getContext(), "Post Added!", Toast.LENGTH_SHORT).show();
                                addPostImageView1.setImageResource(R.drawable.ic_image);
                                inputAddPost1.setText(null);
                            } else {
                                Toast.makeText(getContext(), "Error Posting!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                } else {

                    mBar.dismiss();
                    Toast.makeText(getContext(), "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void LoadPosts(String s) {
        Query query = mUserRef.orderByChild("sort");
        options= new FirebaseRecyclerOptions.Builder<Posts>().setQuery(query,Posts.class).build();
        adapter= new FirebaseRecyclerAdapter<Posts, ZzMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ZzMyViewHolder holder, int position, @NonNull Posts model) {
                String postKey = getRef(position).getKey();
                holder.postDesc.setText(model.getPostText());
                final String timeAgo = calculateTimeAgo(model.getDate());
                holder._profileTimePosted.setText(timeAgo);
                holder.username.setText("Admin");
                Picasso.get().load(model.getImagePost()).into(holder.postImage);
                //Picasso.get().load(model.getUserProfileImage()).into(holder.profileImage);
                holder.countLikes(postKey,mUser.getUid(), lRef);
                holder.likeImage.setOnClickListener(view -> lRef.child(postKey).child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            lRef.child(postKey).child(mUser.getUid()).removeValue();
                            holder.likeImage.setColorFilter(Color.GRAY);
                            notifyDataSetChanged();
                        } else {
                            lRef.child(postKey).child(mUser.getUid()).setValue("like");
                            holder.likeImage.setColorFilter(Color.GREEN);
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error in like!", Toast.LENGTH_SHORT).show();
                    }
                }));

            }
            @NonNull
            @Override
            public ZzMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_posting, parent, false);
                return new ZzMyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private String calculateTimeAgo(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE && resultCode == RESULT_OK && data!=null) {
            imageUri=data.getData();
            addPostImageView1.setImageURI(imageUri);
        }
    }
}