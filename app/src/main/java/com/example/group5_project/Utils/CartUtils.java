package com.example.group5_project.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.group5_project.API.Interface.OrderService;
import com.example.group5_project.Entity.CartDetail;
import com.example.group5_project.Entity.OrderDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartUtils {

    public CartUtils() {
    }

    public static void handleAddToCart(Context context, long phone_id, long quantity){
        boolean isPhoneExisted = false;
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String cart_json = mPrefs.getString("cart", "defvalue");
        Gson gson = new Gson();
        List<CartDetail> cart_detail_list;
        final boolean isCartExisted = !cart_json.equals("defvalue");
        if(isCartExisted){
            Type type = new TypeToken< List < CartDetail >>() {}.getType();
            cart_detail_list = (List<CartDetail>) gson.fromJson(cart_json, type);
            for(CartDetail item : cart_detail_list){
                if(item.getPhone_id() == phone_id){
                    item.setQuantity(item.getQuantity() + quantity);
                    isPhoneExisted = true;
                    break;
                }
            }
            if(!isPhoneExisted){
                cart_detail_list.add(new CartDetail(phone_id, quantity));
            }
        }else{
            cart_detail_list = new ArrayList<>();
            CartDetail cart_detail = new CartDetail(phone_id, quantity);
            cart_detail_list.add(cart_detail);
        }
        String json = gson.toJson(cart_detail_list);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("cart", json);
        editor.commit();
    }

    public static void handleDeleteItemFromCart(Context context, long phone_id){
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String cart_json = mPrefs.getString("cart", "defvalue");
        Gson gson = new Gson();
        List<CartDetail> cart_detail_list;
        final boolean isCartExisted = !cart_json.equals("defvalue");
        if(isCartExisted) {
            Type type = new TypeToken<List<CartDetail>>() {
            }.getType();
            cart_detail_list = (List<CartDetail>) gson.fromJson(cart_json, type);
            for (CartDetail item : cart_detail_list) {
                if (item.getPhone_id() == phone_id) {
                    cart_detail_list.remove(item);
                    break;
                }
            }
            String json = gson.toJson(cart_detail_list);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString("cart", json);
            editor.commit();
        }
    }

    public static List<CartDetail> getCartList(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String cart_json = mPrefs.getString("cart", "defvalue");
        Gson gson = new Gson();
        List<CartDetail> cart_detail_list;
        final boolean isCartExisted = !cart_json.equals("defvalue");
        if(!isCartExisted){
            return null;
        }
        Type type = new TypeToken< List < CartDetail >>() {}.getType();
        cart_detail_list = (List<CartDetail>) gson.fromJson(cart_json, type);
        return cart_detail_list;
    }

    public static void clearCart(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        mPrefs.edit().remove("cart").commit();
    }

    public static HashMap<String, Integer> cartDetailToCreateOrderDetailDTO(List<CartDetail> order_detail){
        if(order_detail == null){
            return null;
        }

        HashMap<String, Integer> dto_hash_map = new HashMap<String, Integer>();

        for(CartDetail item : order_detail){
            dto_hash_map.put(Long.toString(item.getPhone_id()), (int) item.getQuantity());
        }

        return dto_hash_map;

    }
}
