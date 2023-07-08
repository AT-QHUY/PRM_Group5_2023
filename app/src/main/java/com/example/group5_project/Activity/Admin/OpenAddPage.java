package com.example.group5_project.Activity.Admin;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenAddPage extends AppCompatActivity implements View.OnClickListener{
    PhoneService phoneService;
    EditText etName, etDescription, etPrice, etCategory, etImage;
    Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_phone);

        etName = findViewById(R.id.textName);
        etDescription = findViewById(R.id.textDescription);
        etPrice = findViewById(R.id.textPrice);
        etCategory = findViewById(R.id.textCategory);
        etImage = findViewById(R.id.textImage);
        btnSave = findViewById(R.id.edit_button);
        btnSave.setOnClickListener(this);
        btnCancel = findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(this);
        phoneService = PhoneRepository.getPhoneService();
    }

    private void save(){
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String price = etPrice.getText().toString();
        String categoryId = etCategory.getText().toString();
        String image = etImage.getText().toString();

        Phone phone = new Phone(name, description, image, Long.parseLong(price), Long.parseLong(categoryId));
        try{
            Call<Phone> call = phoneService.createPhone(phone, JWTUtils.getHeaderAuthorization(OpenAddPage.this));
            call.enqueue(new Callback<Phone>() {
                @Override
                public void onResponse(Call<Phone> call, Response<Phone> response) {
                    if (response.body() != null) {
                        Toast.makeText(OpenAddPage.this, "Save Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OpenAddPage.this, MainActivity.class);
                    startActivity(intent);
                    }
                }
                @Override
                public void onFailure(Call<Phone> call, Throwable t) {
                }
            });

        } catch (Exception e){
            Log.d("Loi", e.getMessage());
        }
    }
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.edit_button) {
            save();
        }else if (viewId == R.id.cancel_button) {
            // Handle the cancel button click event
            // Add the logic to cancel the edit operation or navigate back to the previous activity
            Intent intent = new Intent(OpenAddPage.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
