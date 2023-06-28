package com.example.group5_project.Entity;

import java.util.ArrayList;

public class Cart {
    private long id;
    private ArrayList<Long> list_cart_detail_id;

    public Cart(long id, ArrayList<Long> list_cart_detail_id) {
        this.id = id;
        this.list_cart_detail_id = list_cart_detail_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Long> getList_cart_detail_id() {
        return list_cart_detail_id;
    }

    public void setList_cart_detail_id(ArrayList<Long> list_cart_detail_id) {
        this.list_cart_detail_id = list_cart_detail_id;
    }
}
