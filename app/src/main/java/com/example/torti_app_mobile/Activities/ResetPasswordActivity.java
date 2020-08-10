package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.R;

import org.json.JSONException;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView txt_password, txt_new_password;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.inicializeElemets();
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (txt_password.getText().length() > 0 &&  txt_new_password.getText().length() > 0) {
                        Auth.resetPassword(
                                txt_password.getText().toString(),
                                txt_new_password.getText().toString(),
                                getApplicationContext(),
                                ResetPasswordActivity.this);
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void inicializeElemets() {
        txt_password = findViewById(R.id.txt_password);
        txt_new_password = findViewById(R.id.txt_new_pass);
        btn_reset = findViewById(R.id.btn_reset);
    }
}