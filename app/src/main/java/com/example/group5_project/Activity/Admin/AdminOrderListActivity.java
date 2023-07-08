package com.example.group5_project.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
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

public class AdminOrderListActivity extends AppCompatActivity {

    ListView lvTrainee;
    List<OrderService.GetOrderDTO> order_list;
    AdminOrderListAdapter traineeAdapter;
    Button btnConfirm;
    TextView tvStatus;

    OrderService order_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_list);

        lvTrainee = (ListView) findViewById(R.id.lvOrder);
        btnConfirm= (Button) findViewById(R.id.btnConfirm);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        order_service = OrderRepository.getOrderService();
        btnConfirm.setEnabled(false);

        ViewData();


        lvTrainee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderService.GetOrderDTO order = order_list.get(position);
                if(order.getStatus() == 0){
                    btnConfirm.setEnabled(true);
                }else{
                    btnConfirm.setEnabled(false);
                }
                tvStatus.setText("Status: " + (order.getStatus() == 0 ? "PENDING" : "CONFIRMED"));
                Intent it = new Intent(AdminOrderListActivity.this, AdminOrderDetailListActivity.class);
                it.putExtra("order_id", order.getId());
                startActivity(it);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Call<Void> call = order_service
//                        .updateOrderStatus(selected_phone_id, new OrderService.UpdateOrderStatusDTO(1),
//                                JWTUtils.getHeaderAuthorization(AdminOrderListActivity.this));
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Toast.makeText(AdminOrderListActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
//                        ViewData();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(AdminOrderListActivity.this, "fail rui`", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
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
}