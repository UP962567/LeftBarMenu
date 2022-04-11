package com.example.LeftMenuBar.MenuItems;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.example.LeftMenuBar.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {
    TextView pageName;
    EditText username, password;
    Button loginBT, registrationBt;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(getContext());
        pageName = view.findViewById(R.id.pageLogin);
        username = view.findViewById(R.id.usernameLogin);
        password = view.findViewById(R.id.passwordLogin);
        loginBT = view.findViewById(R.id.loginButtonLogin);
        loginBT.setOnClickListener(this);
        registrationBt = view.findViewById(R.id.registrerButtonLogin);
        registrationBt.setOnClickListener(this);
        return view;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButtonLogin:
                LoginButton();
                break;
            case R.id.registrerButtonLogin:
                break;
        }
    }
    private void LoginButton() {
        String email = Objects.requireNonNull(username.getText()).toString();
        String pass = Objects.requireNonNull(password.getText()).toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(username, "Email is not valid!");
        }  else if (pass.isEmpty() || pass.length()<5){
            showError(password, "Password mast be more then 5 characters");
        }  else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mLoadingBar.dismiss();
                    Toast.makeText(getContext(), "Login is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(getContext(), "Login has Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void showError(EditText til, String s) {
        til.setError(s);
        til.requestFocus();
    }
}
