package com.example.torti_app_mobile.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    private List<Product> products;
    public OnTotalChangeListener listener;
    private final List<Product> saleDetails = new ArrayList<>();
    public interface OnTotalChangeListener {
        void onTotalChange(String totalFormatted);
    }

    public SaleAdapter(List<Product> products, OnTotalChangeListener listener) {
        this.products = products;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sales, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = this.products.get(position);
        holder.txvName.setText(product.getProduct());
        holder.edtQuantity.setText("0");
        holder.edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quantity = s.toString();
                if(quantity.trim().length() == 0) {
                    quantity = "0";
                }
                product.setQuantity(Integer.parseInt(quantity));
                setTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    private void setTotal() {
        float total = 0f;
        this.saleDetails.clear();
        for (Product p: this.products) {
            if(p.getQuantity() == 0) continue;
            String price = p.getPrice();
            total += Float.parseFloat(price) * p.getQuantity();
            Product product = new Product(p.getId(), p.getQuantity());
            this.saleDetails.add(product);
        }
        String totalFormatted = String.format(Locale.getDefault(), "%.2f", total);
        this.listener.onTotalChange(totalFormatted);
    }

    public void clear() {
        for (Product product: this.products) {
            product.setQuantity(0);
        }
        this.notifyDataSetChanged();
    }

    public List<Product> getProducts() {
        return saleDetails;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvName = null;
        private EditText edtQuantity = null;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvName = itemView.findViewById(R.id.txvProductName);
            this.edtQuantity = itemView.findViewById(R.id.edtQuantity);
        }
    }
}
