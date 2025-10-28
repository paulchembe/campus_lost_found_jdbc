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

public class FoundFeedActivity extends AppCompatActivity {

    RecyclerView recyclerFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_feed);

        recyclerFound = findViewById(R.id.recyclerFound);
        recyclerFound.setLayoutManager(new LinearLayoutManager(this));

        try {
            DBHelper.getItemsByTypeAsync("FOUND", items -> runOnUiThread(() -> {
                if (items == null) {
                    Toast.makeText(this, "No found items yet.", Toast.LENGTH_SHORT).show();
                    recyclerFound.setAdapter(new ItemAdapter(this, Collections.emptyList()));
                } else {
                    recyclerFound.setAdapter(new ItemAdapter(this, items));
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading found items", Toast.LENGTH_SHORT).show();
        }
    }
}
