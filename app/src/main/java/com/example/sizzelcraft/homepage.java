package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class homepage extends AppCompatActivity {

    Button firstfragment, secondfragment, fourthfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Initialize buttons and card views
        firstfragment = findViewById(R.id.btnhome);
        secondfragment = findViewById(R.id.btdeals);
        fourthfragment = findViewById(R.id.btncart);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(getResources().getColor(R.color.yellow)); // Set title text color
        toolbar.setTitle("Sizzel Craft"); // Set the toolbar title
        setSupportActionBar(toolbar);

        // Default fragment
        replaceFragment(new HomeFragment());

        // Set button click listeners
        firstfragment.setOnClickListener(v -> replaceFragment(new HomeFragment()));
        secondfragment.setOnClickListener(v -> replaceFragment(new DealsFragment()));
        fourthfragment.setOnClickListener(v -> replaceFragment(new cartfragment()));

        // Set CardView click listeners
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting) {
            startActivity(new Intent(homepage.this, logout.class));
        } else if (itemId == R.id.about) {
            startActivity(new Intent(homepage.this, Aboutus.class));
            Toast.makeText(this, "About Sizzel Craft", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Sizzel Craft: [AndroidStudioProjects\\sizzelCraft2]");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    super.onBackPressed();
                    moveTaskToBack(true);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null); // Optional
        fragmentTransaction.commit();
    }

}
