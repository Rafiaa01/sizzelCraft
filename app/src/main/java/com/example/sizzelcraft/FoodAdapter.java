package com.example.sizzelcraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;


public class FoodAdapter extends ArrayAdapter<fooditem> {

    private ArrayList<fooditem> items;

    public FoodAdapter(@NonNull Context context, @NonNull ArrayList<fooditem> items) {
        super(context, 0, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menuitem, parent, false);
        }

        // Get the current FoodItem
        fooditem item = getItem(position);

        // Bind views
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView titleView = convertView.findViewById(R.id.item_title);
        TextView descriptionView = convertView.findViewById(R.id.item_description);
        TextView priceView = convertView.findViewById(R.id.item_price);
        ImageView likeButton = convertView.findViewById(R.id.item_like);
        Button addToCartButton = convertView.findViewById(R.id.item_add_to_cart);

        // Set data to views
        if (item != null) {
            imageView.setImageResource(item.getImageResId());
            titleView.setText(item.getName());
            descriptionView.setText(item.getDescription());
            priceView.setText(item.getPrice());
        }

        CartDatabaseHelper dbHelper = new CartDatabaseHelper(getContext());

        // Set listeners for Like and Add to Cart buttons
        likeButton.setOnClickListener(v -> Toast.makeText(getContext(), "Liked " + item.getName(), Toast.LENGTH_SHORT).show());
        addToCartButton.setOnClickListener(v -> {
            if (item != null) {
                dbHelper.addItemToCart(item); // Save to SQLite database
                Toast.makeText(getContext(), "Added to cart: " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
