package com.example.group5_project.Activity.User;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.Activity.Admin.AdminOrderDetailListActivity;
import com.example.group5_project.Activity.Common.SharedActivity;
import com.example.group5_project.Adapter.UserOrderListAdapter;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderActivity extends SharedActivity {

    ListView lv_order;
    List<OrderService.GetOrderDTO> order_list;
    UserOrderListAdapter order_adapter;
    OrderService order_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        lv_order = (ListView) findViewById(R.id.lvOrder);
        order_service = OrderRepository.getOrderService();

        ViewData();


        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderService.GetOrderDTO order = order_list.get(position);
                Intent it = new Intent(UserOrderActivity.this, UserOrderDetailActivity.class);
                it.putExtra("order_id", order.getId());
                startActivity(it);
            }
        });
    }

    public void ViewData(){
        Call<OrderService.GetOrderDTO[]> call = order_service.getAllOrders(JWTUtils.getHeaderAuthorization(UserOrderActivity.this));
        call.enqueue(new Callback<OrderService.GetOrderDTO[]>() {
            @Override
            public void onResponse(Call<OrderService.GetOrderDTO[]> call, Response<OrderService.GetOrderDTO[]> response) {
                order_list = Arrays.asList(response.body());
                order_adapter = new UserOrderListAdapter(UserOrderActivity.this, R.layout.admin_order_list_layout, order_list);
                lv_order.setAdapter(order_adapter);
            }

            @Override
            public void onFailure(Call<OrderService.GetOrderDTO[]> call, Throwable t) {
                Toast.makeText(UserOrderActivity.this, "Failed to load order list", Toast.LENGTH_SHORT).show();
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