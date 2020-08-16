package com.example.torti_app_mobile.Models;

import java.util.List;

public class History {
    public final static int SALE_COMPLETED = 1;
    public final static int SALE_PENDING = 2;
    public final static int LOST_PRODUCT = 3;
    private int status;
    private String total;
    private String credit;
    private String totalToPay;
    private List<Product> details;

    public History(int status, String total, String credit, String totalToPay, List<Product> details) {
        this.status = status;
        this.total = total;
        this.credit = credit;
        this.totalToPay = totalToPay;
        this.details = details;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setTotalToPay(String totalToPay) {
        this.totalToPay = totalToPay;
    }

    public void setDetails(List<Product> details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public String getTotal() {
        return total;
    }

    public String getCredit() {
        return credit;
    }

    public String getTotalToPay() {
        return totalToPay;
    }

    public List<Product> getDetails() {
        return details;
    }
}
