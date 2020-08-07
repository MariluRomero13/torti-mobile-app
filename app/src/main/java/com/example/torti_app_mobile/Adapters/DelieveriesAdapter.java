package com.example.torti_app_mobile.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import java.util.List;

public class DelieveriesAdapter extends RecyclerView.Adapter<DelieveriesAdapter.ViewHolder> {
    private List<Customer> customerList;

    public DelieveriesAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_delieveries, viewGroup, false);
        DelieveriesAdapter.ViewHolder vh = new DelieveriesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.txtCustomer.setText(customerList.get(i).getName());
        holder.txtPhone.setText(customerList.get(i).getPhone());
        Log.d("ADAPTER:", customerList.get(i).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG::", "Hola");
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCustomer, txtPhone;
        public ViewHolder(View itemView){
            super(itemView);
            txtCustomer = itemView.findViewById(R.id.txt_customer);
            txtPhone = itemView.findViewById(R.id.txt_phone);
        }
    }
}
