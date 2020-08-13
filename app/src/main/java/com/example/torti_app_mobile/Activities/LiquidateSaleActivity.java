package com.example.torti_app_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torti_app_mobile.Adapters.DelieveriesAdapter;
import com.example.torti_app_mobile.Adapters.LiquidateSaleAdapter;
import com.example.torti_app_mobile.Adapters.ProductAdapter;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class LiquidateSaleActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private Button btn_detail;
    private TextView txt_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquidate_sale);
        this.initializeElements();
        this.liquited_sale();
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Tortillas", "10.50", 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LiquidateSaleActivity.this));
        recyclerView.setAdapter(new LiquidateSaleAdapter(productList));
    }

    private void initializeElements() {
        recyclerView = findViewById(R.id.r_delivieries);
        btn_detail = findViewById(R.id.btn_detail_liquidate_sale);
    }

    private void liquited_sale() {
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
                        Toast.makeText(LiquidateSaleActivity.this, "Hola", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(LiquidateSaleActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }
}