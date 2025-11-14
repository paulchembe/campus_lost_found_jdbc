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

        findViewById(R.id.btnFeed).setOnClickListener(v ->
                startActivity(new Intent(this, FeedActivity.class)));

        findViewById(R.id.btnPost).setOnClickListener(v ->
                startActivity(new Intent(this, PostItemActivity.class)));

        findViewById(R.id.btnMatches).setOnClickListener(v ->
                startActivity(new Intent(this, MatchesActivity.class)));

        findViewById(R.id.btnAbout).setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        findViewById(R.id.btnProfile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }
}
