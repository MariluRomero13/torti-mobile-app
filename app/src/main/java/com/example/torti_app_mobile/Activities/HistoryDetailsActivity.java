package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.torti_app_mobile.Adapters.DevolutionAdapter;
import com.example.torti_app_mobile.Adapters.HistoryAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.History;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

public class HistoryDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView = null;
    private TextView txvTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        this.txvTitle = findViewById(R.id.txvTitle);
        int status = getIntent().getIntExtra("status", 1);
        int customerId = getIntent().getIntExtra("customerId", 1);
        this.recyclerView = findViewById(R.id.recyclerView);
        if(status == History.SALE_PENDING || status == History.SALE_COMPLETED){
            getHistoryDetailsFromServer(status, customerId);
            if (status == History.SALE_PENDING)
                txvTitle.setText(R.string.label_sale_pending);
        } else if(status == History.LOST_PRODUCT) {
            txvTitle.setText(R.string.label_lost_products);
            getLostProducts(customerId);
        }

    }

    private void getHistoryDetailsFromServer(final int status, final int customerId) {
        JSONObject data = new JSONObject();
        try {
            data.put("customer_id", customerId);
            data.put("status", status);
            final String requestBody = data.toString();
            StringRequest request = new StringRequest(Request.Method.POST, api_url + "/sales-histories",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response", response);
                            if(!response.isEmpty()) {
                                List<History> historiesList = new ArrayList<>();
                                try {
                                    JSONArray histories = new JSONArray(response);
                                    for (int i = 0; i < histories.length(); i++) {
                                        JSONObject historyJson = histories.getJSONObject(i);
                                        String totalSale = historyJson.getString("total");
                                        String credit = historyJson.getString("credit");
                                        String totalToPay = historyJson.getString("total_to_pay");
                                        int status = historyJson.getInt("status");
                                        JSONArray detailsJson = historyJson.getJSONArray("details");
                                        List<Product> details = new ArrayList<>();
                                        for (int j = 0; j < detailsJson.length(); j++) {
                                            JSONObject productJson
                                                    = detailsJson.getJSONObject(i);
                                            int quantity = productJson.getInt("quantity");
                                            String total = productJson.getString("total");
                                            String productName = productJson
                                                    .getJSONObject("product")
                                                    .getString("name");
                                            String price = productJson
                                                    .getJSONObject("product")
                                                    .getString("unit_price");
                                            Product product = new Product(productName, price, quantity);
                                            details.add(product);
                                        }

                                        History history =
                                                new History(status, totalSale, credit, totalToPay, details);
                                        historiesList.add(history);
                                    }

                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(
                                                    HistoryDetailsActivity.this));
                                    recyclerView.setAdapter(new HistoryAdapter(historiesList));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "bearer " +
                            Auth.getAuth(HistoryDetailsActivity.this).getToken());
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            VolleyS.getInstance(this).getQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLostProducts(int customerId) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                api_url + "/devolution/" + customerId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response", response.toString());
                        List<Devolution> devolutions = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String quantity = object.getString("quantity");
                                String description = object.getString("description");
                                JSONObject productObject = object.getJSONObject("product");
                                String productName = productObject.getString("name");
                                String productPrice = productObject.getString("unit_price");
                                Product product = new Product(productName, productPrice);
                                Devolution devolution = new Devolution(quantity, description, product);
                                devolutions.add(devolution);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(
                                HistoryDetailsActivity.this));
                        recyclerView.setAdapter(new DevolutionAdapter(devolutions));
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
                        Auth.getAuth(HistoryDetailsActivity.this).getToken());
                return headers;
            }
        };
        VolleyS.getInstance(this).getQueue().add(request);
    }
}
