package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Aboutus extends AppCompatActivity {
ImageView barrow11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aboutus);
        barrow11=findViewById(R.id.barrow1);
            barrow11.setOnClickListener(v -> {
                Intent sint = new Intent(Aboutus.this, homepage.class);
                startActivity(sint);
                finish();
            });


    }
}