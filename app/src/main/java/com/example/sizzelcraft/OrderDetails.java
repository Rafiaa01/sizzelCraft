package com.example.sizzelcraft;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderDetails extends AppCompatActivity {

    TextView txtOrderId, txtOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        txtOrderId = findViewById(R.id.OrderId);
        txtOrderDetails = findViewById(R.id.OrderDetails);

        // Get order details from intent
        long orderId = getIntent().getLongExtra("order_id", -1);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        double totalPrice = getIntent().getDoubleExtra("total_price", 0.0);

        if (orderId == -1 || name == null) {
            Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display order details
        txtOrderId.setText("Order ID: #" + orderId);
        txtOrderDetails.setText("Name: " + name + "\nPhone: " + phone + "\nEmail: " + email +
                "\nAddress: " + address + "\nTotal Price: $" + String.format("%.2f", totalPrice));
    }
}