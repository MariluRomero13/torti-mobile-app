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

import com.example.torti_app_mobile.Models.Customer;
import com.example.torti_app_mobile.R;

import java.util.List;

public class DelieveriesAdapter extends RecyclerView.Adapter<DelieveriesAdapter.ViewHolder> {
    public interface OnDeliveryClickListener{
        void onDeliveryClick(Customer customer);
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
        holder.iconMoreVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeliveryClick(customer);
                PopupMenu popup = new PopupMenu(context, holder.iconMoreVert);
                popup.inflate(R.menu.delieveries_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_call:
                                Toast.makeText(context, "Llamar", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_location:
                                Toast.makeText(context, "Ubicacion", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_sales:
                                Toast.makeText(context, "Ventas", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_pending_payment:
                                Toast.makeText(context, "Pagos pendientes", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
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
