package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuslostfound.R;
import com.example.campuslostfound.adapters.ItemAdapter;
import com.example.campuslostfound.db.DBHelper;
import com.example.campuslostfound.models.ItemPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerMatches;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    private List<ItemPost> allItems = new ArrayList<>(); // All lost + found items
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        searchView = findViewById(R.id.searchViewMatches);
        recyclerMatches = findViewById(R.id.recyclerMatches);
        progressBar = findViewById(R.id.progressBarMatches);
        tvEmpty = findViewById(R.id.tvEmptyMatches);

        recyclerMatches.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this, new ArrayList<>());
        recyclerMatches.setAdapter(adapter);

        loadAllItems();

        setupSearch();
    }

    private void loadAllItems() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        // Fetch Lost items first
        DBHelper.getItemsByType("LOST", lostItems -> runOnUiThread(() -> {
            if (lostItems != null) allItems.addAll(lostItems);

            // Then fetch Found items
            DBHelper.getItemsByType("FOUND", foundItems -> runOnUiThread(() -> {
                if (foundItems != null) allItems.addAll(foundItems);

                progressBar.setVisibility(View.GONE);

                if (allItems.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("No items found in the database.");
                    adapter = new ItemAdapter(this, Collections.emptyList());
                    recyclerMatches.setAdapter(adapter);
                } else {
                    adapter = new ItemAdapter(this, allItems);
                    recyclerMatches.setAdapter(adapter);
                }
            }));
        }));
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItems(newText);
                return true;
            }
        });
    }

    private void searchItems(String query) {
        if (allItems.isEmpty()) return;

        List<ItemPost> filtered = new ArrayList<>();
        String q = query.trim().toLowerCase();

        for (ItemPost item : allItems) {
            if ((item.title != null && item.title.toLowerCase().contains(q)) ||
                    (item.description != null && item.description.toLowerCase().contains(q)) ||
                    (item.category != null && item.category.toLowerCase().contains(q)) ||
                    (item.location != null && item.location.toLowerCase().contains(q))) {
                filtered.add(item);
            }
        }

        if (filtered.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("No matching items found.");
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        adapter = new ItemAdapter(this, filtered);
        recyclerMatches.setAdapter(adapter);
    }
}
