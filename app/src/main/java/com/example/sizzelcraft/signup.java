package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText sname, susername, semail, spassword;
    TextView textredirect;
    FirebaseDatabase dataBase;
    DatabaseReference reference;
    Button signupbtn;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize the views
        sname = findViewById(R.id.signupname);
        susername = findViewById(R.id.signupusername1);
        semail = findViewById(R.id.signupemail);
        spassword = findViewById(R.id.signuppasswrd);
        signupbtn = findViewById(R.id.signuobtn1);
        textredirect = findViewById(R.id.textredirect);
        backarrow = findViewById(R.id.backimg22);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate the input fields
                if (!validateFields()) {
                    return;
                }

                // Initialize Firebase
                dataBase = FirebaseDatabase.getInstance();
                reference = dataBase.getReference("users");

                // Get user input
                String name = sname.getText().toString().trim();
                String username = susername.getText().toString().trim();
                String email = semail.getText().toString().trim();
                String password = spassword.getText().toString().trim();

                // Create helper class object
                helperClass h1 = new helperClass(name, username, email, password);

                // Save data to Firebase
                reference.child(username).setValue(h1)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(signup.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                                Intent nint = new Intent(signup.this, homepage.class);
                                startActivity(nint);
                                finish();
                            } else {
                                Toast.makeText(signup.this, "Signup failed. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        textredirect.setOnClickListener(v -> {
            Intent sint = new Intent(signup.this, signin.class);
            startActivity(sint);
        });

        backarrow.setOnClickListener(v -> {
            Intent sint = new Intent(signup.this, signin.class);
            startActivity(sint);
            finish();
        });
    }

    // Method to validate input fields
    private boolean validateFields() {
        String name = sname.getText().toString().trim();
        String username = susername.getText().toString().trim();
        String email = semail.getText().toString().trim();
        String password = spassword.getText().toString().trim();

        if (name.isEmpty()) {
            sname.setError("Name is required");
            sname.requestFocus();
            return false;
        }

        if (username.isEmpty()) {
            susername.setError("Username is required");
            susername.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            semail.setError("Email is required");
            semail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            semail.setError("Enter a valid email address");
            semail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            spassword.setError("Password is required");
            spassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            spassword.setError("Password must be at least 6 characters");
            spassword.requestFocus();
            return false;
        }

        return true;
    }
}
