package com.example.torti_app_mobile.Framents;

import android.app.Dialog;
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
import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.example.torti_app_mobile.Models.Auth;
import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DelieveriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DelieveriesFragment extends Fragment implements DelieveriesAdapter.OnDeliveryClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DelieveriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DelieveriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DelieveriesFragment newInstance(String param1, String param2) {
        DelieveriesFragment fragment = new DelieveriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_delieveries, container, false);
        this.recyclerView = rootView.findViewById(R.id.r_delivieries);
        getAssignmentsCustomersFromServer();
        return rootView;
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
                                String name = customerJson.getString("name");
                                String address = customerJson.getString("address");
                                String phone = customerJson.getString("phone");
                                double latitude = customerJson.getDouble("latitude");
                                double longitude = customerJson.getDouble("longitude");
                                Customer customer =
                                        new Customer(name, address, phone, latitude, longitude);
                                customerList.add(customer);
                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new DelieveriesAdapter(customerList, DelieveriesFragment.this));
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
    public void onDeliveryClick(Customer customer) {
        Log.e("customer", customer.getName());
    }

}