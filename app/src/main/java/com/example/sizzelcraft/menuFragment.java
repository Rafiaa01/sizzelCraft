package com.example.sizzelcraft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sizzelcraft.databinding.FragmentMenuBinding;

public class menuFragment extends Fragment {

    private FragmentMenuBinding binding; // Declare the binding object

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Initialize the binding object
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        // Optional: Set up listeners or other operations
        // For example: binding.someButton.setOnClickListener(...);

        return binding.getRoot(); // Return the root view from the binding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to avoid memory leaks
        binding = null;
    }
}
