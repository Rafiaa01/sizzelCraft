package com.example.sizzelcraft;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {

    TextView txtOrderId, txtOrderDetails;
    OrderDatabaseHelper orderDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        txtOrderId = findViewById(R.id.txtOrderId);
        txtOrderDetails = findViewById(R.id.txtOrderDetails);
        orderDatabaseHelper = new OrderDatabaseHelper(this);

        // Get order ID from intent
        long orderId = getIntent().getLongExtra("order_id", -1);

        if (orderId == -1) {
            Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch order details from database
        Cursor cursor = orderDatabaseHelper.getOrderById(orderId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));

            // Display order details
            txtOrderId.setText("Order ID: #" + orderId);
            txtOrderDetails.setText("Name: " + name + "\nPhone: " + phone + "\nEmail: " + email +
                    "\nAddress: " + address + "\nTotal Price: $" + String.format("%.2f", totalPrice));

            cursor.close();
        } else {
            Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
