package com.example.torti_app_mobile.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Activities.HomeActivity;
import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import java.util.List;

public class DelieveriesAdapter extends RecyclerView.Adapter<DelieveriesAdapter.ViewHolder> {
    public interface OnDeliveryClickListener{
        void onDeliveryClick(View v, Customer customer);
        void onItemClick(Customer customer);
    }
    private List<Customer> customerList;
    private OnDeliveryClickListener listener;
    private Context context;

    public DelieveriesAdapter(List<Customer> customerList, OnDeliveryClickListener listener) {
        this.customerList = customerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_delieveries, viewGroup, false);
        context = v.getContext();
        DelieveriesAdapter.ViewHolder vh = new DelieveriesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final Customer customer = customerList.get(i);
        holder.txtCustomer.setText(customer.getName());
        holder.txtPhone.setText(customer.getPhone());
        Log.d("ADAPTER:", customer.getName());
        if(context instanceof HomeActivity) {
            holder.iconMoreVert.setVisibility(View.VISIBLE);
            holder.iconMoreVert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeliveryClick(v, customer);
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(customer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomer, txtPhone;
        private ImageView iconMoreVert = null;
        public ViewHolder(View itemView){
            super(itemView);
            txtCustomer = itemView.findViewById(R.id.txt_customer);
            txtPhone = itemView.findViewById(R.id.txt_phone);
            this.iconMoreVert = itemView.findViewById(R.id.iconMore);
        }
    }
}
