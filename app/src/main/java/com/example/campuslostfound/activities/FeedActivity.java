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

        // Navigate directly to Item Details (example item)
        Button btnItemDetails = findViewById(R.id.btnItemDetails);
        btnItemDetails.setOnClickListener(v -> {
            Intent intent = new Intent(this, ItemDetailsActivity.class);

            // Pass example item details (Option 2: fields individually)
            intent.putExtra("item_id", 1);
            intent.putExtra("title", "Sample Item");
            intent.putExtra("category", "Wallet");
            intent.putExtra("location", "Library");
            intent.putExtra("date", "13/11/2025");
            intent.putExtra("description", "A sample lost wallet for demo purposes.");
            intent.putExtra("contact", "+260 123 456 789");
            intent.putExtra("photoUri", ""); // can put a URL if available
            intent.putExtra("status", "Not Returned");

            startActivity(intent);
        });
    }
}
