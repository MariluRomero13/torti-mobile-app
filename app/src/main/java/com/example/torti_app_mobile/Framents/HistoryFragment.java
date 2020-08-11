package com.example.torti_app_mobile.Framents;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.torti_app_mobile.Activities.HistoryActivity;
import com.example.torti_app_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SALES_COMPLETED = 1;
    private static final int SALES_PENDING = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        View btnHistorySales = rootView.findViewById(R.id.btn_history_sales);
        View btnHistoryPending = rootView.findViewById(R.id.btn_history_pending_p);
        btnHistoryPending.setOnClickListener(this);
        btnHistorySales.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(getActivity() == null) return;
        Intent intent = new Intent(getContext(), HistoryActivity.class);
        switch (v.getId()) {
            case R.id.btn_history_sales:
                intent.putExtra("type", SALES_COMPLETED);
                break;
            case R.id.btn_history_pending_p:
                intent.putExtra("type", SALES_PENDING);
                break;
            }
            getActivity().startActivity(intent);
        }
    }