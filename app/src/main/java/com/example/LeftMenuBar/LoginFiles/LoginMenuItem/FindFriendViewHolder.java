package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {

    CircleImageView circleImageView;
    TextView username, gender;

    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);

        circleImageView = itemView.findViewById(R.id.ProfileImage_FindFriend);
        username = itemView.findViewById(R.id.Username_FindFriend);
        gender = itemView.findViewById(R.id.gender_FindFriend);
    }

}
