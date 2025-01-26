package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Checkout extends AppCompatActivity {

    EditText edtName, edtPhoneNumber, edtEmail, edtAddress;
    Button btnConfirmOrder;
    TextView totalPriceView;
    double totalPrice;
    OrderDatabaseHelper orderDatabaseHelper; // SQLite Helper
    CartDatabaseHelper cartdb;

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

        // Initialize database helper
        orderDatabaseHelper = new OrderDatabaseHelper(this);
        cartdb=new CartDatabaseHelper(this);

        // Get total price from intent
        totalPrice = getIntent().getDoubleExtra("total_price", 0.0);
        totalPriceView.setText("Total Price: $" + String.format("%.2f", totalPrice));

        // Confirm order button click
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user details
                String name = edtName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();

                // Validate fields
                if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    Toast.makeText(Checkout.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert order into database and get order ID
                    long orderId = orderDatabaseHelper.insertOrder(name, phoneNumber, email, address, totalPrice);

                    cartdb.deleteAllCartItems();
                    // Open order confirmation page
                    Intent intent = new Intent(Checkout.this, OrderConfirmationActivity.class);
                    intent.putExtra("order_id", orderId);
                    startActivity(intent);
                    finish(); // Close checkout activity
                }
            }
        });
    }
}
