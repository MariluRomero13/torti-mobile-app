package com.example.torti_app_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app_mobile.Models.History;
import com.example.torti_app_mobile.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<History> histories;

    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = this.histories.get(position);
        holder.bind(history);
    }

    @Override
    public int getItemCount() {
        return this.histories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private TextView txvTotalSale;
        private TextView txvCredit;
        private TextView txvTotalToPay;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvTotalSale = itemView.findViewById(R.id.txvTotalSale);
            this.txvCredit = itemView.findViewById(R.id.txvCredit);
            this.txvTotalToPay = itemView.findViewById(R.id.txvTotalToPay);
            this.recyclerView = itemView.findViewById(R.id.recyclerView);
        }

        private void bind (History history) {
            this.txvTotalSale.setText(
                    String.format(Locale.getDefault(),
                            "Total: $%s",history.getTotal()));
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            this.recyclerView.setAdapter(new ProductAdapter(history.getDetails()));
            if(history.getStatus() == History.SALE_COMPLETED) {
                this.txvCredit.setVisibility(View.GONE);
                this.txvTotalToPay.setVisibility(View.GONE);
            } else if (history.getStatus() == History.SALE_PENDING) {
                this.txvCredit.setText(String.format(Locale.getDefault(),
                        "Abonado: $%s", history.getCredit()));
                this.txvTotalToPay.setText(String.format(Locale
                        .getDefault(), "Total a pagar: $%s", history.getTotalToPay()));
            }
        }
    }
}
