package com.example.aquamanadmin;

public class Orderinfo {
    private String price;
    private String date;
    private String status;
    private String summary;

    public Orderinfo() {
    }

    public Orderinfo(String price, String date, String status, String summary) {
        this.price = price;
        this.date = date;
        this.status = status;
        this.summary = summary;
    }


    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
