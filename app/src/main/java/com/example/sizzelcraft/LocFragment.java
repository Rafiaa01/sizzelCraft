package com.example.sizzelcraft;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gmap;
    private EditText edtName;
    private Button btnViewOrder;
    private OrderDatabaseHelper orderDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loc, container, false);

        // Initialize views
        edtName = view.findViewById(R.id.txtOrderId);
        btnViewOrder = view.findViewById(R.id.Trackorder);
        orderDatabaseHelper = new OrderDatabaseHelper(getContext());

        // Handle "View Order Details" button click
        btnViewOrder.setOnClickListener(v -> viewOrderDetails());

        // Initialize Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    private void viewOrderDetails() {
        String enteredName = edtName.getText().toString().trim();

        if (enteredName.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the database to check if the name exists
        Cursor cursor = orderDatabaseHelper.getOrderByName(enteredName);
        if (cursor != null && cursor.moveToFirst()) {
            // Get order details
            long orderId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));

            cursor.close();

            // Start OrderConfirmationActivity with order details
            Intent intent = new Intent(getContext(), OrderDetails.class);
            intent.putExtra("order_id", orderId);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("email", email);
            intent.putExtra("address", address);
            intent.putExtra("total_price", totalPrice);
            startActivity(intent);

        } else {
            Toast.makeText(getContext(), "No order found for this name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;

        // Enable location if permissions are granted
        Context context = getContext();
        if (context != null && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gmap.setMyLocationEnabled(true);
        } else {
            // Request permissions if not already granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Add a marker at Pakistan's coordinates and move the camera
        LatLng pakistan = new LatLng(30.3753, 69.3451);
        gmap.addMarker(new MarkerOptions().position(pakistan).title("Pakistan"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(pakistan, 10));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Context context = getContext();
            if (context != null && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (gmap != null) {
                    gmap.setMyLocationEnabled(true);
                }
            }
        }
    }
}
