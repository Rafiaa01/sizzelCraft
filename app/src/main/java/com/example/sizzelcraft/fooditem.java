package com.example.sizzelcraft;
import java.io.Serializable;
public class fooditem implements Serializable {
    private int imageResId;
    private String name;
    private String description;
    private String price;

    public fooditem(int imageResId, String name, String description, String price) {
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
        this.price = price;

    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
