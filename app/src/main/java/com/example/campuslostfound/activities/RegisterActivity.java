package com.example.campuslostfound.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.db.DBHelper;

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

        btnReg.setOnClickListener(v-> {
            DBHelper.registerUserAsync(
                etFirst.getText().toString(),
                etLast.getText().toString(),
                etNrc.getText().toString(),
                etStudent.getText().toString(),
                etPhone.getText().toString(),
                etPassword.getText().toString(),
                (ok,msg) -> runOnUiThread(() -> {
                    if (ok) { Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show(); finish(); }
                    else Toast.makeText(this, "Failed: "+msg, Toast.LENGTH_LONG).show();
                })
            );
        });
    }
}
