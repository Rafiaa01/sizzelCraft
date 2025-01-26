package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class cartfragment extends Fragment {
    private CartAdapter cartAdapter;
    private CartDatabaseHelper dbHelper;
    private TextView totalPriceTextView;
    private double totalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartfragment, container, false);

        // Initialize database helper
        dbHelper = new CartDatabaseHelper(getContext());

        // Fetch cart items from the database
        ArrayList<fooditem> cartItems = dbHelper.getCartItems();

        // Bind ListView
        ListView cartListView = view.findViewById(R.id.cart_list);

        // Set Adapter with items fetched from the database
        cartAdapter = new CartAdapter(getContext(), cartItems, true);
        cartListView.setAdapter(cartAdapter);

        // Bind total price TextView
        totalPriceTextView = view.findViewById(R.id.total_price);

        // Calculate and display the total price
        calculateAndDisplayTotalPrice(cartItems);

        // Checkout button functionality
        Button checkoutButton = view.findViewById(R.id.checkout);
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                // Show message that the cart is empty
                Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                // Open the CheckoutActivity
                Intent intent = new Intent(getContext(), Checkout.class);
                intent.putExtra("total_price", totalPrice);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the data when returning to the fragment
        if (cartAdapter != null) {
            ArrayList<fooditem> updatedCartItems = dbHelper.getCartItems(); // Fetch updated data
            cartAdapter.clear();
            cartAdapter.addAll(updatedCartItems);
            cartAdapter.notifyDataSetChanged();

            // Recalculate and display the total price
            calculateAndDisplayTotalPrice(updatedCartItems);
        }
    }

    // Method to calculate and display the total price
    private void calculateAndDisplayTotalPrice(ArrayList<fooditem> cartItems) {
        totalPrice = 0.0;

        for (fooditem item : cartItems) {
            // Parse the price to double and add to total
            totalPrice += Double.parseDouble(item.getPrice().replace("$", "").trim());
        }

        // Display total price in the TextView
        totalPriceTextView.setText("Total: $" + String.format("%.2f", totalPrice));
    }
}