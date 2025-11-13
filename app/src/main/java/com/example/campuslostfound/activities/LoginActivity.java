package com.example.campuslostfound.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.api.ApiClient;
import com.example.campuslostfound.db.DBHelper;

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
            String identifier = etIdentifier.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            DBHelper.loginAsync(identifier, password, (ok, msg, user) ->
                    runOnUiThread(() -> {
                        if (ok) {
                            SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            prefs.edit()
                                    .putInt("user_id", user.id)
                                    .apply();

                            Intent i = new Intent(this, DashboardActivity.class);
                            i.putExtra("user_id", user.id);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(this, "Login failed: " + msg, Toast.LENGTH_LONG).show();
                        }
                    })
            );
        });


        // Go to register page
        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}
