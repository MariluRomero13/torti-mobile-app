package com.example.torti_app_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Activities.Devolution;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

public class DevolutionAdapter extends RecyclerView.Adapter<DevolutionAdapter.ViewHolder> {
    private List<Devolution> devolutions;

    public DevolutionAdapter(List<Devolution> devolutions) {
        this.devolutions = devolutions;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_devolution, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Devolution devolution = this.devolutions.get(position);
        Product product = devolution.getProduct();
        holder.txvProductName.setText(product.getProduct());
        holder.txvProductPrice.setText(String.format(Locale.getDefault(),
                "Precio: $%s", product.getPrice()));
        holder.txvQuantity.setText(String.format(Locale.getDefault(),
                "Cantidad: %s", devolution.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return this.devolutions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txvProductName = null;
        private TextView txvQuantity = null;
        private TextView txvProductPrice = null;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvProductName = itemView.findViewById(R.id.txvProductName);
            this.txvQuantity = itemView.findViewById(R.id.txvQuantity);
            this.txvProductPrice = itemView.findViewById(R.id.txvProductPrice);
        }
    }
}
