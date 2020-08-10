package com.example.torti_app_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.torti_app_mobile.Framents.DelieveriesFragment;
import com.example.torti_app_mobile.Framents.HistoryFragment;
import com.example.torti_app_mobile.Framents.ProductsAssigmentFragment;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_navigation_view = findViewById(R.id.bottom_navigation);
        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_deliveries:
                        openFragment(DelieveriesFragment.newInstance("", ""));
                        return true;
                    case R.id.item_product_a:
                        openFragment(ProductsAssigmentFragment.newInstance("", ""));
                        return true;
                    case R.id.item_history:
                        openFragment(HistoryFragment.newInstance("", ""));
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