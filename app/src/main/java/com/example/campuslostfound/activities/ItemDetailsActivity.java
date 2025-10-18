package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;

public class ItemDetailsActivity extends AppCompatActivity {
    Button btnMarkReturned, btnMatches;
    long itemId = 0;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_item_details);
        btnMarkReturned = findViewById(R.id.btnMarkReturned);
        btnMatches = findViewById(R.id.btnMatches);

        btnMarkReturned.setOnClickListener(v-> DBHelper.markReturnedAsync(itemId, (ok,msg)-> runOnUiThread(() -> {
            if (ok) Toast.makeText(this, "Marked returned", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Failed: "+msg, Toast.LENGTH_SHORT).show();
        })));

        btnMatches.setOnClickListener(v-> startActivity(new android.content.Intent(this, MatchesActivity.class)));
    }
}
