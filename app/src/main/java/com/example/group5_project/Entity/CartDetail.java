package com.example.group5_project.Entity;

public class CartDetail {
    private long id, phone_id, quantity;

    public CartDetail(long id, long phone_id, long quantity) {
        this.id = id;
        this.phone_id = phone_id;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(long phone_id) {
        this.phone_id = phone_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
