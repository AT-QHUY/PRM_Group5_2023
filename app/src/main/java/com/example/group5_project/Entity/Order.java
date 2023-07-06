package com.example.group5_project.Entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private long id;
    private ArrayList<Long> list_order_detail_id;
    private Date create_at;

    private User create_by;
    private long total_price;
    private String status;

    public Order(long id, ArrayList<Long> list_order_detail_id, Date create_at, User create_by, long total_price, String status) {
        this.id = id;
        this.list_order_detail_id = list_order_detail_id;
        this.create_at = create_at;
        this.create_by = create_by;
        this.total_price = total_price;
        this.status = status;
    }

    public User getCreate_by() {
        return create_by;
    }

    public void setCreate_by(User create_by) {
        this.create_by = create_by;
    }

    public long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(long total_price) {
        this.total_price = total_price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Long> getList_order_detail_id() {
        return list_order_detail_id;
    }

    public void setList_order_detail_id(ArrayList<Long> list_order_detail_id) {
        this.list_order_detail_id = list_order_detail_id;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
