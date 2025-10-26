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
import com.example.campuslostfound.api.ApiClient;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
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

// ✅ Set the background color
        webView.setBackgroundColor(0xFFA0B6CC); // ARGB: FF = fully opaque
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null); // ensures color renders
        webView.getSettings().setJavaScriptEnabled(true);


        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // ✅ Register button action
        btnReg.setOnClickListener(v -> {
            new Thread(() -> {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", etFirst.getText().toString());
                params.put("last_name", etLast.getText().toString());
                params.put("nrc", etNrc.getText().toString());
                params.put("student_no", etStudent.getText().toString());
                params.put("phone", etPhone.getText().toString());
                params.put("password", etPassword.getText().toString());

                String response = ApiClient.post("register.php", params);

                runOnUiThread(() -> {
                    webView.loadDataWithBaseURL(null, response, "text/html", "UTF-8", null);

                    // Navigate to login if registration successful
                    if (response.contains("Registration Successful")) {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else if (response.contains("Registration Failed")) {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        // ✅ Back button action: always return to login
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
