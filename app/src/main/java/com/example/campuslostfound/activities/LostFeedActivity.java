package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campuslostfound.R;
import com.example.campuslostfound.adapters.ItemAdapter;
import com.example.campuslostfound.db.DBHelper;
import java.util.Collections;

public class LostFeedActivity extends AppCompatActivity {

    RecyclerView recyclerLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_feed);

        recyclerLost = findViewById(R.id.recyclerLost);
        recyclerLost.setLayoutManager(new LinearLayoutManager(this));

        try {
            DBHelper.getItemsByTypeAsync("LOST", items -> runOnUiThread(() -> {
                if (items == null) {
                    Toast.makeText(this, "No lost items found.", Toast.LENGTH_SHORT).show();
                    recyclerLost.setAdapter(new ItemAdapter(this, Collections.emptyList()));
                } else {
                    recyclerLost.setAdapter(new ItemAdapter(this, items));
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading lost items", Toast.LENGTH_SHORT).show();
        }
    }
}
