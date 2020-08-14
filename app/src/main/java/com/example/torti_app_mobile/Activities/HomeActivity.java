package com.example.torti_app_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.torti_app_mobile.Framents.DelieveriesFragment;
import com.example.torti_app_mobile.Framents.HistoryFragment;
import com.example.torti_app_mobile.Framents.ProductsAssigmentFragment;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation_view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottom_navigation_view = findViewById(R.id.bottom_navigation);
        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_notify:
                Toast.makeText(this, "Notificar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_reset_pass:
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                break;
            case R.id.item_logout:
                try {
                    Auth.logout(getApplicationContext(), HomeActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}