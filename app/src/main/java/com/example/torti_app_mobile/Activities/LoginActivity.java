package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.R;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private TextView txt_email, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initializeElements();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (txt_email.getText().length() > 0  && txt_password.getText().length() > 0) {
                        Auth.login(txt_email.getText().toString(), txt_password.getText().toString(), LoginActivity.this);
                    } else {
                        Toast.makeText(LoginActivity.this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeElements() {
        btn_login = findViewById(R.id.btn_login);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
    }
}