package com.example.sizzelcraft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sizzelcraft.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class menuFragment extends Fragment {


    private FragmentMenuBinding binding; // Declare the binding object

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Initialize the binding object
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        // Initialize the food items list
        ArrayList<fooditem> foodItems = new ArrayList<>();
        foodItems.add(new fooditem(R.drawable.burger, "Burger", "Delicious beef burger", "$5.99"));
        foodItems.add(new fooditem(R.drawable.pizza, "Pizza", "Cheesy pepperoni pizza", "$8.99"));
        foodItems.add(new fooditem(R.drawable.pasta, "Pasta", "Creamy Alfredo pasta", "$7.99"));
        foodItems.add(new fooditem(R.drawable.sushi, "Sushi", "Fresh salmon sushi rolls", "$12.99"));
        foodItems.add(new fooditem(R.drawable.tacos, "Tacos", "Spicy chicken tacos", "$6.49"));
        foodItems.add(new fooditem(R.drawable.sandwich, "Sandwich", "Grilled cheese sandwich", "$4.99"));
        foodItems.add(new fooditem(R.drawable.steak, "Steak", "Juicy grilled steak", "$14.99"));
        foodItems.add(new fooditem(R.drawable.soup, "Soup", "Hot and sour soup", "$3.99"));
        foodItems.add(new fooditem(R.drawable.salad, "Salad", "Fresh garden salad", "$5.49"));
        foodItems.add(new fooditem(R.drawable.icecream, "Ice Cream", "Creamy vanilla ice cream", "$2.99"));

        // Set up the adapter
        FoodAdapter adapter = new FoodAdapter(requireContext(), foodItems);

        // Bind adapter to ListView using binding
        binding.foodList.setAdapter(adapter);


        return binding.getRoot(); // Return the root view from the binding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to avoid memory leaks
        binding = null;
    }
}