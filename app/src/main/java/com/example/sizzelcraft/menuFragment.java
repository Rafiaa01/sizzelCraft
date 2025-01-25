package com.example.sizzelcraft;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sizzelcraft.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class menuFragment extends Fragment {


    private FragmentMenuBinding binding; // Declare the binding object
    private DatabaseHelper databaseHelper; // Declare database helper

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Initialize the binding object
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(requireContext());

        // Fetch food items from the database
        ArrayList<fooditem> foodItems = fetchFoodItemsFromDatabase();

        // Set up the adapter
        FoodAdapter adapter = new FoodAdapter(requireContext(), foodItems);

        // Bind adapter to ListView using binding
        binding.foodlist.setAdapter(adapter);

        return binding.getRoot(); // Return the root view from the binding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to avoid memory leaks
        binding = null;
    }

    // Method to fetch food items from the database
    private ArrayList<fooditem> fetchFoodItemsFromDatabase() {
        ArrayList<fooditem> foodItems = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllFoodItems();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));

                foodItems.add(new fooditem(imageResId, name, description, price));
            }
            cursor.close();
        } else {
            Toast.makeText(requireContext(), "No items found in the database", Toast.LENGTH_SHORT).show();
        }
        return foodItems;
    }
}