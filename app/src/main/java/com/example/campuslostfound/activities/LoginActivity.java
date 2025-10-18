package com.example.campuslostfound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;

public class LoginActivity extends AppCompatActivity {
    EditText etIdentifier, etPassword;
    Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_login);
        etIdentifier = findViewById(R.id.etIdentifier);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v-> {
            String id = etIdentifier.getText().toString().trim();
            String pw = etPassword.getText().toString().trim();
            if (id.isEmpty() || pw.isEmpty()) { Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show(); return; }
            DBHelper.loginUserAsync(id, pw, (ok,msg) -> {
                if (ok) {
                    // go to dashboard
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Login ok", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Login failed: "+msg, Toast.LENGTH_LONG).show());
                }
            });
        });

        btnRegister.setOnClickListener(v-> startActivity(new Intent(this, RegisterActivity.class)));
    }
}
