package com.example.sizzelcraft;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    EditText edtName, edtPhoneNumber, edtEmail, edtAddress;
    Button btnConfirmOrder;
    TextView totalPriceView;
    ArrayList<fooditem> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        totalPriceView = findViewById(R.id.total_price1);

        // Get the cart items from the Intent that started this activity
        if (getIntent().getSerializableExtra("cart_items") != null) {
            cartItems = (ArrayList<fooditem>) getIntent().getSerializableExtra("cart_items");
        }

        // Calculate and display the total price

        // Set up the "Confirm Order" button click listener
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = edtName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();

                // Simple validation to check if fields are empty
                if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    Toast.makeText(Checkout.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Show confirmation message
                    Toast.makeText(Checkout.this, "Order confirmation email sent to you!", Toast.LENGTH_SHORT).show();

                    // You can also send the order details to a backend or process the order here
                    // Example: sendOrderToBackend(name, phoneNumber, email, address, cartItems);
                }
            }
        });
    }
}