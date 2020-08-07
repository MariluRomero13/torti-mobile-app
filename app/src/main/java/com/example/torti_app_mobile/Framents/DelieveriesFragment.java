package com.example.torti_app_mobile.Framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DelieveriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DelieveriesFragment extends Fragment {

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

        List<Customer> lista = new ArrayList<>();
        lista.add(new Customer("La Esquina", "Las Etnias", "8712345678", "", ""));
        lista.add(new Customer("Tortilleria Las Etnias", "Las Etnias", "8714567890", "", ""));
        this.recyclerView = rootView.findViewById(R.id.r_delivieries);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DelieveriesAdapter(lista));
        Log.i("LISTA::", lista.toString());
        return rootView;
    }
}