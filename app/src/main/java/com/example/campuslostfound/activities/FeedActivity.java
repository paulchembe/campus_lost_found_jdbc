package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Navigate to LOST items feed
        Button btnLost = findViewById(R.id.btnLost);
        btnLost.setOnClickListener(v -> {
            Intent intent = new Intent(this, LostFeedActivity.class);
            startActivity(intent);
        });

        // Navigate to FOUND items feed
        Button btnFound = findViewById(R.id.btnFound);
        btnFound.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoundFeedActivity.class);
            startActivity(intent);
        });
    }
}
