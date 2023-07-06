package com.example.group5_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.OrderDetail;
import com.example.group5_project.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderDetailListAdapter extends BaseAdapter {

    private Context context;
    private int layout;

    private Order order;
    private List<OrderDetail> order_detail_list = new ArrayList<>();


    public AdminOrderDetailListAdapter(Context context, int layout, Order order) {
        this.context = context;
        this.layout = layout;
        this.order = order;
    }

    @Override
    public int getCount() {
        return order_detail_list.size();
    }

    @Override
    public Object getItem(int position) {
        return order_detail_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return order_detail_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);

        OrderDetail order_detail = order_detail_list.get(position);
        tvName.setText("phone name");
        tvQuantity.setText(Long.toString(order_detail.getQuantity()));
        tvPrice.setText("phone price");

        return convertView;
    }
}
