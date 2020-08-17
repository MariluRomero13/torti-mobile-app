package com.example.torti_app_mobile.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Devolution devolution = this.devolutions.get(position);
        final Product product = devolution.getProduct();
        holder.txvProductName.setText(product.getProduct());
        holder.txvProductPrice.setText(String.format(Locale.getDefault(),
                "Precio: $%s", product.getPrice()));
        holder.txvQuantity.setText(String.format(Locale.getDefault(),
                "Cantidad: %s", devolution.getQuantity()));
        holder.btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForDescription(holder.itemView.getContext(), devolution.getDescription());
            }
        });

    }

    private void showDialogForDescription(Context context, String description) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_description);
        final TextView txvDescription = dialog.findViewById(R.id.txvDescription);
        final View btnAccept = dialog.findViewById(R.id.txvAccept);
        if(description != null) txvDescription.setText(description);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return this.devolutions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txvProductName = null;
        private TextView txvQuantity = null;
        private TextView txvProductPrice = null;
        private View btnDescription = null;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvProductName = itemView.findViewById(R.id.txvProductName);
            this.txvQuantity = itemView.findViewById(R.id.txvQuantity);
            this.txvProductPrice = itemView.findViewById(R.id.txvProductPrice);
            this.btnDescription = itemView.findViewById(R.id.iconMessage);
        }
    }
}
