package com.example.sizzelcraft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    CardView c1, c2, c3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the CardView elements
        c1 = view.findViewById(R.id.card1);
        c2 = view.findViewById(R.id.card2);
        c3 = view.findViewById(R.id.card3);

        // Set click listeners
        c1.setOnClickListener(v -> replaceFragment(new menuFragment()));
        c2.setOnClickListener(v -> replaceFragment(new DealsFragment()));
        c3.setOnClickListener(v -> replaceFragment(new cartfragment()));

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: to maintain back stack
        fragmentTransaction.commit();
    }
}
