package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;

public class
RegisterActivity extends AppCompatActivity {
    EditText etFirst, etLast, etNrc, etStudent, etPhone, etPassword;
    Button btnReg;
    WebView webView;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etNrc = findViewById(R.id.etNrc);
        etStudent = findViewById(R.id.etStudent);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btn_back);
        webView = findViewById(R.id.webView);

        // Optional: simple UI background setup
        webView.setBackgroundColor(0xFFFAFAFA);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptEnabled(true);

        // ✅ Register button action
        btnReg.setOnClickListener(v -> {
            String fn = etFirst.getText().toString().trim();
            String ln = etLast.getText().toString().trim();
            String nrc = etNrc.getText().toString().trim();
            String studNo = etStudent.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            // Validation
            if (fn.isEmpty() || ln.isEmpty() || nrc.isEmpty() ||
                    studNo.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🟢 Call DBHelper.registerUser(...) instead of ApiClient
            DBHelper.registerUser(fn, ln, nrc, studNo, phone, pass, (ok, msg) -> runOnUiThread(() -> {
                if (ok) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Return to login screen
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Registration failed: " + msg, Toast.LENGTH_LONG).show();
                }
            }));
        });

        // ✅ Back button action
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
