package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bt=findViewById(R.id.mainb1);
        bt.setOnClickListener(v -> {
            Intent maini=new Intent(MainActivity.this,signin.class);
            startActivity(maini);
        });



    }
}