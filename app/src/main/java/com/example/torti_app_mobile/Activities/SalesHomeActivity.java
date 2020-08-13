package com.example.torti_app_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.torti_app_mobile.Framents.DelieveriesFragment;
import com.example.torti_app_mobile.Framents.HistoryFragment;
import com.example.torti_app_mobile.Framents.LostProductFragment;
import com.example.torti_app_mobile.Framents.PendingFragment;
import com.example.torti_app_mobile.Framents.ProductsAssigmentFragment;
import com.example.torti_app_mobile.Framents.SalesFragment;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SalesHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_home);

        bottom_navigation_view = findViewById(R.id.bottom_navigation_sales);
        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_sale:
                        openFragment(SalesFragment.newInstance("", ""));
                        return true;
                    case R.id.item_pending:
                        openFragment(PendingFragment.newInstance("", ""));
                        return true;
                    case R.id.item_lost_products:
                        openFragment(LostProductFragment.newInstance("", ""));
                        return true;
                }

                return false;
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}