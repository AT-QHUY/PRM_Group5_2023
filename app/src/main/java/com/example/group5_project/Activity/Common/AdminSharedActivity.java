package com.example.group5_project.Activity.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group5_project.Activity.Admin.AdminOrderListActivity;
import com.example.group5_project.Activity.Admin.MainActivity;
import com.example.group5_project.Activity.Admin.OpenAddPage;
import com.example.group5_project.Activity.User.CartActivity;
import com.example.group5_project.Activity.User.PhoneListUserActivity;
import com.example.group5_project.Activity.User.SignInActivity;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

public class AdminSharedActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent it = new Intent();


        if(id == R.id.itemCart){
            it = new Intent(this, AdminOrderListActivity.class);
        } else if (id == R.id.itemHome) {
            it = new Intent(this, MainActivity.class);
        } else if(id == R.id.itemLogout){
            JWTUtils.clearToken(this);
            it = new Intent(this, SignInActivity.class);
        } else if (id == R.id.itemAdd) {
            it = new Intent(this, OpenAddPage.class);
        }
        startActivity(it);
        return true;
    }
}
