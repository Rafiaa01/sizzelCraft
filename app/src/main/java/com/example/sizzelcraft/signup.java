package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class signup extends AppCompatActivity {

    private EditText signupEmail, signupUsername, signupPassword, signupName;
    private TextView textredirect;
    private ImageView backarrow;
    private Button signupButton;
    private MydbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        signupEmail = findViewById(R.id.signupemail);
        signupUsername = findViewById(R.id.signupusername1);
        signupPassword = findViewById(R.id.signuppasswrd);
        signupName = findViewById(R.id.signupname);
        textredirect = findViewById(R.id.textredirect);
        backarrow = findViewById(R.id.backimg22);
        signupButton = findViewById(R.id.signuobtn1);

        dbHelper = new MydbHelper(this);

        // Set up the signup button click listener
        signupButton.setOnClickListener(v -> {
            if (validateFields()) {
                String email = signupEmail.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String name = signupName.getText().toString().trim();

                boolean result = dbHelper.adduser(name, username, email, password);
                if (result) {
                    Toast.makeText(signup.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    Intent sintent = new Intent(signup.this, homepage.class);
                    startActivity(sintent);

                } else {
                    Toast.makeText(signup.this, "Error adding user. Email may exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirect to the sign-in page
        textredirect.setOnClickListener(v -> {
            Intent sint = new Intent(signup.this, signin.class);
            startActivity(sint);
        });

        // Handle back arrow click
        backarrow.setOnClickListener(v -> {
            Intent sint = new Intent(signup.this, signin.class);
            startActivity(sint);
            finish();
        });

    }



    // Method to validate input fields
    private boolean validateFields() {
        String name = signupName.getText().toString().trim();
        String username = signupUsername.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        if (name.isEmpty()) {
            signupName.setError("Name is required");
            signupName.requestFocus();
            return false;
        }

        if (username.isEmpty()) {
            signupUsername.setError("Username is required");
            signupUsername.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Enter a valid email address");
            signupEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            signupPassword.setError("Password is required");
            signupPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            signupPassword.setError("Password must be at least 6 characters");
            signupPassword.requestFocus();
            return false;
        }

        return true;
    }
}
