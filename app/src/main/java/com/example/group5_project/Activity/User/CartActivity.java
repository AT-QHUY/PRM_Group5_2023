package com.example.group5_project.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Activity.Admin.AdminOrderListActivity;
import com.example.group5_project.Adapter.CartAdapter;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.CartUtils;
import com.example.group5_project.Utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    Button back, checkout;
    TextView totalPrice;
    PhoneService phone_service;
    OrderService order_service;

    List<CartDetail> cart_detail_list;
    CartAdapter adapter;
    ListView lvCart;
    long total_price ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_cart);
        checkout = (Button) findViewById(R.id.btnCheckout);
        back = (Button) findViewById(R.id.btnListProduct);
        totalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        lvCart = (ListView) findViewById(R.id.cartList);
        phone_service = PhoneRepository.getPhoneService();
        order_service = OrderRepository.getOrderService();
        CartView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PhoneListUserActivity.class);
                startActivity(intent);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartDetail> new_cart_detail_list = CartUtils.getCartList(CartActivity.this);

                if(new_cart_detail_list == null || new_cart_detail_list.isEmpty()){
                    Toast.makeText(CartActivity.this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
                }else{
                    createOrderDialog();
                }
            }
        });

    }

    public void CartView(){
        SharedPreferences mPrefs = getSharedPreferences("session", Context.MODE_PRIVATE);
        String cart_json = mPrefs.getString("cart", "defvalue");
        Gson gson = new Gson();
        Type type = new TypeToken< List < CartDetail >>() {}.getType();
        cart_detail_list = gson.fromJson(cart_json, type);
        adapter = new CartAdapter(this, R.layout.item_cart, cart_detail_list);
        lvCart.setAdapter(adapter);
        total_price = 0;

        if(cart_json.equals("defvalue") || cart_detail_list.isEmpty()){
            totalPrice.setText(Long.toString(total_price));
        }
        for(CartDetail item : cart_detail_list){
             addTotalPrice(item.getPhone_id(), item.getQuantity());
        }
    }

    private void addTotalPrice(long phone_id, long quantity){
        Call<Phone> call= phone_service.getPhone(phone_id);
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                Phone phone = response.body();
                total_price += phone.getPrice() * quantity;
                totalPrice.setText(Long.toString(total_price));
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                Log.d("fail", t.toString());
            }
        });
    }

    private void createOrderDialog(){
        {

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.user_dialog_create_order);

            EditText editTextName = (EditText) dialog.findViewById(R.id.etName);
            EditText editTextAddress = (EditText) dialog.findViewById(R.id.etAddress);

            Button btnSave = (Button) dialog.findViewById(R.id.btnCreate);
            Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);



            btnSave.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View v) {
                    String name = editTextName.getText().toString();
                    String adress = editTextAddress.getText().toString();


                    if(name.equals("") || adress.equals("") ){
                        Toast.makeText(CartActivity.this, "Please input all fields!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        List<CartDetail> new_cart_detail_list = CartUtils.getCartList(CartActivity.this);

                        HashMap<String, Integer> create_order_detail_dto_hash_map = CartUtils.cartDetailToCreateOrderDetailDTO(new_cart_detail_list);

                        if(create_order_detail_dto_hash_map == null || create_order_detail_dto_hash_map.isEmpty()) {
                            Toast.makeText(CartActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                        }else{
                            OrderService.CreateOrderDTO dto = new OrderService.CreateOrderDTO(adress, name, create_order_detail_dto_hash_map);
                            Call<Void> call = order_service.createOrder(dto, JWTUtils.getHeaderAuthorization(CartActivity.this));
                            call.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if(response.isSuccessful()){
                                        CartUtils.clearCart(CartActivity.this);
                                        Toast.makeText(CartActivity.this, "Purchase successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CartActivity.this, AdminOrderListActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(CartActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Toast.makeText(CartActivity.this, "Purchase failed!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                        dialog.dismiss();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    }
}