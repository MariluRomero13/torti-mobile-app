package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Adapters.LiquidateSaleAdapter;
import com.example.torti_app_mobile.Adapters.ProductAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

public class LiquidateSaleActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private Button btn_detail;
    private TextView txt_pay, txt_total_to_pay, txt_abonado, txt_total;
    private int customer_id;
    private ConstraintLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquidate_sale);
        customer_id = getIntent().getIntExtra("customerId", 0);
        this.initializeElements();
        this.loadBottomSheet();
        this.getPendingProduct();
    }

    private void initializeElements() {
        recyclerView = findViewById(R.id.r_delivieries);
        btn_detail = findViewById(R.id.btn_detail_liquidate_sale);
        txt_total = findViewById(R.id.txt_total);
        txt_total_to_pay = findViewById(R.id.txt_total_to_pay);
        txt_abonado = findViewById(R.id.txt_abonado);
        view = findViewById(R.id.view);
    }

    private void loadBottomSheet() {
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        LiquidateSaleActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_liquidate_sale,
                                (ViewGroup) findViewById(R.id.bottom_sheet_pp)
                        );
                txt_pay = bottomSheetView.findViewById(R.id.txt_quantity_to_pay);

                bottomSheetView.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        liquidateSale();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void getPendingProduct() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                api_url + "/get-pending-products/" + customer_id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG:PendingProducts", response.toString());
                        List<Product> productList = new ArrayList<>();
                        try {
                            JSONArray products = response.getJSONArray("products");
                            if (products.length() > 0) {
                                for (int i = 0; i < products.length(); i++) {
                                    JSONObject element = products.getJSONObject(i);
                                    String name_product = element.getString("name");
                                    String total_product = element.getString("total_product");
                                    int quantity_product = element.getInt("quantity");
                                    Product product = new Product(name_product, total_product, quantity_product);
                                    productList.add(product);

                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(LiquidateSaleActivity.this));
                                    recyclerView.setAdapter(new LiquidateSaleAdapter(productList));
                                }
                                JSONObject element = products.getJSONObject(0);
                                txt_total.setText(String.format("Total: $%s", element.getString("total")));
                                txt_total_to_pay.setText(String.format("Total a pagar: $%s", element.getString("total_to_pay")));
                                txt_abonado.setText(String.format("Abonado: $%s", element.getString("credit")));
                            } else {
                                view.setVisibility(View.VISIBLE);
                                btn_detail.setVisibility(view.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG:PendingProducts-e", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " +
                        Auth.getAuth(LiquidateSaleActivity.this).getToken());
                return headers;
            }

        };
        VolleyS.getInstance(this).getQueue().add(request);
    }

    private void liquidateSale() {
        try {
            JSONObject params= new JSONObject();
            params.put("customer_id", customer_id);
            params.put("payment", txt_pay.getText().toString());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    api_url + "/liquidate-sale",
                    params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG:Liquidate", response.toString());
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG:Liquidate-error", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "bearer " +
                            Auth.getAuth(LiquidateSaleActivity.this).getToken());
                    return headers;
                }

            };

            VolleyS.getInstance(this).getQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}