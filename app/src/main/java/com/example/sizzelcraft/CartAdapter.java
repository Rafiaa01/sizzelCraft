package com.example.sizzelcraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

public class CartAdapter extends ArrayAdapter<fooditem> {

    private boolean isCart;  // Flag to determine if the view is for the cart or the menu
    private CartDatabaseHelper dbHelper;  // Database helper instance

    public CartAdapter(@NonNull Context context, @NonNull ArrayList<fooditem> items, boolean isCart) {
        super(context, 0, items);
        this.isCart = isCart;
        this.dbHelper = new CartDatabaseHelper(context);  // Initialize the database helper
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menuitem, parent, false);
        }

        // Get current food item
        fooditem item = getItem(position);

        // Bind views
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView titleView = convertView.findViewById(R.id.item_title);
        TextView descriptionView = convertView.findViewById(R.id.item_description);
        TextView priceView = convertView.findViewById(R.id.item_price);
        Button actionButton = convertView.findViewById(R.id.item_add_to_cart);  // "Add" button in the layout

        // Set data to views
        if (item != null) {
            imageView.setImageResource(item.getImageResId());
            titleView.setText(item.getName());
            descriptionView.setText(item.getDescription());
            priceView.setText(item.getPrice());
        }

        // Modify button behavior based on whether it's the cart or menu
        if (isCart) {
            // Change the text of the button to "Remove" and set its functionality
            actionButton.setText("Remove");
            actionButton.setOnClickListener(v -> {
                if (item != null) {
                    // Remove item from database
                    dbHelper.removeItemFromCart(item.getName());

                    // Remove the item from the list
                    remove(item);

                    // Notify adapter to update the list view
                    notifyDataSetChanged();
                }
            });
        } else {
            // For the menu, keep the "Add" functionality
            actionButton.setText("Add");
            actionButton.setOnClickListener(v -> {
                if (item != null) {
                    // Add the item to the cart
                    CartManager.getInstance().addToCart(item);
                }
            });
        }

        return convertView;
    }
}
