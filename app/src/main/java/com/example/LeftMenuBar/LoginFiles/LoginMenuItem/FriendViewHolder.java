package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MaDy on 11/04/2022 / 03:39
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class FriendViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImageURL;
    TextView username, gender;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageURL = itemView.findViewById(R.id.ProfileImage_YourFriend);
        username = itemView.findViewById(R.id.Username_YourFriend);
        gender = itemView.findViewById(R.id.gender_YourFriend);


    }
}
