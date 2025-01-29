package com.example.sizzelcraft;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OrderConfirmationActivity extends AppCompatActivity {

    TextView txtOrderId, txtOrderDetails;
    Button btnTrackOrder;
    ImageView backarrow;
    OrderDatabaseHelper orderDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        txtOrderId = findViewById(R.id.txtOrderId);
        txtOrderDetails = findViewById(R.id.txtOrderDetails);
        btnTrackOrder = findViewById(R.id.Trackorder);
        orderDatabaseHelper = new OrderDatabaseHelper(this);
        backarrow = findViewById(R.id.backimg2);
backarrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(OrderConfirmationActivity.this,Checkout.class);
    }
});
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

        // Set button click listener to open Track Order Fragment
        btnTrackOrder.setOnClickListener(v -> openTrackOrderFragment());
    }

    private void openTrackOrderFragment() {
        LocFragment locFragment = new LocFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, locFragment); // Replace with the fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
