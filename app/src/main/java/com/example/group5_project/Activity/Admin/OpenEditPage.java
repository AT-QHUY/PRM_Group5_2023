package com.example.group5_project.Activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Activity.Common.AdminSharedActivity;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenEditPage extends AdminSharedActivity implements View.OnClickListener{
    private long id, price , categoryId;
    private String description, name;
    private String image;
    long phoneId;
    Button editButton;
    PhoneService phoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_phone);
        phoneId = getIntent().getLongExtra("phoneId",0);
        phoneService = PhoneRepository.getPhoneService();
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(this);
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        phoneService.getPhone(phoneId).enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                if (response.isSuccessful()) {
                    Phone phone = response.body();
                    // Access the data of the phone
                    id = phone.getId();
                    name = phone.getName();
                    description= phone.getDescription();
                    price = phone.getPrice();
                    image = phone.getImage();
                    categoryId = phone.getCategoryId();

                    EditText nameText = findViewById(R.id.textName);
                    nameText.setText(name);
                    EditText descriptionText = findViewById(R.id.textDescription);
                    descriptionText.setText(description);
                    EditText priceText = findViewById(R.id.textPrice);
                    priceText.setText(String.valueOf(price));
                    EditText categoryText = findViewById(R.id.textCategory);
                    categoryText.setText(String.valueOf(categoryId));
                    EditText imageText = findViewById(R.id.textImage);
                    imageText.setText(image);
                }
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Perform the desired action when the button is clicked
        int viewId = v.getId();
        if (viewId == R.id.edit_button) {
            // Handle the edit button click event
            // Get the updated values from the EditText fields
            EditText nameText = findViewById(R.id.textName);
            String newName = nameText.getText().toString();

            EditText descriptionText = findViewById(R.id.textDescription);
            String newDescription = descriptionText.getText().toString();

            EditText priceText = findViewById(R.id.textPrice);
            String priceString = priceText.getText().toString();
            long newPrice = Long.parseLong(priceString);

            EditText categoryText = findViewById(R.id.textCategory);
            String categoryString = categoryText.getText().toString();
            long newCategoryId = Long.parseLong(categoryString);

            EditText imageText = findViewById(R.id.textImage);
            String newImage = imageText.getText().toString();

            // Create a new Phone object with the updated values
            Phone updatedPhone = new Phone(id, newPrice,  newCategoryId,newDescription, newName  , newImage);

            // Call the API service to update the phone data
            phoneService.updatePhone(phoneId, updatedPhone, JWTUtils.getHeaderAuthorization(OpenEditPage.this)).enqueue(new Callback<Phone>() {
                @Override
                public void onResponse(Call<Phone> call, Response<Phone> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(OpenEditPage.this, "Save Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OpenEditPage.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Handle unsuccessful response
                        System.out.println("Failed to update phone data.");
                    }
                }

                @Override
                public void onFailure(Call<Phone> call, Throwable t) {
                    // Handle network failure or exception
                    System.out.println("Failed to update phone data: " + t.getMessage());
                }
            });
        } else if (viewId == R.id.cancel_button) {
            // Handle the cancel button click event
            // Add the logic to cancel the edit operation or navigate back to the previous activity
            Intent intent = new Intent(OpenEditPage.this, MainActivity.class);
            startActivity(intent);
        }
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
