package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class cartfragment extends Fragment {
    private CartAdapter cartAdapter;
    private CartDatabaseHelper dbHelper;

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

        // Checkout button functionality
        Button checkoutButton = view.findViewById(R.id.checkout);
        checkoutButton.setOnClickListener(v -> {
            // Open the CheckoutActivity
            Intent intent = new Intent(getContext(), Checkout.class);
            startActivity(intent);
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
        }
    }
}
