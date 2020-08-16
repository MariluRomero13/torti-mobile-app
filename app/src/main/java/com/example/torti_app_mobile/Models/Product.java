package com.example.torti_app_mobile.Models;

import android.content.Intent;

public class Product {
    private Integer product_id;
    private String description;
    String product;
    String price;
    Integer quantity;

    public Product(String product, String price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Integer id, String product, String price, Integer quantity) {
        this.product_id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Integer id, Integer quantity) {
        this.product_id = id;
        this.quantity = quantity;
    }

    public Product(String product, String price) {
        this.product = product;
        this.price = price;
    }

    public Product(Integer id, String name, Integer quantity, String description) {
        this.product_id = id;
        this.product = name;
        this.quantity = quantity;
        this.description = description;
    }

    public Product(Integer id, Integer quantity, String description) {
        this.product_id = id;
        this.quantity = quantity;
        this.description = description;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return product_id;
    }
}
