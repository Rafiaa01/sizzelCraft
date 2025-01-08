package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class logout extends AppCompatActivity {

    Button signoutbt, aloginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logout);

        signoutbt = findViewById(R.id.logoutbtn2);
        aloginbtn = findViewById(R.id.anotherbtn1);
        aloginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(logout.this, homepage.class);
            startActivity(intent);
            finish(); // Check user credentials in the database
        });

        signoutbt.setOnClickListener(v -> {
            // Navigate to the signup activity
            Intent mainIntent = new Intent(logout.this, signup.class);
            startActivity(mainIntent);
        });

    }
    @Override

    public void onBackPressed() {

        super.onBackPressed();
        Toast.makeText(this, "Press back to exit", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }


    }
