package com.example.group5_project.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Adapter.UserPhoneListAdapter;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneListUserActivity extends AppCompatActivity {
    ListView lvPhone;
    List<Phone> phone_list;
    UserPhoneListAdapter phone_adapter;
    PhoneService phone_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list_user);

        lvPhone = (ListView) findViewById(R.id.lvPhone);
        phone_service = PhoneRepository.getPhoneService();
        Call<Phone[]> call = phone_service.getAllPhones();
        call.enqueue(new Callback<Phone[]>() {
            @Override
            public void onResponse(Call<Phone[]> call, Response<Phone[]> response) {
                if (response.isSuccessful() && (response.body() != null)) {
                    Phone[] phoneArray = response.body();
                     phone_list = Arrays.asList(phoneArray);
                    phone_adapter = new UserPhoneListAdapter(PhoneListUserActivity.this, R.layout.user_phone_item_layout, phone_list);
                    lvPhone.setAdapter(phone_adapter);
                } else {
                    Toast.makeText(PhoneListUserActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<Phone[]> call, Throwable t) {

            }

        });

        lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhoneListUserActivity.this, UserPhoneDetailActivity.class);
                intent.putExtra("phone_id", id);
                startActivities(new Intent[]{intent});
            }
        });

    }
}