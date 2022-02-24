package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.LeftMenuBar.Utils.DatabaseCodes;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.example.LeftMenuBar.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsLogin extends Fragment implements View.OnClickListener {

    static UserLogin U = new UserLogin();
    EditText emailSettingLogin1,numberSettingLogin1,usernameSettingLogin1,passwordSettingLogin1,
            RepasswordSettingLogin1,fullNameSettingLogin1,countrySettingLogin1;
    Button registerButtonRegister;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_settings_login, container, false);

        new DatabaseCodes.getLoginInofrmation().getLoginInofrmation();

        emailSettingLogin1 = (EditText) view.findViewById(R.id.emailSettingLogin);
        numberSettingLogin1 = (EditText) view.findViewById(R.id.numberSettingLogin);
        usernameSettingLogin1 = (EditText) view.findViewById(R.id.usernameSettingLogin);
        passwordSettingLogin1 = (EditText) view.findViewById(R.id.passwordSettingLogin);
        RepasswordSettingLogin1 = (EditText) view.findViewById(R.id.RepasswordSettingLogin);
        fullNameSettingLogin1 = (EditText) view.findViewById(R.id.fullNameSettingLogin);
        countrySettingLogin1 = (EditText) view.findViewById(R.id.countrySettingLogin);

        emailSettingLogin1.setText(U.getLoginEmail());
        numberSettingLogin1.setText(U.getLoginNumber());
        usernameSettingLogin1.setText(U.getLoginUsername());
        passwordSettingLogin1.setText(U.getLoginPassword());
        RepasswordSettingLogin1.setText(U.getLoginPassword());
        fullNameSettingLogin1.setText(U.getLoginFullname());
        countrySettingLogin1.setText(U.getLoginCountry());



        registerButtonRegister = (Button) view.findViewById(R.id.updateButtonSettings);
        registerButtonRegister.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.updateButtonSettings:
                new SettingsFragmentDB().SettingsFragmentDB();
                new DatabaseCodes.getLoginInofrmation().getLoginInofrmation();
        }
    }

    class SettingsFragmentDB {

        public void SettingsFragmentDB() {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), (CharSequence) e, Toast.LENGTH_SHORT).show();
            } try {
                String user = usernameSettingLogin1.getText().toString();
                String pass = passwordSettingLogin1.getText().toString();
                String repass = passwordSettingLogin1.getText().toString();
                String country = countrySettingLogin1.getText().toString();
                String number = numberSettingLogin1.getText().toString();
                String fullname = fullNameSettingLogin1.getText().toString();
                String email = emailSettingLogin1.getText().toString();
                conn = DriverManager.getConnection("jdbc:mysql://62.108.35.72:3306/CarPollution?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "java", "m1a2l3i4Q%i6");
                ps = conn.prepareStatement("UPDATE user SET " +
                        "username = ?," +
                        " password = ?," +
                        " country = ?," +
                        " number = ?," +
                        " email = ?," +
                        " fullname = ?" +
                        " WHERE (ID = " + U.getLoginID() +")");
                ps.setString(1, user);
                ps.setString(2, pass);
                ps.setString(3, country);
                ps.setString(4, number);
                ps.setString(5, email);
                ps.setString(6, fullname);

                ps.executeUpdate();

                if (user.isEmpty() || pass.isEmpty() || country.isEmpty() || number.isEmpty() || email.isEmpty() || fullname.isEmpty()) {
                    mss1();
                } else if (pass.equalsIgnoreCase(repass)) {
                    ps.executeUpdate();
                    mss2();
                }
            }
            catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
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


    public void mss1(){
        Toast.makeText(getActivity(), "Please fill of the fields! ", Toast.LENGTH_SHORT).show();
    }
    public void mss2(){
        Toast.makeText(getActivity(), "Data Registered Successfully", Toast.LENGTH_SHORT).show();
    }
    public void mss3(){
        Toast.makeText(getActivity(), "Password did not match", Toast.LENGTH_SHORT).show();
    }


}
