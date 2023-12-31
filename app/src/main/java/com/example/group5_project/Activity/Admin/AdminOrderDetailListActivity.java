package com.example.group5_project.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.Activity.Common.AdminSharedActivity;
import com.example.group5_project.Adapter.AdminOrderDetailListAdapter;
import com.example.group5_project.Adapter.AdminOrderListAdapter;
import com.example.group5_project.Entity.Order;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderDetailListActivity extends AdminSharedActivity {

    TextView tvName, tvDate, tvPrice, tvStatus;
    Button btnConfirm;
    ListView lvOrderDetail;
    OrderService order_service;

    AdminOrderDetailListAdapter adapter;

    OrderService.GetOrderDTO order;
    int order_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_detail_list);

        tvName = (TextView) findViewById(R.id.tvName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        lvOrderDetail = (ListView) findViewById(R.id.lvOrderDetail);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        order_service = OrderRepository.getOrderService();

        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);


        if(order_id == 0){
            Toast.makeText(this,"Unable to load this order",Toast.LENGTH_SHORT).show();
        }
        else{
            ViewData();
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = order_service.updateOrderStatus(order_id, 1, JWTUtils.getHeaderAuthorization(AdminOrderDetailListActivity.this));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AdminOrderDetailListActivity.this, "Update status successfully", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(AdminOrderDetailListActivity.this, AdminOrderListActivity.class);
                            startActivity(it);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AdminOrderDetailListActivity.this, "Sthing wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void ViewData(){
        Call<OrderService.GetOrderDTO> call = order_service.getOrder(order_id, JWTUtils.getHeaderAuthorization(AdminOrderDetailListActivity.this));
        call.enqueue(new Callback<OrderService.GetOrderDTO>() {
            @Override
            public void onResponse(Call<OrderService.GetOrderDTO> call, Response<OrderService.GetOrderDTO> response) {
                order = response.body();
                adapter = new AdminOrderDetailListAdapter(AdminOrderDetailListActivity.this, R.layout.admin_order_detail_list_layout, order.getDto_list());
                lvOrderDetail.setAdapter(adapter);
                tvName.setText( order.getCustomerName());
                String formatted_date;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(order.getCreateDate());
                    formatted_date = new SimpleDateFormat("dd-MM-yyyy").format(date);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                tvDate.setText(formatted_date);
                tvPrice.setText(Long.toString(order.getTotalPrice()));

                boolean is_pending = order.getStatus() == 0;
                tvStatus.setText("STATUS: " + ( is_pending ? " PENDING" : "CONFIRMED"));
                btnConfirm.setEnabled(is_pending);
            }

            @Override
            public void onFailure(Call<OrderService.GetOrderDTO> call, Throwable t) {
                Toast.makeText(AdminOrderDetailListActivity.this, "Failed to load order list", Toast.LENGTH_SHORT).show();
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