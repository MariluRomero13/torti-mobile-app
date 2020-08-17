package com.example.torti_app_mobile.Framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.torti_app_mobile.Activities.LiquidateSaleActivity;
import com.example.torti_app_mobile.Adapters.ProductAssigmentAdapter;
import com.example.torti_app_mobile.Adapters.SaleAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.Models.Sale;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesFragment extends Fragment implements SaleAdapter.OnTotalChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int customerId;
    private static final String CUSTOMER_ID = "customerId";

    // TODO: Rename and change types of parameters
    private String mParam2;
    private RecyclerView recyclerView;
    private TextView txvTotal = null, txtPayment =  null;
    private FloatingActionButton fab = null;
    private SaleAdapter saleAdapter = null;
    private String total = null, payment = null;

    public SalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment SalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesFragment newInstance(int customerId) {
        SalesFragment fragment = new SalesFragment();
        Bundle args = new Bundle();
        args.putInt(CUSTOMER_ID, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.customerId = getArguments().getInt(CUSTOMER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_sales, container, false);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.txvTotal = rootView.findViewById(R.id.txvTotal);
        this.fab = rootView.findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.layout_bottom_sale,
                                (ViewGroup) rootView.findViewById(R.id.bottom_sheet_sale)
                        );
                txtPayment = bottomSheetView.findViewById(R.id.txt_to_pay);

                bottomSheetView.findViewById(R.id.btn_pay_sale).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(saleAdapter != null) {
                            List<Product> products = saleAdapter.getProducts();
                            if(products.isEmpty()) {
                                Toast.makeText(getContext(),
                                        "Escoja por lo menos un producto", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            v.setEnabled(false);
                            payment = txtPayment.getText().toString();
                            Log.d("TAG:Payment", payment);
                            Sale sale = new Sale(customerId, total, payment, products);
                            sendSaleDataToServer(sale);
                        }
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
        getProductsFromServer();
        return rootView;
    }

    private void sendSaleDataToServer(Sale sale) {
        final String data = new Gson().toJson(sale, Sale.class);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, api_url + "/sales",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG:Sale", response);
                        saleAdapter.clear();
                        Toast.makeText(getContext(), "Vendido correctamente", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG:Sale-error", error.toString());
                fab.setEnabled(true);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return data.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " + Auth.getAuth(getContext()).getToken());
                return headers;
            }
        };
        VolleyS.getInstance(getContext()).getQueue().add(stringRequest);
        Log.e("data", data);
    }

    private void getProductsFromServer() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                api_url + "/assignment-products", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        List<Product> products = new ArrayList<>();
                        try {
                            JSONArray assignmentProducts =
                                    response.getJSONArray("assignmentProducts");
                            for (int i = 0; i < assignmentProducts.length() ; i++) {
                                JSONObject assignment = assignmentProducts.getJSONObject(i);
                                int quantity = assignment.getInt("quantity");
                                if(quantity == 0) continue;
                                JSONObject productJson = assignment.getJSONObject("stock")
                                        .getJSONObject("product");
                                int id = productJson.getInt("id");
                                String name = productJson.getString("name");
                                String price = productJson.getString("unit_price");
                                Product product = new Product(id, name, price, 0);
                                products.add(product);
                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            saleAdapter = new SaleAdapter(products, SalesFragment.this);
                            recyclerView.setAdapter(saleAdapter);

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
                        Auth.getAuth(getContext()).getToken());
                return headers;
            }
        };
        VolleyS.getInstance(getContext()).getQueue().add(request);
    }

    @Override
    public void onTotalChange(String totalFormatted) {
        this.total = totalFormatted;
        this.txvTotal.setText(String.format(Locale.getDefault(),
                "Total:\n$%s", totalFormatted));
    }
}