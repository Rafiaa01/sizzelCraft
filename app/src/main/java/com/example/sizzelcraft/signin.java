package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class signin extends AppCompatActivity {

    Button signinbt, loginbtn;
    EditText loginemail, loginpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        loginpassword = findViewById(R.id.logoinpasswrd);
        loginemail = findViewById(R.id.loginname);
        signinbt = findViewById(R.id.signuobtn1);
        loginbtn = findViewById(R.id.loginbtn2);


        loginbtn.setOnClickListener(v -> {
            if (!validateUsername() | !validatePassword()) {
                return;
            }
            checkUser();
        });

        signinbt.setOnClickListener(v -> {
            Intent mainIntent = new Intent(signin.this, signup.class);
            startActivity(mainIntent);
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Press to exit", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }

    // Validate the username (email)
    public Boolean validateUsername() {
        String val = loginemail.getText().toString();
        if (val.isEmpty()) {
            loginemail.setError("Username can't be empty");
            return false;
        } else {
            loginemail.setError(null);
            return true;
        }
    }

    // Validate the password
    public Boolean validatePassword() {
        String val = loginpassword.getText().toString();
        if (val.isEmpty()) {
            loginpassword.setError("Password can't be empty");
            return false;
        } else {
            loginpassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String username = loginemail.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        // Query to check if the username exists
        Query checkUserDb = reference.orderByChild("username").equalTo(username);

        checkUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Iterate through the result and fetch the password
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passFromDb = userSnapshot.child("password").getValue(String.class);

                        if (passFromDb != null && passFromDb.equals(password)) {
                            // Correct password, login successful
                            Intent intent = new Intent(signin.this, homepage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Password incorrect
                            loginpassword.setError("Invalid credentials");
                            loginpassword.requestFocus();
                        }
                    }
                } else {
                    // No such user found
                    loginemail.setError("No such user found");
                    loginemail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error (optional)
                Toast.makeText(signin.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
