package com.example.group5_project.Entity;

public class CartDetail {
    private long id, phone_id, quantity;
    private Phone phone;

    public CartDetail(long id, long phone_id, long quantity) {
        this.id = id;
        this.phone_id = phone_id;
        this.quantity = quantity;
    }

    public CartDetail(long id, long phone_id, long quantity, Phone phone) {
        this.id = id;
        this.phone_id = phone_id;
        this.quantity = quantity;
        this.phone = phone;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
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
