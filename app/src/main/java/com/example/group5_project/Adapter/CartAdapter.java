package com.example.group5_project.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Activity.User.CartActivity;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.example.group5_project.Utils.CartUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CartDetail> cartDetails_list;
    private PhoneService phoneService;
    private Phone phone;

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
        phoneService = PhoneRepository.getPhoneService();
        LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(layout, null);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvNameProduct);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
        ImageView imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);

        CartDetail cartDetail = cartDetails_list.get(position);

        Call<Phone> call= phoneService.getPhone(cartDetail.getPhone_id());
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                phone = response.body();
                StringBuilder quantityString = new StringBuilder();
                quantityString.append("QUANTITY: ").append(Long.toString(cartDetail.getQuantity()));
                tvName.setText(phone.getName());
                tvQuantity.setText(Long.toString(cartDetail.getQuantity()));
                tvPrice.setText(Long.toString(phone.getPrice()));
                tvQuantity.setText(quantityString.toString());
                Picasso.get().load( phone.getImage()).into(imgProduct);
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                Log.d("fail", t.toString());
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUtils.handleDeleteItemFromCart(context, cartDetail.getPhone_id());
                CartActivity activity = (CartActivity) context;
                activity.CartView();
                Toast.makeText(context, "Remove item successfully", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }
}
