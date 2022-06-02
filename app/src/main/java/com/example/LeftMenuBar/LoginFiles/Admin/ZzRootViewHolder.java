package com.example.LeftMenuBar.LoginFiles.Admin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.R;

public class ZzRootViewHolder extends RecyclerView.ViewHolder {

    TextView rootOutText,rootOutText1, rootOutText2;

    public ZzRootViewHolder(@NonNull View itemView) {
        super(itemView);
        rootOutText = itemView.findViewById(R.id.rootOutPut123);
        rootOutText1 = itemView.findViewById(R.id.rootOutPut1234);
        rootOutText2 = itemView.findViewById(R.id.rootTime);
    }
}
