package com.example.sizzelcraft;

public class foodmodel {

    private String name;
    private int imageResId;
    private String description;
    private double price;

    public foodmodel(String name, int imageResId, String description, double price) {
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
