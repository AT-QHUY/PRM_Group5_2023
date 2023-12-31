package com.example.group5_project.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.Activity.Common.AdminSharedActivity;
import com.example.group5_project.Activity.User.CartActivity;
import com.example.group5_project.Adapter.AdminOrderListAdapter;
import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.User;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderListActivity extends AdminSharedActivity {

    ListView lvTrainee;
    List<OrderService.GetOrderDTO> order_list;
    AdminOrderListAdapter traineeAdapter;

    OrderService order_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_list);

        lvTrainee = (ListView) findViewById(R.id.lvOrder);
        order_service = OrderRepository.getOrderService();

        ViewData();


        lvTrainee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderService.GetOrderDTO order = order_list.get(position);
                Intent it = new Intent(AdminOrderListActivity.this, AdminOrderDetailListActivity.class);
                it.putExtra("order_id", order.getId());
                startActivity(it);
            }
        });


    }

    public void ViewData(){
        Call<OrderService.GetOrderDTO[]> call = order_service.getAllOrders(JWTUtils.getHeaderAuthorization(AdminOrderListActivity.this));
        call.enqueue(new Callback<OrderService.GetOrderDTO[]>() {
            @Override
            public void onResponse(Call<OrderService.GetOrderDTO[]> call, Response<OrderService.GetOrderDTO[]> response) {
                order_list = Arrays.asList(response.body());
                traineeAdapter = new AdminOrderListAdapter(AdminOrderListActivity.this, R.layout.admin_order_list_layout, order_list);
                lvTrainee.setAdapter(traineeAdapter);
            }

            @Override
            public void onFailure(Call<OrderService.GetOrderDTO[]> call, Throwable t) {
                Toast.makeText(AdminOrderListActivity.this, "Failed to load order list", Toast.LENGTH_SHORT).show();
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