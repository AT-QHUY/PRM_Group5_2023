package com.example.group5_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.Entity.Order;
import com.example.group5_project.R;

public class AdminOrderDetailListActivity extends AppCompatActivity {

    TextView tvName, tvDate, tvPrice, tvStatus;
    Button btnConfirm;
    ListView lvOrderDetail;

    Order order;

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

        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");

        if(order_id == null){
            Toast.makeText(this,"Unable to load this order",Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }
}