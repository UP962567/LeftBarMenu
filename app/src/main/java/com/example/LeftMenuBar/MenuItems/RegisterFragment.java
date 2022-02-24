package com.example.LeftMenuBar.MenuItems;

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

import com.example.LeftMenuBar.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    TextView pagename1;
    EditText emailRegister1,numberRegister1,usernameRegister1,passwordRegister1,
            RepasswordRegister1,fullNameRegister1,countryNameRegister1;
    Button register1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.fragment_register, container, false);


        pagename1 = (TextView) view.findViewById(R.id.pageRegister);
        emailRegister1 = (EditText) view.findViewById(R.id.emailRegister);
        numberRegister1 = (EditText) view.findViewById(R.id.numberRegister);
        usernameRegister1 = (EditText) view.findViewById(R.id.usernameRegister);
        passwordRegister1 = (EditText) view.findViewById(R.id.passwordRegister);
        RepasswordRegister1 = (EditText) view.findViewById(R.id.RepasswordRegister);
        fullNameRegister1 = (EditText) view.findViewById(R.id.fullNameRegister);
        countryNameRegister1 = (EditText) view.findViewById(R.id.countryNameRegister);

        register1 = (Button) view.findViewById(R.id.registerButtonRegister);
        register1.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        switch(v.getId()){
            case R.id.registerButtonRegister:
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), (CharSequence) e, Toast.LENGTH_SHORT).show();
                } try {
                    String user = usernameRegister1.getText().toString();
                    String pass = passwordRegister1.getText().toString();
                    String repass = RepasswordRegister1.getText().toString();
                    String country = countryNameRegister1.getText().toString();
                    String number = numberRegister1.getText().toString();
                    String fullname = fullNameRegister1.getText().toString();
                    String email = emailRegister1.getText().toString();
                    conn = DriverManager.getConnection("jdbc:mysql://62.108.35.72:3306/CarPollution?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","java","m1a2l3i4Q%i6");
                    ps=conn.prepareStatement("insert into user values(ID,?,?,?,?,?,?)");
                ps.setString(1,user);
                ps.setString(2,pass);
                ps.setString(3,country);
                ps.setString(4,number);
                ps.setString(5,email);
                ps.setString(6,fullname);
                    if(user.isEmpty() || pass.isEmpty() || country.isEmpty() || number.isEmpty() || email.isEmpty() || fullname.isEmpty()) {
                        mss1();
                    } else if(pass.equalsIgnoreCase(repass)) {
                        ps.executeUpdate();
                        mss2();
                    } else if(repass == pass) {
                        mss3();
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
                break;
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
