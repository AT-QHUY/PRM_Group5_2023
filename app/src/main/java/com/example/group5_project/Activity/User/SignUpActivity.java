package com.example.group5_project.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.Activity.User.SignInActivity;
import com.example.group5_project.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registerBtn;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        username = findViewById(R.id.usernameSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmedpasswordSignUp);
        registerBtn = findViewById(R.id.registerBtn);
        signin = findViewById(R.id.tvLogin);

        signin.setOnClickListener(this);
    }

    private void signInForm(){
        Intent intend = new Intent(this, SignInActivity.class);
        startActivity(intend);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvLogin) {
            signInForm();
        }
    }
}
