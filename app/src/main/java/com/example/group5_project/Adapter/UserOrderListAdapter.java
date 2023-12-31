package com.example.group5_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserOrderListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<OrderService.GetOrderDTO> order_list;


    public UserOrderListAdapter(Context context, int layout, List<OrderService.GetOrderDTO> order_list) {
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
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
        ImageView imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);

        String formatted_date;
        OrderService.GetOrderDTO order = order_list.get(position);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(order.getCreateDate());
            formatted_date = new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tvName.setText(order.getCustomerName());
        tvDate.setText(formatted_date);
        tvStatus.setText(order.getStatus() == 0 ? "PENDING" : "CONFIRMED");
        imgProfile.setImageResource(R.drawable.baseline_person_24_black);
        tvPrice.setText(Long.toString(order.getTotalPrice()));

        return convertView;
    }
}
