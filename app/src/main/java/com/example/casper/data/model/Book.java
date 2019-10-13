package com.example.casper.data.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;

    private double price;
    private int coverResourceId;

    public Book(String title, double price, int coverResourceId) {
        this.setTitle(title);
        this.setPrice(price);
        this.setCoverResourceId(coverResourceId);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }
}
