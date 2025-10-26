package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.api.ApiClient;
import com.example.campuslostfound.db.DBHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etFirst, etLast, etNrc, etStudent, etPhone, etPassword;
    Button btnReg;
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_register);
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etNrc = findViewById(R.id.etNrc);
        etStudent = findViewById(R.id.etStudent);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.btnRegister);


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
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("success")) {
                            Toast.makeText(this, "Registered OK", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });


    }
}
