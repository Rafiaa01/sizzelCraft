package com.example.sizzelcraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
class cadapter extends BaseAdapter {
    private Context context;
    private List<foodmodel> foodDeals;

    public cadapter(Context context, List<foodmodel> foodDeals) {
        this.context = context;
        this.foodDeals = foodDeals;
    }

    @Override
    public int getCount() {
        return foodDeals.size();
    }

    @Override
    public Object getItem(int position) {
        return foodDeals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.food_deal_items, parent, false);
        }

        // Find views
        TextView nameTextView = convertView.findViewById(R.id.foodName);
        TextView descriptionTextView = convertView.findViewById(R.id.foodDescription);
        TextView priceTextView = convertView.findViewById(R.id.foodPrice);
        ImageView imageView = convertView.findViewById(R.id.foodImage);
        Button addButton = convertView.findViewById(R.id.addbutton);

        // Get the current food item
        foodmodel foodDeal = (foodmodel) getItem(position);

        // Set data to views
        nameTextView.setText(foodDeal.getName());
        descriptionTextView.setText(foodDeal.getDescription());
        priceTextView.setText(String.format("$%.2f", foodDeal.getPrice()));
        imageView.setImageResource(foodDeal.getImageResId());

        // Add click listener for the "Add" button
        addButton.setOnClickListener(v -> {
            // Add to cart (you would need to implement the cart logic)
            // cart.addToCart(new foodmodel(...));
            Toast.makeText(context, foodDeal.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
