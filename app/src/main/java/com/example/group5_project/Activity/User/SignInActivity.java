package com.example.group5_project.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.API.Interface.UserService;
import com.example.group5_project.API.Repository.UserRepository;
import com.example.group5_project.Entity.User;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    UserService user_service;
    EditText username;
    EditText password;
    Button loginBtn;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        username = (EditText) findViewById(R.id.usernameSignIn);
        password = (EditText) findViewById(R.id.passwordSignIn);
        loginBtn = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signup);
        user_service = UserRepository.getUserService();

        signup.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    private void signUpForm(){
        Intent intend = new Intent(this, SignUpActivity.class);
        startActivity(intend);
        finish();
    }

    private void onSignin(){
        String email = username.getText().toString();
        String pass = password.getText().toString();
        if(email.equals("") || pass.equals("")){
            Toast.makeText(SignInActivity.this, "Please input username and password!", Toast.LENGTH_SHORT).show();
        }else{
            UserService.LoginDTO dto = new UserService.LoginDTO(email, pass);
            Call<UserService.TokenDTO> call = user_service.login(dto);
            call.enqueue(new Callback<UserService.TokenDTO>() {
                @Override
                public void onResponse(Call<UserService.TokenDTO> call, Response<UserService.TokenDTO> response) {
                    UserService.TokenDTO res = response.body();
                    if(res != null){
                        Intent it = new Intent(SignInActivity.this, PhoneListUserActivity.class);
                        try {
                            JWTUtils.decoded(res.getToken());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        startActivity(it);
                    }
                }

                @Override
                public void onFailure(Call<UserService.TokenDTO> call, Throwable t) {
                    Toast.makeText(SignInActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.signup) {
            signUpForm();
        }else if(id == R.id.loginbtn){
            onSignin();
        }
    }


}
