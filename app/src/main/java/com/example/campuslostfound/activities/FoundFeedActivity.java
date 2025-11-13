package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuslostfound.R;
import com.example.campuslostfound.adapters.ItemAdapter;
import com.example.campuslostfound.db.DBHelper;
import com.example.campuslostfound.models.ItemPost;

import java.util.Collections;
import java.util.List;

public class FoundFeedActivity extends AppCompatActivity {

    private RecyclerView recyclerFound;
    private ProgressBar progressBarFound;
    private TextView tvEmptyFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_feed);

        // Initialize UI elements
        recyclerFound = findViewById(R.id.recyclerFound);
        progressBarFound = findViewById(R.id.progressBarFound);
        tvEmptyFound = findViewById(R.id.tvEmptyFound);

        recyclerFound.setLayoutManager(new LinearLayoutManager(this));

        // Load found items
        loadFoundItems();
    }

    private void loadFoundItems() {
        // Show loading spinner
        progressBarFound.setVisibility(View.VISIBLE);
        tvEmptyFound.setVisibility(View.GONE);

        DBHelper.getItemsByType("FOUND", items -> runOnUiThread(() -> {
            progressBarFound.setVisibility(View.GONE);

            if (items == null || items.isEmpty()) {
                // Show empty message if no items are found
                tvEmptyFound.setVisibility(View.VISIBLE);
                tvEmptyFound.setText("No found items yet.");
                recyclerFound.setAdapter(new ItemAdapter(this, Collections.emptyList()));
            } else {
                // Display items in the RecyclerView
                tvEmptyFound.setVisibility(View.GONE);
                recyclerFound.setAdapter(new ItemAdapter(this, items));
            }
        }));
    }
}
