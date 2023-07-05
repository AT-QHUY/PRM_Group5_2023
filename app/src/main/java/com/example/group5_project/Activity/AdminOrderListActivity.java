package com.example.group5_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.group5_project.Adapter.AdminOrderListAdapter;
import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.User;
import com.example.group5_project.R;

import java.util.ArrayList;
import java.util.Date;

public class AdminOrderListActivity extends AppCompatActivity {

    ListView lvTrainee;
//    TraineeService traineeService;
    ArrayList<Order> order_list;
    AdminOrderListAdapter traineeAdapter;
    Button btnConfirm;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_list);

        lvTrainee = (ListView) findViewById(R.id.lvOrder);
        btnConfirm= (Button) findViewById(R.id.btnConfirm);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        order_list = new ArrayList<>();
        User user = new User(1, "name", "address", "role");

        order_list.add(new Order(1, new ArrayList<>(), new Date(), user, 100, "yes sir" ));
        order_list.add(new Order(2, new ArrayList<>(), new Date(), user, 100, "pending" ));
        order_list.add(new Order(3, new ArrayList<>(), new Date(), user, 100, "yes sir" ));


        traineeAdapter = new AdminOrderListAdapter(AdminOrderListActivity.this, R.layout.admin_order_list_layout, order_list);
        lvTrainee.setAdapter(traineeAdapter);

        lvTrainee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = order_list.get(position);
                if(order.getStatus().equalsIgnoreCase("pending")){
                    btnConfirm.setEnabled(true);
                }
                tvStatus.setText("Status: " + order.getStatus());
            }
        });

    }
}