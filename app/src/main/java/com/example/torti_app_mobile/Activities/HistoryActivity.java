package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Framents.DelieveriesFragment;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

public class HistoryActivity extends AppCompatActivity implements DelieveriesAdapter.OnDeliveryClickListener {
    private RecyclerView recyclerView = null;
    private int statusType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.statusType = getIntent().getIntExtra("type", 1);
        setContentView(R.layout.activity_history);
        this.recyclerView = findViewById(R.id.recyclerView);
        getAssignmentsCustomersFromServer();
    }

    private void getAssignmentsCustomersFromServer() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                api_url + "/assignment-customers", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Customer> customerList = new ArrayList<>();
                        try {
                            JSONArray assignmentsCustomer =
                                    response.getJSONArray("assignCustomer");
                            for (int i = 0; i < assignmentsCustomer.length(); i++) {
                                JSONObject assignment
                                        = assignmentsCustomer.getJSONObject(i);
                                JSONObject customerJson =
                                        assignment.getJSONObject("customers");
                                int id = customerJson.getInt("id");
                                String name = customerJson.getString("name");
                                String address = customerJson.getString("address");
                                String phone = customerJson.getString("phone");
                                double latitude = customerJson.getDouble("latitude");
                                double longitude = customerJson.getDouble("longitude");
                                Customer customer =
                                        new Customer(id, name, address, phone, latitude, longitude);
                                customerList.add(customer);
                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                            recyclerView.setAdapter(new DelieveriesAdapter(customerList, HistoryActivity.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " +
                        Auth.getAuth(HistoryActivity.this).getToken());
                return headers;
            }
        };
        VolleyS.getInstance(this).getQueue().add(request);
    }

    @Override
    public void onDeliveryClick(View v, Customer customer) {
    }

    @Override
    public void onItemClick(Customer customer) {
        Intent intent = new Intent(this, HistoryDetailsActivity.class);
        intent.putExtra("customerId", customer.getId());
        intent.putExtra("status", this.statusType);
        startActivity(intent);
    }
}
