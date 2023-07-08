package com.example.group5_project.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Repository.OrderRepository;
import com.example.group5_project.R;
import com.example.group5_project.Utils.JWTUtils;
import com.example.group5_project.service.VNPayService;
import com.example.group5_project.shared.VNPayStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VNPayNotificationActivity extends AppCompatActivity {
    private TextView tvPaymentNotification;
    private Button btnBackToHome;
    private OrderService orderService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vnpay_notification);
        tvPaymentNotification = findViewById(R.id.tv_paymentNotification);
        Uri uri = this.getIntent().getData();
        if(uri != null) {
            orderService = OrderRepository.getOrderService();
            try {
                VNPayStatus status = VNPayService.extractVNPayStatus(uri);
                if(status == VNPayStatus.V00) {
                    long orderId = VNPayService.extractVNPTxnRef(uri);
                    Call<Void> orderCall = orderService.updateOrderStatus(orderId, 1, JWTUtils.getHeaderAuthorization(this));
                    orderCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                tvPaymentNotification.setTextColor(Color.rgb(0, 255, 25));
                                tvPaymentNotification.setText(VNPayService.VNPayActionReturn(uri));
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Error: ", "onFailure: " + t.getMessage());
                            tvPaymentNotification.setTextColor(Color.rgb(255, 51, 51));
                            tvPaymentNotification.setText("Transaction fail");
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Error", "onCreate: " + e.getMessage());
            }
        }
        btnBackToHome = findViewById(R.id.btn_home);
        btnBackToHome.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, PhoneListUserActivity.class));
        });
    }
}