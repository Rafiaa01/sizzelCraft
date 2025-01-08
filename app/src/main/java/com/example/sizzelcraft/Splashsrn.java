package com.example.sizzelcraft;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Splashsrn extends AppCompatActivity {

    private TextView splashText;
    private String fullText = "Sizzle Craft!";
    private int textIndex = 0;
    private long delay = 150; // Delay in milliseconds for each character
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsrn);

        // Initialize the TextView
        splashText = findViewById(R.id.ff);

        // Clear the text to ensure no duplicate content
        splashText.setText("");

        // Start the typewriter effect
        startTypewriterAnimation();

        // Delay before transitioning to the main activity
        new Handler().postDelayed(() -> {
            // Create an Intent to move to the Main Activity
            Intent intent = new Intent(Splashsrn.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the splash screen so the user cannot return to it
        }, fullText.length() * delay + 1000); // Ensure enough time for the animation
    }

    private void startTypewriterAnimation() {
        handler.postDelayed(typewriterRunnable, delay);
    }

    private Runnable typewriterRunnable = new Runnable() {
        @Override
        public void run() {
            if (textIndex < fullText.length()) {
                splashText.setText(fullText.substring(0, textIndex + 1)); // Display only the current portion
                textIndex++;
                handler.postDelayed(this, delay); // Continue the animation
            }
        }
    };
}
