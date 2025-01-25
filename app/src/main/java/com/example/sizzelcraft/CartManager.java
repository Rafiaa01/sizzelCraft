package com.example.sizzelcraft;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private final ArrayList<fooditem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<fooditem> getCartItems() {
        return cartItems;
    }

    public void addToCart(fooditem item) {
        cartItems.add(item);
    }
}