package com.example.group5_project.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.Activity.User.UserPhoneDetailActivity;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.CartUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserPhoneListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Phone> phone_list;


    public UserPhoneListAdapter(Context context, int layout, List<Phone> phone_list) {
        this.context = context;
        this.layout = layout;
        this.phone_list = phone_list;
    }

    @Override
    public int getCount() {
        return phone_list.size();
    }

    @Override
    public Object getItem(int position) {
        return phone_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return phone_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvPhoneName);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPhonePrice);
        ImageView imgPhone = (ImageView) convertView.findViewById(R.id.imgPhone);
        ImageView btnAddToCart = (ImageView) convertView.findViewById(R.id.imgAddToCart);


        Phone phone = phone_list.get(position);
        tvName.setText(phone.getName());
        tvPrice.setText(Long.toString(phone.getPrice()));
        Picasso.get().load(phone.getImage()).into(imgPhone);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUtils.handleAddToCart(context, phone.getId(), 1);
                Toast.makeText(context, "Added successfully ", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
