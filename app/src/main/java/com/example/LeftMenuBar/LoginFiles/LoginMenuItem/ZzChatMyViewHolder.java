package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MaDy on 11/04/2022 / 23:35
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class ZzChatMyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profile1, profile2;
    TextView text1, text2;

    public ZzChatMyViewHolder(@NonNull View itemView) {
        super(itemView);

        profile1 = itemView.findViewById(R.id.FirstUserProfile);
        text1 = itemView.findViewById(R.id.FirstUserText);
        profile2 = itemView.findViewById(R.id.SecondUserProfile);
        text2 = itemView.findViewById(R.id.SecondUserText);

    }
}
