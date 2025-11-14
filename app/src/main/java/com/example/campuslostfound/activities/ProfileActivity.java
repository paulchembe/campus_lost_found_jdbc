package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.campuslostfound.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView imgProfile = findViewById(R.id.imgProfile);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);

        // These will later be fetched from logged-in user data
        tvUsername.setText("Joseph B. Kasanga");
        tvEmail.setText("joseph.kasanga@example.com");
    }
}
