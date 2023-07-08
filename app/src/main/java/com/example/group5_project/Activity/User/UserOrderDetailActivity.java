package com.example.group5_project.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.Activity.Admin.AdminOrderDetailListActivity;
import com.example.group5_project.Activity.Common.SharedActivity;
import com.example.group5_project.Adapter.AdminOrderDetailListAdapter;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderDetailActivity extends SharedActivity {

    TextView tvName, tvDate, tvPrice, tvStatus;
    ListView lvOrderDetail;
    OrderService order_service;

    AdminOrderDetailListAdapter adapter;

    OrderService.GetOrderDTO order;
    int order_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_detail);

        tvName = (TextView) findViewById(R.id.tvName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        lvOrderDetail = (ListView) findViewById(R.id.lvOrderDetail);

        order_service = OrderRepository.getOrderService();

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);


        if(order_id == 0){
            Toast.makeText(this,"Unable to load this order",Toast.LENGTH_SHORT).show();
        }
        else{
            ViewData();
        }
    }

    public void ViewData(){
        Call<OrderService.GetOrderDTO> call = order_service.getOrder(order_id, JWTUtils.getHeaderAuthorization(UserOrderDetailActivity.this));
        call.enqueue(new Callback<OrderService.GetOrderDTO>() {
            @Override
            public void onResponse(Call<OrderService.GetOrderDTO> call, Response<OrderService.GetOrderDTO> response) {
                order = response.body();
                adapter = new AdminOrderDetailListAdapter(UserOrderDetailActivity.this, R.layout.admin_order_detail_list_layout, order.getDto_list());
                lvOrderDetail.setAdapter(adapter);
                tvName.setText(order.getCustomerName());
                String formatted_date;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(order.getCreateDate());
                    formatted_date = new SimpleDateFormat("dd-MM-yyyy").format(date);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String total_price =Long.toString(order.getTotalPrice());
                tvDate.setText(  formatted_date);
                tvPrice.setText( total_price);
                tvStatus.setText("Status: " + (order.getStatus() == 0 ? "Pending" : "Confirmed"));

            }

            @Override
            public void onFailure(Call<OrderService.GetOrderDTO> call, Throwable t) {
                Toast.makeText(UserOrderDetailActivity.this, "Failed to load order list", Toast.LENGTH_SHORT).show();
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