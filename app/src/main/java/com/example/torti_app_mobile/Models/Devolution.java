package com.example.torti_app_mobile.Models;

import java.util.List;

public class Devolution {
    private Integer customer_id;
    private List<Product> details;

    public Devolution(Integer customer_id, List<Product> details) {
        this.customer_id = customer_id;
        this.details = details;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public List<Product> getDetails() {
        return details;
    }
}
