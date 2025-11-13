package com.example.campuslostfound.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;
import com.example.campuslostfound.models.ItemPost;

import java.util.Calendar;

public class PostItemActivity extends AppCompatActivity {

    EditText etTitle, etDesc, etCategory, etLocation, etContact;
    Button btnDate, btnSubmit, btnPickImage;
    ImageView imgPreview;
    RadioGroup rgStatus;
    long selectedDate = System.currentTimeMillis();
    Uri photoUri = null;
    String selectedType = "LOST";

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_post_item);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        etCategory = findViewById(R.id.etCategory);
        etLocation = findViewById(R.id.etLocation);
        etContact = findViewById(R.id.etContact);
        btnDate = findViewById(R.id.btnDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnPickImage = findViewById(R.id.btnPickImage);
        imgPreview = findViewById(R.id.imgPreview);
        rgStatus = findViewById(R.id.rgStatus);

        // Image picker setup
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        photoUri = result.getData().getData();
                        imgPreview.setImageURI(photoUri);
                    }
                }
        );

        btnPickImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickIntent);
        });

        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLost) selectedType = "LOST";
            else if (checkedId == R.id.rbFound) selectedType = "FOUND";
        });

        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (dp, y, m, d) -> {
                // Display friendly date on button
                String displayDate = d + "/" + (m + 1) + "/" + y;
                btnDate.setText(displayDate);

                // Save date in YYYY-MM-DD format for PHP/MySQL
                selectedDate = y * 10000 + (m + 1) * 100 + d; // optional numeric representation
                String itemDateForPhp = String.format("%04d-%02d-%02d", y, m + 1, d); // "2025-11-13"
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });


        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            if (title.isEmpty() || desc.length() < 30) {
                Toast.makeText(this, "Provide title and description ≥ 30 chars", Toast.LENGTH_LONG).show();
                return;
            }

            // Convert selectedDate to MySQL DATE format yyyy-MM-dd
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(selectedDate);
            String formattedDate = String.format("%04d-%02d-%02d",
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));

            ItemPost p = new ItemPost();
            p.type = selectedType;
            p.title = title;
            p.description = desc;
            p.category = etCategory.getText().toString();
            p.location = etLocation.getText().toString();
            p.date = formattedDate; // ✅ Correct format
            p.contact = etContact.getText().toString();
            p.photoUri = (photoUri != null) ? photoUri.toString() : null;

            DBHelper.createItem(p, (ok, msg, id) -> runOnUiThread(() -> {
                if (ok) {
                    Toast.makeText(this, "Posted under " + selectedType, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to post: " + msg, Toast.LENGTH_LONG).show();
                }
            }));
        });
    }
}
