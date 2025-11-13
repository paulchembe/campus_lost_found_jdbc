package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Navigate to Feed screen
        findViewById(R.id.btnFeed).setOnClickListener(v ->
                startActivity(new Intent(this, FeedActivity.class)));

        // Navigate to Post Item screen
        findViewById(R.id.btnPost).setOnClickListener(v ->
                startActivity(new Intent(this, PostItemActivity.class)));

        // Navigate to Matches screen
        findViewById(R.id.btnMatches).setOnClickListener(v ->
                startActivity(new Intent(this, MatchesActivity.class)));

        // Navigate to About Us screen
        findViewById(R.id.btnAbout).setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));
    }
}
