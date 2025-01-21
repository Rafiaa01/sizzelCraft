package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class signin extends AppCompatActivity {

    private Button signinButton, loginButton;
    private EditText loginname, loginPassword;
    private MydbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        // Initialize views
        loginPassword = findViewById(R.id.logoinpasswrd);
        loginname = findViewById(R.id.loginname);
        signinButton = findViewById(R.id.signuobtn1);
        loginButton = findViewById(R.id.loginbtn2);

        // Initialize database helper
        dbHelper = new MydbHelper(this);

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginname.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                // Validate input fields
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(signin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate user credentials using database helper
                if (dbHelper.validateUser(username, password)) {
                    Toast.makeText(signin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin.this, homepage.class);
                    startActivity(intent);
                    finish(); // Close the sign-in activity
                } else {
                    Toast.makeText(signin.this, "Invalid name or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle redirect to signup activity
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this, signup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Press to exit", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }
}
