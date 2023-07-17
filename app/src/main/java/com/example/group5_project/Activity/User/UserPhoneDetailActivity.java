package com.example.group5_project.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Activity.Common.SharedActivity;
import com.example.group5_project.Adapter.UserPhoneListAdapter;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.CartUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPhoneDetailActivity extends SharedActivity {

    ImageView imgPhone, imgAdd, imgMinus;
    TextView tvName, tvPrice, tvDescription, tvQuantity;
    Button btnAddToCart;
    Phone phone;
    PhoneService phoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone_detail);

        imgPhone = (ImageView) findViewById(R.id.imgPhone);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        imgMinus = (ImageView) findViewById(R.id.imgMinus);
        tvName = (TextView) findViewById(R.id.tvPhoneName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);


        Intent intent = getIntent();
        long phone_id = intent.getLongExtra("phone_id",0);
        phoneService = PhoneRepository.getPhoneService();

        if(phone_id == 0){
            Toast.makeText(this,"Cannot load phone detail!", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
            Call<Phone> call = phoneService.getPhone(phone_id);

            call.enqueue(new Callback<Phone>() {
                @Override
                public void onResponse(Call<Phone> call, Response<Phone> response) {
                    if (response.isSuccessful() && (response.body() != null)) {
                        phone = response.body();
                        Picasso.get().load(phone.getImage()).into(imgPhone);
                        tvName.setText(phone.getName());
                        tvPrice.setText(Long.toString(phone.getPrice()));
                        tvDescription.setText(phone.getDescription());
                    } else {
                        Toast.makeText(UserPhoneDetailActivity.this, "Fail", Toast.LENGTH_LONG).show();
                    }

                }
                @Override
                public void onFailure(Call<Phone> call, Throwable t) {

                }

            });
            }catch (Exception e){
                Log.d("loi", e.getMessage());
            }
        }

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                quantity++;
                tvQuantity.setText(Integer.toString(quantity));
            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                if(quantity <=0){
                    return;
                }
                quantity--;
                tvQuantity.setText(Integer.toString(quantity));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long quantity = Long.parseLong(tvQuantity.getText().toString());
                if(quantity <=0){
                    Toast.makeText(UserPhoneDetailActivity.this, "Quantity must be greater than 0!", Toast.LENGTH_SHORT).show();
                }else{
                    CartUtils.handleAddToCart(UserPhoneDetailActivity.this, phone_id,quantity);
                    Toast.makeText(UserPhoneDetailActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserPhoneDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}