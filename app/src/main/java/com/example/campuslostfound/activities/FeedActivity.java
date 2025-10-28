package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.campuslostfound.R;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_feed);

        findViewById(R.id.btnLost).setOnClickListener(v -> {
            startActivity(new Intent(this, LostFeedActivity.class));
        });

        findViewById(R.id.btnFound).setOnClickListener(v -> {
            startActivity(new Intent(this, FoundFeedActivity.class));
        });
    }
}
