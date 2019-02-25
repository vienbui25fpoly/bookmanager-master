package com.tiennvph06776.bookmanager.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.Bill;
import com.tiennvph06776.bookmanager.project.sqlitedao.BillDAO;

import java.util.Date;
import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolder> {
    private List<Bill> billList;
    private BillDAO billDAO;

    public AdapterBill(BillDAO billDAO, List<Bill> billList) {
        this.billDAO = billDAO;
        this.billList = billList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.item_bill, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Bill st = billList.get(position);
        holder.tvMa.setText(st.id);
        holder.tvNgay.setText(new Date(st.date).toString());

    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvNgay, tvMa;


        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_HoaDon);
            tvMa = itemView.findViewById(R.id.tvMaHoaDon_HoaDon);
            tvNgay = itemView.findViewById(R.id.tvNgay_HoaDon);
        }

    }
}
