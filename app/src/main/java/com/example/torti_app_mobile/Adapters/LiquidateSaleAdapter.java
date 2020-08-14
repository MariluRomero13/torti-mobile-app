package com.example.torti_app_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

import java.util.List;

public class LiquidateSaleAdapter extends RecyclerView.Adapter<LiquidateSaleAdapter.ViewHolder> {
    private List<Product> productList;

    public LiquidateSaleAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public LiquidateSaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_liquidate_sale, viewGroup, false);
        LiquidateSaleAdapter.ViewHolder vh = new LiquidateSaleAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        // double total_by_product = productList.get(i).getPrice() * productList.get(i).getQuantity();
        holder.txtProduct.setText(productList.get(i).getProduct());
        holder.txtPrice.setText(String.format("Precio: $%s", productList.get(i).getPrice()));
        holder.txtQuantity.setText("Cantidad: "+ productList.get(i).getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtProduct, txtPrice, txtQuantity;
        public ViewHolder(View itemView){
            super(itemView);
            txtProduct = itemView.findViewById(R.id.txt_product);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
        }
    }
}
