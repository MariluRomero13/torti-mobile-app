package com.example.torti_app_mobile.Activities;

import com.example.torti_app_mobile.Models.Product;

public class Devolution {
    private String quantity;
    private String description;
    private Product product;

    public Devolution(String quantity, String description, Product product) {
        this.quantity = quantity;
        this.description = description;
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public Product getProduct() {
        return product;
    }

    public String getQuantity() {
        return quantity;
    }
}
