package com.example.group5_project.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.group5_project.Adapter.CartAdapter;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Button back, checkout;
    TextView totalPrice;

    List<CartDetail> cartDetails_list;
    CartAdapter adapter;
    ListView lvCart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_cart);
        checkout = (Button) findViewById(R.id.btnCheckout);
        back = (Button) findViewById(R.id.btnListProduct);
        totalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("key", "value");
        editor.commit();

        mPrefs.getString("key", "");
        String key = mPrefs.getString("key", "");
        totalPrice.setText(key);
        CartView();
        adapter = new CartAdapter(this, R.layout.item_cart, cartDetails_list);
        lvCart.setAdapter(adapter);

    }

    private void CartView(){
        lvCart = (ListView) findViewById(R.id.cartList);
        cartDetails_list =new ArrayList<>();
        Phone phone = new Phone(1, 1, 1, "Samsung", "iOS", "");
        cartDetails_list.add(new CartDetail(1,1, 2, phone));
        cartDetails_list.add(new CartDetail(2,1, 2, phone));
        cartDetails_list.add(new CartDetail(3,1, 2, phone));
    }

}