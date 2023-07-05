package com.example.group5_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.usernameProfile);
        email = findViewById(R.id.emailProfile);
        password = findViewById(R.id.passwordProfile);
        editProfile = findViewById(R.id.editProfileBtn);
    }
}