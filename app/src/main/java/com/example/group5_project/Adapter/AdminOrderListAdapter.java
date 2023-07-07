package com.example.group5_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group5_project.Entity.Order;
import com.example.group5_project.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdminOrderListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Order> order_list;


    public AdminOrderListAdapter(Context context, int layout, List<Order> order_list) {
        this.context = context;
        this.layout = layout;
        this.order_list = order_list;
    }

    @Override
    public int getCount() {
        return order_list.size();
    }

    @Override
    public Object getItem(int position) {
        return order_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return order_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
        ImageView imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);

        Order order = order_list.get(position);
        tvName.setText(order.getCreate_by().getName());
        tvDate.setText(dateFormat.format(order.getCreate_at()));
        tvStatus.setText(order.getStatus());
        imgProfile.setImageResource(R.drawable.ic_baseline_person_outline_24);
        tvPrice.setText(Long.toString(order.getTotal_price()));

        return convertView;
    }
}
