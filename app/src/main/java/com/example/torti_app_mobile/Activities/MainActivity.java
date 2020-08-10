package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.R;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Auth.getAuth(getApplicationContext()) != null) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    Log.d("Tag:hasToken", "True");
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Log.d("Tag:hasToken", "False");
                }
            }
        }, 2000);
    }
}