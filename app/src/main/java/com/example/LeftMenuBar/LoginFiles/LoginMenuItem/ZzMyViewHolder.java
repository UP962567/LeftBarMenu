package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ZzMyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    TextView username,likeCounter,postDesc,_profileTimePosted;
    ImageView postImage, likeImage;


    public ZzMyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage=itemView.findViewById(R.id._profileImagePosted);
        username=itemView.findViewById(R.id._profileNamePosted);
        _profileTimePosted=itemView.findViewById(R.id._profileTimePosted);
        postDesc=itemView.findViewById(R.id._profileDescriptionPost);
        likeCounter=itemView.findViewById(R.id.LikeText);
        postImage=itemView.findViewById(R.id._bodyImagePosted);
        likeImage=itemView.findViewById(R.id._likeButton);
    }

    public void countLikes(String postKey, String uid, DatabaseReference lRef) {
        lRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalLikes= (int) snapshot.getChildrenCount();
                    likeCounter.setText(totalLikes+"");
                } else {
                    likeCounter.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists()) {
                    likeImage.setColorFilter(Color.GREEN);
                } else {
                    likeImage.setColorFilter(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
