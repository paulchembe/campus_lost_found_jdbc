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
                Calendar sel = Calendar.getInstance();
                sel.set(y, m, d);
                selectedDate = sel.getTimeInMillis();
                btnDate.setText(d + "/" + (m + 1) + "/" + y);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            if (title.isEmpty() || desc.length() < 30) {
                Toast.makeText(this, "Provide title and description ≥ 30 chars", Toast.LENGTH_LONG).show();
                return;
            }

            ItemPost p = new ItemPost();
            p.type = selectedType;
            p.title = title;
            p.description = desc;
            p.category = etCategory.getText().toString();
            p.location = etLocation.getText().toString();
            p.date = selectedDate;
            p.contact = etContact.getText().toString();
            p.photoUri = (photoUri != null) ? photoUri.toString() : null;

            DBHelper.createItemAsync(p, (ok, id) -> runOnUiThread(() -> {
                if (ok) {
                    Toast.makeText(this, "Posted under " + selectedType, Toast.LENGTH_SHORT).show();
                    finish(); // Just close this screen, don't navigate anywhere
                } else {
                    Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show();
                }
            }));
        });
    }
}
