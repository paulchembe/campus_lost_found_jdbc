package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btnFeed).setOnClickListener(v-> startActivity(new Intent(this, com.example.campuslostfound.activities.FeedActivity.class)));
        findViewById(R.id.btnPost).setOnClickListener(v-> startActivity(new Intent(this, com.example.campuslostfound.activities.PostItemActivity.class)));
        findViewById(R.id.btnAbout).setOnClickListener(v-> Toast.makeText(this, "About screen not implemented in scaffold", Toast.LENGTH_SHORT).show());
    }
}
