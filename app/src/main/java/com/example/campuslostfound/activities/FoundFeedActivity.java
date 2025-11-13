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

public class FoundFeedActivity extends AppCompatActivity {

    private RecyclerView recyclerFound;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_feed);

        recyclerFound = findViewById(R.id.recyclerFound);
        progressBar = findViewById(R.id.progressBarFound);
        tvEmpty = findViewById(R.id.tvEmptyFound);

        recyclerFound.setLayoutManager(new LinearLayoutManager(this));

        loadFoundItems();
    }

    private void loadFoundItems() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        DBHelper.getItemsByType("FOUND", items -> runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);

            if (items == null || items.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("No found items yet.");
                recyclerFound.setAdapter(new ItemAdapter(this, Collections.emptyList()));
            } else {
                tvEmpty.setVisibility(View.GONE);
                ItemAdapter adapter = new ItemAdapter(this, items);
                recyclerFound.setAdapter(adapter);

                // Handle item clicks
                adapter.setOnItemClickListener(item -> {
                    Intent intent = new Intent(FoundFeedActivity.this, ItemDetailsActivity.class);
                    intent.putExtra("title", item.title);
                    intent.putExtra("category", item.category);
                    intent.putExtra("location", item.location);
                    intent.putExtra("date", item.date); // formatted or raw
                    intent.putExtra("description", item.description);
                    intent.putExtra("contact", item.contact);
                    intent.putExtra("photoUri", item.photoUri);
                    startActivity(intent);
                });
            }
        }));
    }
}
