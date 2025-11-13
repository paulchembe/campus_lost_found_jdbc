package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuslostfound.R;
import com.example.campuslostfound.adapters.ItemAdapter;
import com.example.campuslostfound.db.DBHelper;
import com.example.campuslostfound.models.ItemPost;

import java.util.Collections;
import java.util.List;

public class LostFeedActivity extends AppCompatActivity {

    private RecyclerView recyclerLost;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_feed);

        recyclerLost = findViewById(R.id.recyclerLost);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);

        recyclerLost.setLayoutManager(new LinearLayoutManager(this));

        loadLostItems();
    }

    private void loadLostItems() {
        // Show loading
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        DBHelper.getItemsByType("LOST", items -> runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);

            if (items == null || items.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("No lost items found.");
                recyclerLost.setAdapter(new ItemAdapter(this, Collections.emptyList()));
            } else {
                tvEmpty.setVisibility(View.GONE);
                ItemAdapter adapter = new ItemAdapter(this, items);
                recyclerLost.setAdapter(adapter);

                // Handle item click
                adapter.setOnItemClickListener(item -> {
                    Intent intent = new Intent(LostFeedActivity.this, ItemDetailsActivity.class);
                    intent.putExtra("title", item.title);
                    intent.putExtra("category", item.category);
                    intent.putExtra("location", item.location);
                    intent.putExtra("date", item.date); // Use string or formatted date
                    intent.putExtra("description", item.description);
                    intent.putExtra("contact", item.contact);
                    intent.putExtra("photoUri", item.photoUri);
                    startActivity(intent);
                });
            }
        }));
    }
}
