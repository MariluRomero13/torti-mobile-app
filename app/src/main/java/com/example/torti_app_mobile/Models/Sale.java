package com.example.torti_app_mobile.Models;

import java.util.List;

public class Sale {
    private int customer_id;
    private String total;
    private String payment;
    private List<Product> details;

    public Sale(int customerId, String total, String payment, List<Product> details) {
        this.customer_id = customerId;
        this.total = total;
        this.payment = payment;
        this.details = details;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public String getTotal() {
        return total;
    }

    public String getPayment() {
        return payment;
    }

    public List<Product> getDetails() {
        return details;
    }
}
