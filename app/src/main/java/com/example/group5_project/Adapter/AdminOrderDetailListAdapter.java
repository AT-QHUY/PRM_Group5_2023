package com.example.group5_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.API.Interface.PhoneService;
import com.example.group5_project.API.Repository.PhoneRepository;
import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.OrderDetail;
import com.example.group5_project.Entity.Phone;
import com.example.group5_project.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderDetailListAdapter extends BaseAdapter {

    private Context context;
    private int layout;

    private List<OrderService.GetOrderDetailDTO> order_detail_list = new ArrayList<>();


    public AdminOrderDetailListAdapter(Context context, int layout, List<OrderService.GetOrderDetailDTO> dto_list) {
        this.context = context;
        this.layout = layout;
        this.order_detail_list = dto_list;
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
        ImageView imgPhone = (ImageView) convertView.findViewById(R.id.imgPhone);

        OrderService.GetOrderDetailDTO order_detail = order_detail_list.get(position);
        PhoneService service = PhoneRepository.getPhoneService();
        Call<Phone> call = service.getPhone(order_detail.getPhoneId());

        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                tvName.setText(response.body().getName());
                tvQuantity.setText(Integer.toString(order_detail.getQuantity()));
                tvPrice.setText(Integer.toString( order_detail.getPrice()));
                Picasso.get().load(response.body().getImage()).into(imgPhone);

            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                Toast.makeText(context, "Failed load phone value", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
