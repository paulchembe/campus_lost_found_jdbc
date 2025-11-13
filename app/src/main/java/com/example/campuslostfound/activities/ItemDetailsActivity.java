package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        ImageView imgItem = findViewById(R.id.imgItemDetail);
        TextView tvTitle = findViewById(R.id.tvTitleDetail);
        TextView tvCategory = findViewById(R.id.tvCategoryDetail);
        TextView tvLocation = findViewById(R.id.tvLocationDetail);
        TextView tvDate = findViewById(R.id.tvDateDetail);
        TextView tvDescription = findViewById(R.id.tvDescriptionDetail);
        TextView tvContact = findViewById(R.id.tvContactDetail);

        // Retrieve extras individually
        String title = getIntent().getStringExtra("title");
        String category = getIntent().getStringExtra("category");
        String location = getIntent().getStringExtra("location");
        long dateMillis = getIntent().getLongExtra("date", 0);
        String description = getIntent().getStringExtra("description");
        String contact = getIntent().getStringExtra("contact");
        String photoUri = getIntent().getStringExtra("photoUri");

        // Set values to views
        if (title != null) tvTitle.setText(title);
        if (category != null) tvCategory.setText("Category: " + category);
        if (location != null) tvLocation.setText("Location: " + location);
        if (description != null) tvDescription.setText(description);
        if (contact != null) tvContact.setText("Contact: " + contact);

        if (dateMillis != 0) {
            Date date = new Date(dateMillis);
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
            tvDate.setText("Date: " + formattedDate);
        }

        if (photoUri != null && !photoUri.isEmpty()) {
            Picasso.get().load(photoUri)
                    .placeholder(R.drawable.ic_camera_placeholder)
                    .into(imgItem);
        } else {
            imgItem.setImageResource(R.drawable.ic_camera_placeholder);
        }
    }
}
