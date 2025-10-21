package com.example.campuslostfound.activities;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;
import com.example.campuslostfound.model.ItemPost;

import java.util.Calendar;

public class PostItemActivity extends AppCompatActivity {
    EditText etTitle, etDesc, etCategory, etLocation, etContact;
    Button btnDate, btnSubmit;
    long selectedDate = System.currentTimeMillis();

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

        btnDate.setOnClickListener(v-> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (dp,y,m,d)-> {
                Calendar sel = Calendar.getInstance(); sel.set(y,m,d);
                selectedDate = sel.getTimeInMillis();
                btnDate.setText(d+"/"+(m+1)+"/"+y);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnSubmit.setOnClickListener(v-> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            if (title.isEmpty() && desc.length() < 30) { Toast.makeText(this, "Provide title or description ≥ 30 chars", Toast.LENGTH_LONG).show(); return; }
            ItemPost p = new ItemPost();
            p.type = "LOST"; // for scaffold; add toggle in production
            p.title = title; p.description = desc; p.category = etCategory.getText().toString(); p.location = etLocation.getText().toString(); p.date = selectedDate; p.contact = etContact.getText().toString();
            DBHelper.createItemAsync(p, (ok,id)-> runOnUiThread(() -> {
                if (ok) { Toast.makeText(this, "Posted (id="+id+")", Toast.LENGTH_SHORT).show(); finish(); }
                else Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show();
            }));
        });
    }
}
