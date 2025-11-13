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
                recyclerLost.setAdapter(new ItemAdapter(this, items));
            }
        }));
    }
}
