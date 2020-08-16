package com.example.torti_app_mobile.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Activities.HomeActivity;
import com.example.torti_app_mobile.Models.Product;
import com.example.torti_app_mobile.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LostProductAdapter extends RecyclerView.Adapter<LostProductAdapter.ViewHolder> {
    private List<Product> productList;
    private List<Product> lostProducts = new ArrayList<>();

    public LostProductAdapter(List<Product> products) {
        this.productList = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lost_products, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Product product = this.productList.get(position);
        holder.txvProductName.setText(product.getProduct());
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
                fillListLostProduct();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.btnShowDialogForDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForDescription(holder.itemView.getContext(), product);
            }
        });
    }

    private void showDialogForDescription(final Context context, final Product product) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        final EditText edtDescription = dialog.findViewById(R.id.edtDescription);
        final View btnAccept = dialog.findViewById(R.id.txvAccept);
        String description = product.getDescription();
        if(description != null) edtDescription.setText(description);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = edtDescription.getText().toString();
                product.setDescription(description);
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                fillListLostProduct();
            }
        });

        dialog.show();
    }

    private void fillListLostProduct() {
        this.lostProducts.clear();
        for (Product product: this.productList) {
            if(product.getQuantity() == 0 || product.getDescription() == null)
                continue;
            int quantity = product.getQuantity();
            String description = product.getDescription();
            int id = product.getId();
            Product p = new Product(id, quantity, description);
            this.lostProducts.add(p);
        }
    }

    public void clear () {
        for (Product product: this.productList) {
            product.setQuantity(0);
            product.setDescription(null);
        }
        this.notifyDataSetChanged();
    }

    public List<Product> getLostProducts() {
        return this.lostProducts;
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvProductName = null;
        private EditText edtQuantity = null;
        private View btnShowDialogForDescription = null;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvProductName = itemView.findViewById(R.id.txvProductName);
            this.edtQuantity = itemView.findViewById(R.id.edtQuantity);
            this.btnShowDialogForDescription = itemView.findViewById(R.id.imgDescription);
        }
    }
}
