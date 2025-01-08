package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class homepage extends AppCompatActivity {

    Button firstfragment, secondfragment, thirdfragment, fourthfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firstfragment = findViewById(R.id.btnhome);
        secondfragment = findViewById(R.id.btdeals);
        thirdfragment = findViewById(R.id.btcheckout);
        fourthfragment = findViewById(R.id.btncart);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // Set button click listeners
        firstfragment.setOnClickListener(v -> replaceFragment(new HomeFragment()));

        secondfragment.setOnClickListener(v -> replaceFragment(new DealsFragment()));

        thirdfragment.setOnClickListener(v -> Toast.makeText(homepage.this, "Checkout clicked", Toast.LENGTH_SHORT).show());

        fourthfragment.setOnClickListener(v -> Toast.makeText(homepage.this, "Cart clicked", Toast.LENGTH_SHORT).show());
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
            Intent intent = new Intent(homepage.this, logout.class);
            startActivity(intent);
        } else if (itemId == R.id.about) {
            Intent intent = new Intent(homepage.this, Aboutus.class);
            startActivity(intent);
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
        super.onBackPressed();
        Toast.makeText(this, "Press to exit", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}
