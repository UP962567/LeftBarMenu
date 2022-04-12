package com.example.LeftMenuBar.MenuItems;

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

import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.SetupLogin;
import com.example.LeftMenuBar.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    TextView pagename1;
    EditText emailRegister1,passwordRegister1, RepasswordRegister1;
    Button register1;

    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(getContext());


        pagename1 = (TextView) view.findViewById(R.id.pageRegister);
        emailRegister1 = (EditText) view.findViewById(R.id.emailRegister);
        passwordRegister1 = (EditText) view.findViewById(R.id.passwordRegister);
        RepasswordRegister1 = (EditText) view.findViewById(R.id.RepasswordRegister);

        register1 = (Button) view.findViewById(R.id.registerButtonRegister);
        register1.setOnClickListener(this);

        return view;
    }

    private void RegistrationCall() {
        String pass = passwordRegister1.getText().toString();
        String repass = RepasswordRegister1.getText().toString();
        String email = emailRegister1.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError(emailRegister1, "Email is not valid!");
        }  else if (pass.isEmpty() || pass.length()<5){
            showError(passwordRegister1, "Password mast be more then 5 characters");
        } else if (!repass.equals(pass)){
            showError(RepasswordRegister1, "Password did not match!");
        } else {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mLoadingBar.dismiss();
                    Toast.makeText(getContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), SetupLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(getContext(), "Registration is Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showError(EditText til, String s) {
        til.setError(s);
        til.requestFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButtonRegister:
                RegistrationCall();
                break;
            case R.id.registrerButtonLogin:
                break;
        }
    }
}
