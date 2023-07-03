package com.example.group5_project.Entity;

public class Phone {
    private long id, price , categoryId;
    private String description, name;
    private String image;

    public Phone(long id, long price, String description, String name, long categoryId, String image) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.name = name;
        this.categoryId = categoryId;
        this.image = image;
    }

    public Phone(String name, String description, long price, long categoryId, String image) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.categoryId = categoryId;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategory(long category) {
        this.categoryId = category;
    }


}
