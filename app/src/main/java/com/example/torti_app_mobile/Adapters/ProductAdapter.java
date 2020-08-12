package com.example.torti_app_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    public ProductAdapter(List<Product> list){
        this.products = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = this.products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvProductName;
        private TextView txvPrice;
        private TextView txvQty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvProductName = itemView.findViewById(R.id.txvProductName);
            this.txvPrice = itemView.findViewById(R.id.txvProductPrice);
            this.txvQty = itemView.findViewById(R.id.txvQty);
        }

        private void bind(Product product) {
            this.txvProductName.setText(product.getProduct());
            this.txvPrice.setText(String.format(Locale.getDefault(),
                    "Precio: $%s", product.getPrice()));
            this.txvQty.setText(String.format(Locale.getDefault(),
                    "Cantidad: %d", product.getQuantity()));
        }
    }
}
