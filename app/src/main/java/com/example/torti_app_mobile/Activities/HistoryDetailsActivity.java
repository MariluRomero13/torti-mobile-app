package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.torti_app_mobile.R;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        int status = getIntent().getIntExtra("status", 1);
        int customerId = getIntent().getIntExtra("customerId", 1);
        getHistoryDetailsFromServer(status, customerId);
    }

    private void getHistoryDetailsFromServer(int status, int customerId) {

    }
}
