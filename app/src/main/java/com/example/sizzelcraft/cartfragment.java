package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class cartfragment extends Fragment {
    private CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartfragment, container, false);

        // Bind ListView
        ListView cartListView = view.findViewById(R.id.cart_list);

        // Set Adapter
        cartAdapter = new CartAdapter(getContext(), CartManager.getInstance().getCartItems(),true);
        cartListView.setAdapter(cartAdapter);

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
        // Notify adapter of data changes when returning to the fragment
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
        }
    }

}