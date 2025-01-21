package com.example.sizzelcraft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class DealsFragment extends Fragment {

    private FoodDealDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deals, container, false);

        // Initialize the database helper
        databaseHelper = new FoodDealDatabaseHelper(getContext());

        // Insert initial food deals (for testing)
        insertInitialFoodDeals();

        // Retrieve food deals from the database
        List<foodmodel> foodDeals = databaseHelper.getAllFoodDeals();

        // Set up the ListView with the custom adapter
        ListView listView = rootView.findViewById(R.id.foodListView);
        cadapter adapter = new cadapter(getContext(), foodDeals);
        listView.setAdapter(adapter);

        return rootView;
    }

    private void insertInitialFoodDeals() {

    }
}
