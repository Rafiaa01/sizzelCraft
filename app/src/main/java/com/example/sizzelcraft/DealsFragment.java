package com.example.sizzelcraft;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sizzelcraft.databinding.FragmentDealsBinding;

import java.util.ArrayList;
import java.util.List;

public class DealsFragment extends Fragment {

    private FragmentDealsBinding binding; // Declare the binding object
    private FoodDealDatabaseHelper databaseHelper; // Declare food deal database helper

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Initialize the binding object
        binding = FragmentDealsBinding.inflate(inflater, container, false);

        // Initialize the database helper
        databaseHelper = new FoodDealDatabaseHelper(requireContext());

        // Fetch food deals from the database
        ArrayList<fooditem> foodDeals = fetchFoodDealsFromDatabase();

        // Set up the adapter
        FoodAdapter adapter = new FoodAdapter(requireContext(), foodDeals);

        // Bind adapter to ListView using binding
        binding.foodListView.setAdapter(adapter);

        return binding.getRoot(); // Return the root view from the binding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to avoid memory leaks
        binding = null;
    }

    // Method to fetch food deals from the database
    private ArrayList<fooditem> fetchFoodDealsFromDatabase() {
        ArrayList<fooditem> foodDeals = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllFoodDeals();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));

                foodDeals.add(new fooditem(imageResId, name, description, price));
            }
            cursor.close();
        } else {
            Toast.makeText(requireContext(), "No deals found in the database", Toast.LENGTH_SHORT).show();
        }
        return foodDeals;
    }
}