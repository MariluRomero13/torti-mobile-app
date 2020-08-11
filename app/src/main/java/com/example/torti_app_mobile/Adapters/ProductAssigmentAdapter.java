package com.example.torti_app_mobile.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;

import java.util.List;

public class ProductAssigmentAdapter extends RecyclerView.Adapter<ProductAssigmentAdapter.ViewHolder> {

    private List<Product> productList;

    public ProductAssigmentAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAssigmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_products_assigment, viewGroup, false);
        ProductAssigmentAdapter.ViewHolder vh = new ProductAssigmentAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAssigmentAdapter.ViewHolder holder, int i) {
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
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtQuantity = itemView.findViewById(R.id.txt_quantity_product);
        }
    }
}
