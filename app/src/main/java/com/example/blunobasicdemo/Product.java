package com.example.blunobasicdemo;

public class Product{

    String name;
    float price;
    String description;
    int image;
    int id_tag;
    int quantity = 0;

    public Product( String name, float price, String description, int image, int quantity){

        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;

        this.quantity = quantity;

    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id_tag) {
        this.id_tag = id_tag;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
