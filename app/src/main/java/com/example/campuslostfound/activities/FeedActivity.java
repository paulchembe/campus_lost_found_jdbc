package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;

public class FeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_feed);
        // For simplicity this scaffold uses two buttons instead of full TabLayout
        findViewById(R.id.btnLost).setOnClickListener(v-> Toast.makeText(this, "Show lost list (implement RecyclerView)", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnFound).setOnClickListener(v-> Toast.makeText(this, "Show found list (implement RecyclerView)", Toast.LENGTH_SHORT).show());
    }
}
