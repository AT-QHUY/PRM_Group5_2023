package com.example.group5_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group5_project.Entity.Phone;
import com.example.group5_project.MainActivity;
import com.example.group5_project.OpenAddPage;
import com.example.group5_project.OpenEditPage;
import com.example.group5_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class PhoneAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private final List<Phone> phones;

    public PhoneAdapter(MainActivity context, int layout, List<Phone> phones) {
        this.context = context;
        this.layout = layout;
        this.phones = phones;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Object getItem(int position) {
        return phones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView phoneName = view.findViewById(R.id.tvPhoneName);
        TextView price = view.findViewById(R.id.tvPrice);
        ImageView imageResource = view.findViewById(R.id.imageResource);

        Phone phone = phones.get(position);
        phoneName.setText(phone.getName());
        price.setText(String.valueOf(phone.getPrice()));
        String imageUri = phone.getImage(); // Assuming phone.getImage() returns a string representing the image URI
        Picasso.get().load(imageUri).into(imageResource);
        ImageView deleteView = view.findViewById(R.id.imgViewDelete);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    context.DialogDelete(phone.getName(),phone.getId());
                }
            });
        ImageView editView = view.findViewById(R.id.imgViewEdit);
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, OpenEditPage.class);
                Long phoneId = phone.getId();
                intent.putExtra("phoneId", phoneId);
                context.startActivity(intent);
            }
        });
        return view;
        }
}
