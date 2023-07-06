package com.example.group5_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CartDetail> cartDetails_list;

    public CartAdapter(Context context, int layout, List<CartDetail> cartDetails_list) {
        this.context = context;
        this.layout = layout;
        this.cartDetails_list = cartDetails_list;
    }

    @Override
    public int getCount() {
        return cartDetails_list.size();
    }

    @Override
    public Object getItem(int position) {

        return cartDetails_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartDetails_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvNameProduct);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);

        CartDetail cartDetail = cartDetails_list.get(position);
        tvName.setText(cartDetail.getPhone().getName());
        tvQuantity.setText(Long.toString(cartDetail.getQuantity()));
        tvPrice.setText(Long.toString(cartDetail.getPhone().getPrice()));
//        imgProduct.setImageResource(cartDetail.getPhone().getImage(1));

        return convertView;
    }
}
