package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.BillDetail;

import java.util.List;

public class AdapterBillDetail extends RecyclerView.Adapter<AdapterBillDetail.ViewHolder> {
    private List<BillDetail> hoaDonList;

    public AdapterBillDetail(List<BillDetail> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_bill_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBillDetail.ViewHolder holder, int position) {
        final BillDetail st = hoaDonList.get(position);

        holder.tvName.setText(st.bookID);
        holder.tvQuality.setText(st.quality + "");
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgDelete;
        public TextView tvName, tvQuality;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvQuality = itemView.findViewById(R.id.tvQuality);

        }

    }
}
