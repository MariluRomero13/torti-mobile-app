package com.example.torti_app_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Framents.DelieveriesFragment;
import com.example.torti_app_mobile.Framents.HistoryFragment;
import com.example.torti_app_mobile.Framents.ProductsAssigmentFragment;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

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
                openDialogForNotification();
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

    private void openDialogForNotification() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_notification);
        final EditText edtNotification = dialog.findViewById(R.id.edtDescription);
        final Button btnSendNotification = dialog.findViewById(R.id.btnSendNotification);

        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String notification = edtNotification.getText().toString();
                if(notification.trim().length() > 0) {
                    sendNotificationToServer(notification, dialog);
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Por favor ingrese una notificación",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void sendNotificationToServer(String notification, final Dialog dialog) {
        JSONObject data = new JSONObject();
        try {
            data.put("description", notification);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    api_url + "/notification", data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dialog.dismiss();
                            Toast.makeText(HomeActivity.this, "Notificación enviada", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "bearer " + Auth.getAuth(HomeActivity.this).getToken() );
                    return headers;
                }
            };
            VolleyS.getInstance(this).getQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}