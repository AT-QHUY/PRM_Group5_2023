package com.example.group5_project.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Activity.Common.AdminSharedActivity;
import com.example.group5_project.Adapter.PhoneAdapter;
import com.example.group5_project.Entity.Phone;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

public class MainActivity extends AdminSharedActivity {
    PhoneService phoneService;
    ListView PhoneListView;
    PhoneAdapter phoneAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhoneListView = findViewById(R.id.PhoneListView);
        phoneService = PhoneRepository.getPhoneService();
        GetData();
    }
    private void GetData(){
        Call<Phone[]> call = phoneService.getAllPhones();
        call.enqueue(new Callback<Phone[]>() {
            @Override
            public void onResponse(Call<Phone[]> call, Response<Phone[]> response) {
                if (response.isSuccessful() && (response.body() != null)) {
                    Phone[] phoneArray = response.body();
                    List<Phone> phoneList = Arrays.asList(phoneArray);
                    phoneAdapter = new PhoneAdapter(MainActivity.this, R.layout.phone_layout, phoneList);
                    PhoneListView.setAdapter(phoneAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<Phone[]> call, Throwable t) {

            }

        });
    }


    public void DialogDelete(String name, long id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Bạn có muốn xóa điện thoại "+name+" không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phoneService.deletePhone(id, JWTUtils.getHeaderAuthorization(MainActivity.this)).enqueue(new Callback<Phone>() {
                    @Override
                    public void onResponse(Call<Phone> call, Response<Phone> response) {
                        if(response.body() != null) {
                            Toast.makeText(MainActivity.this,"Đã xóa "+ name,Toast.LENGTH_SHORT).show();
                            GetData();
                        }
                    }
                    @Override
                    public void onFailure(Call<Phone> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.show();
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