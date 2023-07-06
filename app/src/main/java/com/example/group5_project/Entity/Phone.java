package com.example.group5_project.Entity;

public class Phone {
    private long id, price , categoryId;
    private String categoryName;
    private String description, name;
    private String image;

    public Phone(long id, long price, long categoryId, String description, String name, String image) {
        this.id = id;
        this.price = price;
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
        this.image = image;
    }

    public Phone(String name, String description, String image, long price, long categoryId){
        this.price = price;
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
        this.image = image;
    }


    public Phone(long price, String categoryName, String description, String name, String image) {
        this.price = price;
        this.categoryName = categoryName;
        this.description = description;
        this.name = name;
        this.image = image;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
