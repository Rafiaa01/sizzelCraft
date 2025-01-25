package com.example.sizzelcraft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class homepage extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    private int cartquantity=0;


    // Initialize views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage); // Ensure this matches your XML file name

        // Initialize views
        drawerLayout = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // Set up the toolbar
        setSupportActionBar(toolbar);

        // Set up ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_nav, // Add this string in res/values/strings.xml
                R.string.close_nav  // Add this string in res/values/strings.xml
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
View headerview=navigationView.getHeaderView(0);
TextView username=headerview.findViewById(R.id.lognamebar);
TextView email=headerview.findViewById(R.id.logembr);
        MydbHelper dbHelper = new MydbHelper(this);
        String[] userData = dbHelper.getUserData();  // This will return an array with name and email

        // Set the username and email to the TextViews
        username.setText(userData[0]);  // Set the username
        email.setText(userData[1]);  //
        // Set NavigationView Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                // Log the item clicked
                Log.d("Navigation", "Selected Item ID: " + itemId);

                if (itemId == R.id.navhome) {
                    Log.d("Navigation", "Home selected");
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.navsettings) {
                    Log.d("Navigation", "Settings selected");
                    startActivity(new Intent(homepage.this, signin.class));
                } else if (itemId == R.id.navabout) {
                    Log.d("Navigation", "About selected");
                    startActivity(new Intent(homepage.this, Aboutus.class));
                } else if (itemId == R.id.navshare) {
                    Log.d("Navigation", "Share selected");
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Sizzel Craft!");
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } else if (itemId == R.id.navlogout) {
                    Log.d("Navigation", "Logout selected");
                    startActivity(new Intent(homepage.this, logout.class));
                }

                // Close the navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;  // Ensure this is true to mark the item as handled
            }
                                                         });

            // Load the default fragment (e.g., HomeFragment) when the activity starts
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.navhome); // Set default selected item
        }
// Set up BottomNavigationView listener
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homee) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.menu) {
                replaceFragment(new menuFragment());
            } else if (item.getItemId() == R.id.deals) {
                replaceFragment(new DealsFragment());
            } else if (item.getItemId() == R.id.cart) {

                replaceFragment(new LocFragment());

            }
            return true;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart); // Retrieve the actual MenuItem
        View actionView = menuItem.getActionView();
        TextView cartBadge = actionView.findViewById(R.id.cart_badge_text);
        cartBadge.setText("2"); // Set the badge count
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true; // Return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            replaceFragment(new cartfragment());
            Toast.makeText(this, "Cart clicked!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}