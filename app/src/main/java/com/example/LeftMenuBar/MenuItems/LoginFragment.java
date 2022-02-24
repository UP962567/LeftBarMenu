package com.example.LeftMenuBar.MenuItems;

import android.content.Intent;
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

import com.example.LeftMenuBar.LoginFiles.LoginActivity;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.example.LeftMenuBar.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFragment extends Fragment implements View.OnClickListener {
    TextView pageName;
    EditText username, password;
    Button loginBT, registrationBt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        pageName = (TextView) view.findViewById(R.id.pageLogin);
        username = (EditText) view.findViewById(R.id.usernameLogin);
        password = (EditText) view.findViewById(R.id.passwordLogin);

        loginBT = (Button) view.findViewById(R.id.loginButtonLogin);
        loginBT.setOnClickListener(this);

        registrationBt = (Button) view.findViewById(R.id.registrerButtonLogin);
        registrationBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButtonLogin:
                new loginFragemntDB().loginFragemntDB();
                break;
            case R.id.registrerButtonLogin:
                break;
        }
    }


    class loginFragemntDB {

        public void loginFragemntDB() {

            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            String user = username.getText().toString();
            String pass = password.getText().toString();
            String quary = "SELECT * FROM user WHERE username = ? and password = ?";
            try {
                conn = DriverManager.getConnection("jdbc:mysql://62.108.35.72:3306/CarPollution?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "java", "m1a2l3i4Q%i6");
                ps = (conn).prepareStatement(quary);
                ps.setString(1, user);
                ps.setString(2, pass);

                UserLogin.setLoginUsername(user);
                UserLogin.setLoginPassword(pass);

                rs = ps.executeQuery();
                if (rs.next()) {
                    Intent i;
                    i = new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Your password does not match in our database!", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) { /* Ignored */}
                }
            }

        }
    }
}
