package com.example.sizzelcraft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class logout extends AppCompatActivity {

    Button signOutButton, anotherLoginButton;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Initialize UI elements
        backArrow = findViewById(R.id.backimg);
        signOutButton = findViewById(R.id.logoutbtn2);
        anotherLoginButton = findViewById(R.id.anotherbtn1);

        // Handle "another login" button click
        anotherLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(logout.this, signin.class);
            startActivity(intent);
            finish(); // Finish current activity
        });

        // Handle "sign out" button click
        signOutButton.setOnClickListener(v -> {
            // Perform logout actions
            logoutUser();
        });

        // Handle back arrow click
        backArrow.setOnClickListener(v -> {
            Intent backIntent = new Intent(logout.this, homepage.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void logoutUser() {
        // Clear the user session
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all session data
        editor.apply();

        // Notify the user
        Toast.makeText(this, "You have been logged out successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to the sign-in activity
        Intent intent = new Intent(logout.this, signin.class);
        startActivity(intent);
        finish(); // Finish current activity
    }

    @Override
    public void onBackPressed() {
        // Handle back button press
        super.onBackPressed();
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }
}
