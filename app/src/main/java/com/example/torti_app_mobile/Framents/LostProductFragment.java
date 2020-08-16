package com.example.torti_app_mobile.Framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Adapters.LostProductAdapter;
import com.example.torti_app_mobile.Adapters.SaleAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Devolution;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostProductFragment extends Fragment {
    private static final String CUSTOMER_ID = "customerId";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private RecyclerView recyclerView = null;
    private LostProductAdapter adapter = null;
    private FloatingActionButton fab = null;
    private int customerId;

    public LostProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LostProductFragment newInstance(int customerId) {
        LostProductFragment fragment = new LostProductFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_lost_product, container, false);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.fab = rootView.findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = adapter.getLostProducts();
                if(products.isEmpty()) {
                    Toast.makeText(getContext(),
                            "Por favor llene todos los datos requeridos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Devolution devolution = new Devolution(customerId, products);
                sendLostProductsToServer(devolution);
            }
        });
        getProductsFromServer();
        return rootView;
    }

    private void sendLostProductsToServer(Devolution devolution) {
        String data = new Gson().toJson(devolution, Devolution.class);
        Log.e("data", data);
        try {
            JSONObject object = new JSONObject(data);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    api_url + "/devolution", object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean status = response.getBoolean("status");
                                if(status) {
                                    adapter.clear();
                                    Toast.makeText(getContext(),
                                            "Devolución creada correctamente",
                                            Toast.LENGTH_SHORT).show();
                                }
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
                    headers.put("Authorization", "bearer " + Auth.getAuth(getContext()).getToken());
                    return headers;
                }
            };
            VolleyS.getInstance(getContext()).getQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                                Product product = new Product(id, name, 0, null);
                                products.add(product);
                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new LostProductAdapter(products);
                            recyclerView.setAdapter(adapter);

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
}