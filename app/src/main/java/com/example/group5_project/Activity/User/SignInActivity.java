package com.example.group5_project.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button loginBtn;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        username = findViewById(R.id.usernameSignIn);
        password = findViewById(R.id.passwordSignIn);
        loginBtn = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(this);
    }

    private void signUpForm(){
        Intent intend = new Intent(this, SignUpActivity.class);
        startActivity(intend);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.signup) {
            signUpForm();
        }
    }
}
