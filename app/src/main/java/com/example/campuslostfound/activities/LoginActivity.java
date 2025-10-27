package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.api.ApiClient;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText etIdentifier, etPassword;
    Button btnLogin, btnRegister;
    WebView webView; // Optional: to display messages

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etIdentifier = findViewById(R.id.etIdentifier);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // Set WebView background color
        webView.setBackgroundColor(0xFFA0B6C); // ARGB: FF = fully opaque
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null); // ensures background renders


        //Login button action
        btnLogin.setOnClickListener(v -> {
            String id = etIdentifier.getText().toString().trim();
            String pw = etPassword.getText().toString().trim();

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Map<String, String> params = new HashMap<>();
                params.put("identifier", id);
                params.put("password", pw);

                String response = ApiClient.post("login.php", params);

                runOnUiThread(() -> {
                    // Display HTML message in WebView
                    webView.loadDataWithBaseURL(null, response, "text/html", "UTF-8", null);

                    // Detect "Login Successful" in the HTML response
                    if (response.contains("Login Successful")) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish(); // close login activity
                    } else if (response.contains("Login Failed")) {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        // ✅ Go to register page
        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}
