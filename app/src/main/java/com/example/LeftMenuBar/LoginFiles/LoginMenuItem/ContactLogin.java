package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.LeftMenuBar.Utils.UserLogin;
import com.example.LeftMenuBar.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactLogin extends Fragment implements View.OnClickListener {

    TextView page2;
    EditText about2, message2, email2;
    Button sendButton2;
    UserLogin U = new UserLogin();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.activity_contact_login, container, false);



        page2 = (TextView) view.findViewById(R.id.pageContact_login);
        email2 = (EditText) view.findViewById(R.id.emailContact_login);
        email2.setText(U.getLoginUsername());
        about2 = (EditText) view.findViewById(R.id.aboutContact_login);
        message2 = (EditText) view.findViewById(R.id.messageContact_login);

        sendButton2 = (Button) view.findViewById(R.id.sendButtonContact_login);
        sendButton2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendButtonContact_login:
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), (CharSequence) e, Toast.LENGTH_SHORT).show();
                } try {
                String email1 = email2.getText().toString();
                String contest1 = message2.getText().toString();
                String about1 = about2.getText().toString();
                Connection connection = DriverManager.getConnection("jdbc:mysql://62.108.35.72:3306/CarPollution?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","java","m1a2l3i4Q%i6");
                PreparedStatement Statement=connection.prepareStatement("insert into contact values(id,?,?,?)");
                Statement.setString(1,email1);
                Statement.setString(2,about1);
                Statement.setString(3,contest1);
                if(email1.isEmpty() || contest1.isEmpty() || about1.isEmpty()) {
                    mss1();
                } else {
                    mss2();
                    Statement.executeUpdate();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();}
                break;
        }
    }


    public void mss1(){
        Toast.makeText(getActivity(), "Please fill of the fields! ", Toast.LENGTH_SHORT).show();
    }
    public void mss2(){
        Toast.makeText(getActivity(), "We received your complain! \n Thank your for your time!", Toast.LENGTH_SHORT).show();
    }
}
