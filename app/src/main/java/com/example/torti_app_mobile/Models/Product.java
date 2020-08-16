package com.example.torti_app_mobile.Models;

import android.content.Intent;

public class Product {
    private Integer product_id;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
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
