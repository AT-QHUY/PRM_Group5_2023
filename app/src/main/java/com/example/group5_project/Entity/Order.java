package com.example.group5_project.Entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private long id;
    private ArrayList<Long> list_order_detail_id;
    private Date create_at;
    private String status;

    public Order(long id, ArrayList<Long> list_order_detail_id, Date create_at, String status) {
        this.id = id;
        this.list_order_detail_id = list_order_detail_id;
        this.create_at = create_at;
        this.status = status;
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
