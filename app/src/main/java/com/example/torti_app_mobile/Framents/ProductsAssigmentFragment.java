package com.example.torti_app_mobile.Framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Adapters.ProductAssigmentAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

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
 * Use the {@link ProductsAssigmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsAssigmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsAssigmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsAssigmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsAssigmentFragment newInstance(String param1, String param2) {
        ProductsAssigmentFragment fragment = new ProductsAssigmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_products_assigment, container, false);

        /*List<Product> lista = new ArrayList<>();
        lista.add(new Product("Tortillas Grandes", "10", 20));*/
        this.recyclerView = rootView.findViewById(R.id.r_products_a);
        getAssignmentProductsFromServer();
        return rootView;
    }

    private void getAssignmentProductsFromServer() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                api_url + "api/deliverier/assignment-products", null,
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
                                JSONObject productJson = assignment.getJSONObject("stock")
                                        .getJSONObject("product");
                                String name = productJson.getString("name");
                                String price = productJson.getString("unit_price");
                                Product product = new Product(name, price, quantity);
                                products.add(product);
                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new ProductAssigmentAdapter(products));

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
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjIsImlhdCI6MTU5NjgxOTQyNiwiZXhwIjoxNTk2ODQ4MjI2fQ.Fadk9Z8O0H7iVaIDoMKnibSmydgJ0D4pimqan06Uy8g");
                return headers;
            }
        };
        VolleyS.getInstance(getContext()).getQueue().add(request);
    }
}